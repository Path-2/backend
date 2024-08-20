package io.github.dumijdev.users.application.ports.input

import io.github.dumijdev.users.application.core.domain.User

interface CreateUserInputPort {
    fun create(user: User) : User
}