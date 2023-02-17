package ao.path2.ms.auth.utils.security

fun getFacebookAuthURL(token: String) =
  "https://graph.facebook.com/me?access_token=${token}&fields=id,name,last_name,first_name"

fun getGoogleAuthURL() =
  "https://openidconnect.googleapis.com/v1/userinfo"