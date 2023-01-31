package ao.path2.ms.auth.config

import org.n52.jackson.datatype.jts.JtsModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
  @Bean
  fun jtsModule() = JtsModule()
}