package ao.path2.ms.user.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CORSConfig {

  /*@Value("\${cors.originPatterns:default}")
  private val corsOriginPatterns: String = "*"*/

  @Bean
  fun addCorsConfig(): WebMvcConfigurer {
    return object : WebMvcConfigurer {
      override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
          .allowedMethods("*")
          .allowedOrigins("*")
      }
    }
  }
}