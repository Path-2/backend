package ao.path2.ms.forgot.service

import ao.path2.ms.forgot.dto.ForgotUserDto
import ao.path2.ms.forgot.dto.PwdDto
import ao.path2.ms.forgot.exceptions.ResourceNotFoundException
import ao.path2.ms.forgot.models.EmailModel
import ao.path2.ms.forgot.models.User
import ao.path2.ms.forgot.producers.RabbitMQProducer
import ao.path2.ms.forgot.repository.UserRepository
import ao.path2.ms.forgot.token.JwtToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.LocalDateTime

@Service
class ForgotService(
  private val userRepository: UserRepository,
  private val jwtToken: JwtToken,
  private val producer: RabbitMQProducer,
  private val encoder: PasswordEncoder
) {
  fun createRequestToRestorePwd(user: ForgotUserDto) {
    val user1: User = userRepository.findByEmail(user.username) ?: userRepository.findByUsername(user.username)
    ?: throw ResourceNotFoundException("user not found ${user.username}")

    val token = jwtToken.generateToken(user1.email ?: "")

    val email = EmailModel(
      to = user1.email,
      name = user1.name,
      token = token,
      timestamp = LocalDateTime.now()
    )

    producer.enqueue(email)
  }

  fun updatePwd(pwdDto: PwdDto, token: String) {
    if (!jwtToken.isTokenValid(token))
      throw Exception("Invalid token")

    val username = jwtToken.getUsername(token)

    val user = userRepository.findByUsername(username) ?: throw ResourceNotFoundException("")

    user.password = encoder.encode(pwdDto.password)

    userRepository.save(user)
  }
}