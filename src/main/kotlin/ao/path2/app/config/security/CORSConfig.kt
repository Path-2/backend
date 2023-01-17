package ao.path2.app.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.filter.CorsFilter
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
        val allowedOrigins = "*".split(",").toTypedArray()
        registry.addMapping("/**")
          .allowedMethods("*")
          .allowedOriginPatterns(*allowedOrigins)
          .allowCredentials(true)
      }
    }
  }
}