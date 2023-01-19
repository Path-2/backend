package ao.path2.ms.user.utils.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtTokenUtil {

  //@Value("\${jwt.secret}")
  private var secret: String =
    "uNDUnhBpCtYu1mqiwE7kFK6kAxNylztdqQqiCXuwjB0mgTAsbcoiqr82WnxUnSDgzf9OT1Ks4fveKb3VoMfrmcUBEytV6CLJFB9K8PLtZviTZCXztY9aypGT8yHrtm2BHot7rHyWQysSMLydm4hTWWb8P2Lvos54fmiw2HH41doSUvl2D499vqWt8mgfG2tUC7okkH7A00yrIV6yTihKJDaqZyPrdGNl22GceROyNpW5IWFxDDF6YSEQQQ1MswfDwKjVyFFbCuHcBbK3y1WNow2Q7M19np5z7HO80IglFPBTuTFy2vl6uGI4npuBi5POqJdwfIUpW4uLFfg4byvnvOx970gtfJg2IuaqL5A8fzOTNA0uep1jaotYwliKfLdfUNuxohQ0Ln8chPxI3Vw2DEruH1jRvXMXwU3OcVuOCLnf5H9JShNZFZoISAtwH2CZtlbNXxgWp1kYoeBKpwFGOy1HJ7gwYtxQJpUQMjQUySBaQ36Y7ZLnVmNTzAGwhrBA87nEG7hQnZVfq3ZPlYkfuftkmnLOceAdWeEFBOrvJr508JPJa5JBVic8KYaJFr6s5Zw9nI0oRCAoOFIFZXnbq7pXFvSpfTh10Kv2y1XwD9c8wdlmCtTpXqCzExMb8GJnKbAG6HWoCB4ooDSSNtV6eSWr6qvJbG9rTKoGmHP3jo3tW66gh9CauJDamYRU5QqSgEYhdHwEoEFMzWGPTPUDh26H7IeQ8Qucksa9dyWTvZDApwmyjYM71ESp4qAvqqh0jxGQdDZZF"
  private val expiration = 24 * 60 * 1000
  private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8));
  fun generateToken(username: String, vararg roles: String): String {
    val builder = Jwts.builder()
      .setSubject(username)
      .setExpiration(expireAt(expiration))
      .signWith(key, SignatureAlgorithm.HS512)

    if (roles.isNotEmpty())
      builder.claim("roles", roles.joinToString())

    return builder.compact()
  }

  private fun expireAt(expiration: Int): Date = Date(System.currentTimeMillis() + expiration)

  private fun getClaims(token: String): Claims {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
  }

  fun getEmail(token: String): String = getClaims(token).subject

  fun isTokenValid(token: String): Boolean {
    val claims = getClaims(token)
    val username = claims.subject
    val expirationDate = claims.expiration
    val now = Date(System.currentTimeMillis())
    if (username != null && expirationDate != null && now.before(expirationDate)) {
      return true
    }
    return false
  }
}