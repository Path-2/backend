package ao.path2.app.utils.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil {

  @Value("\${jwt.secret}")
  private lateinit var secret: String
  private val expiration = 6 * 1000 * 1000

  fun generateToken(username: String): String =
    Jwts.builder().setSubject(username).setExpiration(Date(System.currentTimeMillis() + expiration))
      .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512)).compact()

  private fun getClaims(token: String) =
    Jwts.parserBuilder().setSigningKey(secret.toByteArray()).build().parseClaimsJws(token).body

  fun getEmail(token: String): String = getClaims(token).subject

  fun isTokenValid(token: String): Boolean {
    val claims = getClaims(token)
    val expirationDate = claims.expiration
    val now = Date(System.currentTimeMillis())
    return now.before(expirationDate)
  }
}