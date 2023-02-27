package ao.path2.ms.user.core.exceptions

class UnSupportedSocialNetworking(private val social: String): RuntimeException("Social networking unsupported ($social)") {

  constructor() : this("Social networking unsupported") {
  }
}