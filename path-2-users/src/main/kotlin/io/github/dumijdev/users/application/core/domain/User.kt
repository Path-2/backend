package io.github.dumijdev.users.application.core.domain

import java.time.LocalDate

data class User(
    var id: Long?,
    val username: String,
    val name: String,
    val email: String,
    val password: String,
    val birthdate: LocalDate
)
