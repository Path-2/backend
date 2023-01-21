package ao.path2.ms.user.handlers

import java.time.LocalDateTime

data class ErrorDetails(val statusCode: Int, val message: String?, val timestamp: LocalDateTime, val cause: String?)
