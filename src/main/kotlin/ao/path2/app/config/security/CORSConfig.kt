package ao.path2.app.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CORSConfig {

  @Bean
  fun cors(configSource: CorsConfigurationSource): CorsFilter {

    return CorsFilter(configSource)
  }
}