package ao.path2.ms.auth.config

import ao.path2.ms.auth.token.JwtToken
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Beans {
  @Bean
  fun jwtTokenBean() = JwtToken()
}