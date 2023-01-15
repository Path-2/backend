package ao.path2.app.adapters.inbound.service

import ao.path2.app.core.domain.User
import ao.path2.app.core.exceptions.ResourceNotFoundException
import ao.path2.app.core.exceptions.UserAlrightExistException
import ao.path2.app.core.repository.UserRepository
import ao.path2.app.core.service.UserService
import java.util.logging.Logger

class UserServiceImpl(private val repo: UserRepository) : UserService {
  private val log: Logger = Logger.getLogger("userLogger")
  override fun save(user: User): User {
    log.info("Searching user...")
    if (repo.exists(user.id)) {
      log.severe("User found...")
      throw UserAlrightExistException("User ${user.id} alright exist!!!")
    }
    log.info("User not found...")
    return repo.save(user)
  }

  override fun findByEmail(email: String): User {
    log.info("Searching an user with email $email...")
    if (!repo.existsByEmail(email)) {
      log.severe("User not found...")
      throw ResourceNotFoundException("User $email not found!!!")
    }
    log.info("User found...")

    return repo.findByEmail(email)
  }

  override fun findByPhone(phone: String): User {

  }

  override fun listAll(): List<User> {

  }
}