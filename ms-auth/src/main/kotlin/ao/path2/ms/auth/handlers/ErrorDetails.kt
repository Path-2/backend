package ao.path2.ms.auth.handlers

import java.time.LocalDateTime

data class ErrorDetails(val statusCode: Int, val message: String?, val timestamp: LocalDateTime, val cause: String?)
