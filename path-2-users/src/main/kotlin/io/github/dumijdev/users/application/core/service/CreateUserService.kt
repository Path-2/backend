package io.github.dumijdev.users.application.core.service

import io.github.dumijdev.users.application.core.domain.User
import io.github.dumijdev.users.application.ports.input.CreateUserInputPort
import io.github.dumijdev.users.application.ports.output.CreateUserOutputPort

class CreateUserService(private val createUserOutputPort: CreateUserOutputPort) : CreateUserInputPort {

    override fun create(user: User): User {
        return createUserOutputPort.create(user)
    }

}