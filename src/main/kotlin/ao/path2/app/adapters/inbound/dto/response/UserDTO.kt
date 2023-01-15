package ao.path2.app.adapters.inbound.dto.response

import javax.persistence.Column
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


class UserDTO() {
  @NotBlank
  @NotEmpty
  @NotNull
  lateinit var name: String

  lateinit var username: String

  @NotNull
  @NotEmpty
  @NotBlank
  lateinit var phone: String

  @Column(nullable = false, unique = true)
  lateinit var image: String

  @NotBlank
  @NotEmpty
  @NotNull
  @Email
  lateinit var email: String
}