package ao.path2.ms.forgot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class MSForgotPasswordApplication

fun main(args: Array<String>) {
  runApplication<MSForgotPasswordApplication>(*args)
}