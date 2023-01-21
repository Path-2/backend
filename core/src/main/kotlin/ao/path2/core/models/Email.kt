package ao.path2.core.models

import java.time.LocalDateTime

data class Email(val from: Party, val to: Party, val message: String, val timestamp: LocalDateTime)