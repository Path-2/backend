package ao.path2.ms.auth.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
class UserDTO() {
  var username: String = ""

  var name: String = ""

  var phone: String = ""
  var image: String = ""

  var email: String = ""

  var password: String? = ""
}