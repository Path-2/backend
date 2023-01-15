package ao.path2.app.core.domain.mail

import java.time.LocalDateTime

data class Email(val from: Party, val to: Party, val message: String, val timestamp: LocalDateTime)