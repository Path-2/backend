package ao.path2.ms.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class MSAuthApplication

fun main(args: Array<String>) {
	runApplication<ao.path2.ms.auth.MSAuthApplication>(*args)
}
