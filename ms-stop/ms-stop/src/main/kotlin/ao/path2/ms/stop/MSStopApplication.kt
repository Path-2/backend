package ao.path2.ms.stop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class MSStopApplication

fun main(args: Array<String>) {
  runApplication<MSStopApplication>(*args)
}