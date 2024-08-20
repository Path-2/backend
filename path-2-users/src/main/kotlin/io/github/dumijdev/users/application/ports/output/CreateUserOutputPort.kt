package io.github.dumijdev.users.application.ports.output

import io.github.dumijdev.users.application.core.domain.User

interface CreateUserOutputPort {
    fun create(user: User) : User
}