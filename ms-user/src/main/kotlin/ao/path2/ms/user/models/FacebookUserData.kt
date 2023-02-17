package ao.path2.ms.user.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls

@JsonInclude(JsonInclude.Include.NON_NULL)
class FacebookUserData {
  @JsonSetter("first_name", nulls = Nulls.SKIP)
  var firstName: String = ""

  @JsonSetter("last_name", nulls = Nulls.SKIP)
  var lastName: String = ""

  @JsonSetter("name", nulls = Nulls.SKIP)
  var name: String = ""

  @JsonSetter("profile_picture", nulls = Nulls.SKIP)
  var profilePicture: String? = ""

  @JsonSetter("phone", nulls = Nulls.SKIP)
  var phone: String? = ""

  @JsonSetter("email", nulls = Nulls.SKIP)
  var email: String? = ""

  @JsonSetter("id", nulls = Nulls.SKIP)
  var id: String = ""
  override fun toString(): String =
    "SocialUserData(firstName=$firstName, lastName=$lastName, name=$name, profilePicture=$profilePicture, phone=$phone, email=$email, id=$id)"

}