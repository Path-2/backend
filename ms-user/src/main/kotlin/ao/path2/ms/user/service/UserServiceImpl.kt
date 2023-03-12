package ao.path2.ms.user.service

import ao.path2.ms.user.core.exceptions.ResourceExistsException
import ao.path2.ms.user.core.exceptions.ResourceNotFoundException
import ao.path2.ms.user.models.*
import ao.path2.ms.user.models.enums.Template
import ao.path2.ms.user.models.enums.UserSource
import ao.path2.ms.user.producers.RabbitMQProducer
import ao.path2.ms.user.repository.RoleRepository
import ao.path2.ms.user.repository.UserRepository
import ao.path2.ms.user.utils.mapping.Mapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.amqp.AmqpException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Service
class UserServiceImpl(
  private val repo: UserRepository,
  private val roleRepo: RoleRepository,
  private val encoder: PasswordEncoder,
  private val producer: RabbitMQProducer,
  private val mapper: Mapper
) : UserService {
  private val log: Logger = LogManager.getLogger(UserService::class.java.toString())
  val restTemplate = RestTemplate()

  override fun save(user: User): User {
    log.info("Searching user...")
    if (repo.existsByEmail(user.email ?: "") ||
      repo.existsByPhone(user.phone ?: "") ||
      repo.existsByFacebookId(user.facebookId.toString())
    ) {
      log.error("User exists...")
      throw ResourceExistsException("User exists!!!")
    }

    log.info("User not found...")

    user.username = "@${user.email?.substring(0, user.email?.indexOf("@") ?: 8)}"
    user.password = encoder.encode(user.password)
    user.facebookId = null

    if (repo.existsByUsername(user.username)) {
      user.username = "${user.username}.${UUID.randomUUID().toString().substring(0, 1)}"
    }

    val role = roleRepo.findByNameContainingIgnoreCase("user")
    user.createdAt = LocalDateTime.now()
    user.updatedAt = user.createdAt
    user.passwordUpdatedAt = user.createdAt.minusDays(30)

    if (role != null) {
      log.info("Adding role ${role.name}...")
      user.roles = listOf(role)
    }

    val newUser = repo.save(user)

    val data = mutableMapOf<String, Any>()

    data["name"] = newUser.name
    data["verifyLink"] = "http://localhost:7777/v/u/${newUser.username}"

    val emailModel = EmailModel(
      "Verificação da conta",
      newUser.name ,
      "",
      listOf(newUser.email ?: ""),
      Template.VERIFY.value
    )

    try {

      producer.enqueue(emailModel)

      log.info("User will be verify...")

    } catch (ex: AmqpException) {

      log.error("User verify email failed...")

    }

    return newUser
  }

  override fun findByEmail(email: String): User {
    log.info("Searching an user with email $email...")
    if (!repo.existsByEmail(email)) {
      log.error("User with email $email not found...")
      throw ResourceNotFoundException("User $email not found!!!")
    }
    log.info("User with email $email found...")

    return repo.findByEmail(email)
  }

  override fun findByPhone(phone: String): User {
    log.info("Searching an user with phone $phone...")
    if (!repo.existsByPhone(phone)) {
      log.error("User with phone $phone not found...")
      throw ResourceNotFoundException("User $phone not found!!!")
    }
    log.info("User with phone $phone found...")

    return repo.findByPhone(phone)
  }

  private fun withoutPasswordAndRoles(user: User): User = mapper.map(user, User()) { data ->
    run {
      (data as User).password = null
      data.roles = null
    }
  } as User

  override fun listAll(page: Pageable): Page<User> = repo.findAll(page).map { data -> withoutPasswordAndRoles(data) }

  override fun update(user: User): User {
    log.info("Searching user...")
    if (!repo.existsByEmail(user.email ?: "")) {
      log.error("User with id ${user.email} not found...")
      throw ResourceExistsException("User ${user.email} not exist!!!")
    }
    log.info("User found...")
    return repo.save(user)
  }

  override fun findByUsername(username: String): User {
    log.info("Searching an user with username $username...")
    if (!repo.existsByUsername(username)) {
      log.error("User with username $username not found...")
      throw ResourceNotFoundException("User $username not found!!!")
    }
    log.info("User with username $username found...")

    return repo.findByUsername(username)
  }

  @Transactional
  override fun signupWithGoogle(token: String): User {
    val headers = HttpHeaders()

    headers.set("Authorization", "Bearer $token")

    val httpEntity = httpEntity(headers)

    val res =
      restTemplate.exchange(
        getGoogleAuthURL(),
        HttpMethod.GET,
        httpEntity,
        GoogleUserData::class.java
      )

    val body = res.body

    if (body != null) {
      val user = User()
      log.info("Searching user...")

      println(body)

      if (repo.existsByEmail(body.email)) {
        log.error("User exists...")
        throw ResourceExistsException("User exists!!!")
      }

      log.info("User not found...")

      user.name = body.name
      user.image = body.imageUrl ?: ""
      user.email = body.email
      user.username = "@${body.email.substring(0, body.email.indexOf("@"))}"
      user.password = encoder.encode(UUID.randomUUID().toString().substring(0, 15))
      user.createdBy = UserSource.GOOGLE
      user.createdAt = LocalDateTime.now()
      val role = roleRepo.findByNameContainingIgnoreCase("user")

      if (role != null) {
        log.info("Adding role ${role.name}...")
        user.roles = listOf(role)
      }

      println(user)

      if (repo.existsByUsername(user.username)) {
        user.username = "${user.username}.${UUID.randomUUID().toString().substring(0, 1)}"
        println(user.username)
      }

      return repo.save(user)

    } else throw RuntimeException("")
  }

  override fun signupWithFacebook(token: String): User {
    val res =
      restTemplate.getForEntity(
        getFacebookAuthURL(token),
        FacebookUserData::class.java
      )

    val body = res.body

    if (body != null) {
      val user = User()
      log.info("Searching user...")

      if (repo.existsByFacebookId(body.id)) {
        log.error("User exists...")
        throw ResourceExistsException("User exists!!!")
      }

      log.info("User not found...")

      user.name = body.name
      user.image = body.profilePicture ?: ""
      user.facebookId = body.id
      user.username = "@${body.firstName.replace(" ", "")}"
      user.password = encoder.encode(UUID.randomUUID().toString().substring(0, 17))
      user.createdBy = UserSource.FACEBOOK
      user.createdAt = LocalDateTime.now()
      val role = roleRepo.findByNameContainingIgnoreCase("user")

      if (role != null) {
        log.info("Adding role ${role.name}...")
        user.roles = listOf(role)
      }

      if (repo.existsByUsername(user.username)) {
        user.username = "${user.username}.${UUID.randomUUID().toString().substring(0, 1)}"
      }

      return repo.save(user)

    } else throw RuntimeException("")
  }
}

fun getFacebookAuthURL(token: String) =
  "https://graph.facebook.com/me?access_token=${token}&fields=id,name,last_name,first_name"

fun getGoogleAuthURL() =
  "https://openidconnect.googleapis.com/v1/userinfo"

private fun httpEntity(headers: HttpHeaders): HttpEntity<*> = HttpEntity("", headers)