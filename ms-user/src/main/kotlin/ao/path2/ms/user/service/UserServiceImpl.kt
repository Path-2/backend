package ao.path2.ms.user.service

import ao.path2.core.models.EmailModel
import ao.path2.core.models.enums.Template
import ao.path2.ms.user.producers.RabbitMQProducer
import ao.path2.core.exceptions.ResourceExistsException
import ao.path2.ms.user.repository.UserRepository
import ao.path2.ms.user.core.exceptions.ResourceNotFoundException
import ao.path2.ms.user.models.User
import org.apache.logging.log4j.LogManager
import org.springframework.amqp.AmqpException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import javax.transaction.Transactional

@Service
class UserServiceImpl(
  private val repo: UserRepository,
  private val encoder: PasswordEncoder,
  private val producer: RabbitMQProducer
) : UserService {
  private val log: Logger = LogManager.getLogger(UserService::class.java.toString())

  override fun save(user: User): User {
    log.info("Searching user...")
    if (repo.existsByEmail(user.email) || repo.existsByPhone(user.phone)) {
      log.error("User exists...")
      throw ResourceExistsException("User exists!!!")
    }
    log.info("User not found...")

    user.username = "@${user.email.substring(0, user.email.indexOf("@"))}"

    user.password = encoder.encode(user.password)

    if (repo.existsByUsername(user.username)) {
      user.username = "${user.username}.1"
    }

    val newUser = repo.save(user)

    val emailModel = EmailModel()

    emailModel.id = newUser.id
    emailModel.template = Template.VERIFY.value
    emailModel.subject = "Verificação de email"
    emailModel.to = listOf(newUser.email).toTypedArray()

    val data = mutableMapOf<String, Any>()

    data["name"] = newUser.name
    data["verifyLink"] = "http://localhost:7777/v/u/${newUser.username}"

    emailModel.data = data

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

  private fun withoutPasswordAndRoles(user: User): User {
    user.password = null
    user.roles = null
    return user
  }

  override fun listAll(page: Pageable): Page<User> {
    val all = repo.findAll(page)
    val users = mutableListOf<User>()

    for (user in all.content)
      users.add(withoutPasswordAndRoles(user))

    return PageImpl(users, page, page.pageSize.toLong())
  }

  override fun update(user: User): User {
    log.info("Searching user...")
    if (!repo.existsByEmail(user.email)) {
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
}

private fun String.substring(startIndex: Int, endString: String): String {
  val value = toString()
  val index = value.indexOf(endString)

  return value.substring(startIndex, index)
}

private fun String.matches(regex: String?): Boolean = regex!!.endsWith(".1")
