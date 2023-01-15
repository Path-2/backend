package ao.path2.app.adapters.inbound.dto.request

import javax.validation.constraints.*

class UserDTO() {
  @NotBlank
  @NotEmpty
  @NotNull
  var name: String? = null

  @NotNull
  @NotEmpty
  @NotBlank
  var phone: String? = null
  var image: String? = null

  @NotBlank
  @NotEmpty
  @NotNull
  @Email
  var email: String? = null

  @Size(min = 8)
  @NotNull
  @NotEmpty
  @NotBlank
  var password: String? = null
}