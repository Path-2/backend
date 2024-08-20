package io.github.dumijdev.users.adapters.out.database.persistence

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
data class UserEntity(
    @Id
    val id: Long?,
    val name: String,
    val username: String,
    val password: String,
    val birthdate: LocalDate
) {

}
