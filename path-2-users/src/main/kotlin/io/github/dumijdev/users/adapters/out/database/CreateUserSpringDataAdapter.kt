package io.github.dumijdev.users.adapters.out.database

import io.github.dumijdev.path2.core.utils.Encoder
import io.github.dumijdev.users.adapters.out.database.persistence.UserEntity
import io.github.dumijdev.users.adapters.out.database.persistence.UserRepository
import io.github.dumijdev.users.application.core.domain.User
import io.github.dumijdev.users.application.ports.output.CreateUserOutputPort
import org.springframework.stereotype.Component

@Component
class CreateUserSpringDataAdapter(
    private val userRepository: UserRepository,
    private val encoder: Encoder
) : CreateUserOutputPort {

    override fun create(user: User): User {

        val id = userRepository.save(
            UserEntity(
                name = user.name,
                password = encoder.encode(user.password),
                birthdate = user.birthdate,
                username = user.username,
                id = null
            )
        ).id

        return user.apply {
            this.id = id
        }
    }
}