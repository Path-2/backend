package ao.path2.ms.forgot.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.*

class JwtToken() {
  constructor(secret: String) : this() {
    this.secret = secret
  }

  private var secret: String =
    ""
  private var expirationInMillis = 43_200_000
  private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8));

  fun generateToken(
    username: String,
    claims: Map<String, Any>? = mapOf()
  ): String {
    val builder = Jwts.builder()
      .setSubject(username)
      .setIssuer("path2.ms.forgot")
      .setAudience("path2.ms.forgot")
      .setIssuedAt(Date())
      .setId(Random(System.currentTimeMillis()).nextLong().toString())
      .setExpiration(expireAt(expirationInMillis))
      .signWith(key, SignatureAlgorithm.HS512)
      .addClaims(claims)

    return builder.compact()
  }

  private fun expireAt(expiration: Int) = Date(System.currentTimeMillis() + expiration)

  private fun getClaims(token: String): Claims {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
  }

  fun getUsername(token: String): String = getClaims(token).subject

  fun getCustomClaim(token: String, claim: String): Any? = getClaims(token)[claim]

  fun isTokenValid(token: String): Boolean {
    try {
      val claims = getClaims(token)
      val username = claims.subject
      val expirationDate = claims.expiration
      val now = Date(System.currentTimeMillis())
      if (username != null && expirationDate != null && now.before(expirationDate)) {
        return true
      }
    } catch (_: Exception) {
    }

    return false
  }
}