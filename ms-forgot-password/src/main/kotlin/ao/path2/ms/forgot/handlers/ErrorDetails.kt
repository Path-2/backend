package ao.path2.ms.forgot.handlers

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorDetails(
  val statusCode: Int = 500,
  val message: String = "Internal server error",
  val timestamp: LocalDateTime = LocalDateTime.now(),
  val cause: String?
)
