package io.github.dumijdev.path2.app

import com.vaadin.flow.component.page.AppShellConfigurator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Path2AppApplication

fun main(args: Array<String>) {
	runApplication<Path2AppApplication>(*args)
}
