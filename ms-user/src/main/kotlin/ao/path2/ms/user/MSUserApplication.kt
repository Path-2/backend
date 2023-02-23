package ao.path2.ms.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class MSUserApplication

fun main(args: Array<String>) {
  runApplication<MSUserApplication>(*args)
}
