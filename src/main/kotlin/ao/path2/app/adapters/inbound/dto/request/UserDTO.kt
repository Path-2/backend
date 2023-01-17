package ao.path2.app.adapters.inbound.dto.request

import javax.validation.constraints.*

class UserDTO() {
  @NotBlank
  @NotEmpty
  @NotNull
  var name: String = ""

  @NotNull
  @NotEmpty
  @NotBlank
  var phone: String = ""
  var image: String = ""

  @NotBlank
  @NotEmpty
  @NotNull
  @Email
  var email: String = ""

  @Size(min = 8)
  @NotNull
  @NotEmpty
  @NotBlank
  var password: String = ""
}