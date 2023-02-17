package ao.path2.ms.auth.config

import ao.path2.ms.auth.token.JwtToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JSR310Module
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Beans {
  @Bean
  fun jwtTokenBean() = JwtToken()

}