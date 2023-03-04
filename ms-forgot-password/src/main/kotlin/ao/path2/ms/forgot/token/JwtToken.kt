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
    "R8zwvESWo5YEpRZ8dgkS9UYUTvkrfNc6qNmNesyQD1mhwrmKyh2UoNtcnwjQdZ4c7dUQl7X1kB9thEf9caP1fRZexqOWHW2hAx2stUHRB5x7r8PikDe6Qzz1FqvUFhGR5riwyz7X5vh9EZW3qGWLAZ5ctM3Irh7ZCa6HB2WuiNQWqPQcRzXi18sbFgYwrTnCTyKvefCWbGa1d4EiEODJwcUJBk0taEiWbQFrm3X9eEp2HnIOI2xYNSDKTFOSBLNXHraMREuw9TU5Df8E7ngIxuD0hmWoL1Bs0UmOm8krjsknMQNzD76Fw4uaipTA6hKYfDWM1RjUQuvLyvpSvyWIBVwxvLwTMIYQ8qiA6oO0pA3rCkg13Y05k62LbCXZ7APcAs2NJ7inEUkyh8LLWRoWUsgFz3nTUJTnjVSz8feuzAVL4NaHvOCzUZt4cDex9f1f9xe7o6PlwSp89x4D0qk3DDRUGsW5DGZJ7N1cJr6lY8RNMOBiEheKT2YalJRl2zopY03c0BIXDElsHDc0Lec5JcQX3HlL0cbISLsoeYoBOkQee4zY8f1fH5mEZRPynWVGxVnMr8clUT0f1fRvRTfh0EpVeKVgEnLrtrK8qwoaMrLXxAKlKGT0u42yfHR838DKAD9jPqRJF1"
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