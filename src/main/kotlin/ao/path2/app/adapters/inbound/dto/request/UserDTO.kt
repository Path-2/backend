package ao.path2.app.adapters.inbound.dto.request

import javax.persistence.Column
import javax.validation.constraints.*

class UserDTO() {
  @NotBlank
  @NotEmpty
  @NotNull
  lateinit var name: String

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

  @Size(min = 8)
  @NotNull
  @NotEmpty
  @NotBlank
  lateinit var password: String
}