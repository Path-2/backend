package ao.path2.msgateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class MsGatewayApplication

fun main(args: Array<String>) {
	runApplication<MsGatewayApplication>(*args)
}
