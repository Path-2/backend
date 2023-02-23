package ao.path2.ms.auth.handlers

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorDetails(val statusCode: Int, val message: String?, val timestamp: LocalDateTime, val cause: String?)
