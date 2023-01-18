package ao.path2.app.adapters.inbound.dto.response

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


class UserDTO() {
  @NotBlank
  @NotEmpty
  @NotNull
  var name: String = ""
  var username: String = ""

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
}