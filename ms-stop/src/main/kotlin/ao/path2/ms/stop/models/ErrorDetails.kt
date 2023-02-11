package ao.path2.core.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
data class ErrorDetails(val statusCode: Int, val message: String?, val timestamp: LocalDateTime, val cause: String?)
