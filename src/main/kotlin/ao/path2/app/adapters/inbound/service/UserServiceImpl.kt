package ao.path2.app.adapters.inbound.service

import ao.path2.app.core.domain.User
import ao.path2.app.core.exceptions.ResourceExistsException
import ao.path2.app.core.exceptions.ResourceNotFoundException
import ao.path2.app.core.repository.UserRepository
import ao.path2.app.core.service.UserService
import ao.path2.app.utils.mapping.Mapper
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserServiceImpl(private val repo: UserRepository, private val mapper: Mapper) : UserService {
  private val log: Logger = Logger.getLogger("userLogger")

  override fun save(user: User): User {
    log.info("Searching user...")
    if (repo.existsByEmail(user.email) || repo.existsByPhone(user.phone)) {
      log.severe("User exists...")
      throw ResourceExistsException("User exists!!!")
    }
    log.info("User not found...")

    if (user.image == null) {
      user.image = ""
    }

    user.username = user.email.substring(0, user.email.indexOf("@"))

    log.info("Username ${user.username}...")

    return repo.save(user)
  }

  override fun findByEmail(email: String): User {
    log.info("Searching an user with email $email...")
    if (!repo.existsByEmail(email)) {
      log.severe("User with email $email not found...")
      throw ResourceNotFoundException("User $email not found!!!")
    }
    log.info("User with email $email found...")

    return repo.findByEmail(email)
  }

  override fun findByPhone(phone: String): User {
    log.info("Searching an user with phone $phone...")
    if (!repo.existsByPhone(phone)) {
      log.severe("User with phone $phone not found...")
      throw ResourceNotFoundException("User $phone not found!!!")
    }
    log.info("User with phone $phone found...")

    return repo.findByPhone(phone)
  }

  override fun listAll(): List<User> = repo.listAll()

  override fun update(user: User): User {
    log.info("Searching user...")
    if (!repo.existsByEmail(user.email)) {
      log.severe("User with id ${user.email} not found...")
      throw ResourceExistsException("User ${user.email} not exist!!!")
    }
    log.info("User found...")
    return repo.save(user)
  }

  override fun findByUsername(username: String): User {
    log.info("Searching an user with username $username...")
    if (!repo.existsByUsername(username)) {
      log.severe("User with username $username not found...")
      throw ResourceNotFoundException("User $username not found!!!")
    }
    log.info("User with username $username found...")

    return repo.findByUsername(username)
  }
}