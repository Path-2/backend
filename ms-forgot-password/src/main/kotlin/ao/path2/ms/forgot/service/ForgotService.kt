package ao.path2.ms.forgot.service

import ao.path2.ms.forgot.dto.ForgotUserDto
import ao.path2.ms.forgot.dto.PwdDto
import ao.path2.ms.forgot.exceptions.ResourceNotFoundException
import ao.path2.ms.forgot.models.EmailModel
import ao.path2.ms.forgot.models.enums.Template
import ao.path2.ms.forgot.producers.RabbitMQProducer
import ao.path2.ms.forgot.repository.UserRepository
import ao.path2.ms.forgot.token.JwtToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Transactional
@Service
class ForgotService(
  private val userRepository: UserRepository,
  private val jwtToken: JwtToken,
  private val producer: RabbitMQProducer,
  private val encoder: PasswordEncoder
) {
  fun createRequestToRestorePwd(user: ForgotUserDto) {
    val user1 =
      if (userRepository.existsByEmail(user.usernameOrEmail)) userRepository.findByEmail(user.usernameOrEmail)
      else if (userRepository.existsByUsername(user.usernameOrEmail)) userRepository.findByUsername(user.usernameOrEmail)
      else throw ResourceNotFoundException("user not found ${user.usernameOrEmail}")

    val token = jwtToken.generateToken(user1?.email ?: "")

    val email = EmailModel(
      subject = "Pedido de alteração da password",
      token = token,
      name = user1?.name ?: "",
      template = Template.FORGOT.value,
      to = listOf(user1?.email ?: "")
    )

    producer.enqueue(email)
  }

  fun updatePwd(pwdDto: PwdDto, username: String) {

    val user = userRepository.findByUsername(username) ?: userRepository.findByEmail(username)
    ?: throw ResourceNotFoundException("")


    if (daysDifference(user.passwordUpdatedAt ?: LocalDateTime.now()) < 30)
      throw RuntimeException("You cannot update the password in less than 30 days.")

    user.password = encoder.encode(pwdDto.password)
    user.updatedAt = LocalDateTime.now()
    user.passwordUpdatedAt = user.updatedAt

    userRepository.save(user)
  }

  fun daysDifference(dateTime: LocalDateTime, today: LocalDateTime = LocalDateTime.now()): Int =
    (today.year - dateTime.year) + (today.dayOfYear - dateTime.dayOfYear)

}