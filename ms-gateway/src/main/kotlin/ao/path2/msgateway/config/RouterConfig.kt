package ao.path2.msgateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouterConfig {

  fun router(builder: RouteLocatorBuilder) : RouteLocator {
    return builder.routes().build()
  }
}