package io.github.dumijdev.users.infra.config

import io.github.dumijdev.users.application.core.service.CreateUserService
import io.github.dumijdev.users.application.ports.input.CreateUserInputPort
import io.github.dumijdev.users.application.ports.output.CreateUserOutputPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeansConfig {

    @Bean
    fun createUserInputPort(createUserOutputPort: CreateUserOutputPort) : CreateUserInputPort {
        return CreateUserService(createUserOutputPort)
    }

}