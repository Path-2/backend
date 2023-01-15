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
  var name: String? = null
  var username: String? = null

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
}