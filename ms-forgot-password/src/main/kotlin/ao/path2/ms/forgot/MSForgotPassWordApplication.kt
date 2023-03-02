package ao.path2.ms.forgot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class MSForgotPassWordApplication

fun main(args: Array<String>) {
  runApplication<MSForgotPassWordApplication>(*args)
}