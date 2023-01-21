package ao.path2.msdiscovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class MsDiscoveryApplication

fun main(args: Array<String>) {
	runApplication<MsDiscoveryApplication>(*args)
}
