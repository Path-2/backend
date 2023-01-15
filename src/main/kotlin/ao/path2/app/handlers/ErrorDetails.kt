package ao.path2.app.handlers

import java.time.LocalDateTime

data class ErrorDetails(val statusCode: Int, val message: String?, val timestamp: LocalDateTime, val cause: String?)
