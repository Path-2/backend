package ao.path2.ms.user.adapters.inbound.dto

import com.fasterxml.jackson.annotation.JsonInclude
import javax.validation.constraints.*

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserDTO() {
  var username: String = ""

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