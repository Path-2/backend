package ao.path2.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Path2Application

fun main(args: Array<String>) {
	runApplication<Path2Application>(*args)
}
