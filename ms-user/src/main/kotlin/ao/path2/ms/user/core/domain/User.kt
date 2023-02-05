package ao.path2.ms.user.core.domain

class User() {
  var id: Long = 0
  var username: String = ""
  var name: String = ""
  var image: String = ""
  var phone: String = ""
  var email: String = ""
  var password: String = ""
  var facebookId: String = ""
  var roles: List<Role> = listOf()
}