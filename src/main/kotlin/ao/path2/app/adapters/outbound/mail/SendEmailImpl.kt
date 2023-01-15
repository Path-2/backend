package ao.path2.app.adapters.outbound.mail

import ao.path2.app.core.domain.mail.Email
import ao.path2.app.core.service.Path2Mailer
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component
import java.util.*
import java.util.logging.Logger

@Component
class SendEmailImpl() : Path2Mailer {
  private val log: Logger = Logger.getLogger("mail")

  override fun send(email: Email): Boolean {
    val message = SimpleMailMessage()

    val calendar = Calendar.getInstance()

    calendar.set(
      email.timestamp.year,
      email.timestamp.month.value,
      email.timestamp.dayOfMonth,
      email.timestamp.hour,
      email.timestamp.minute,
      email.timestamp.second
    )
    log.info("Building email...")
    message.from = email.from.email
    message.setTo(email.to.email)
    message.text = email.message
    message.sentDate = calendar.time


    try {
      log.info("Sending email...")
     // mailer.send(message)
    } catch (ex: MailException) {
      log.severe("Fail to send email...")
      return false
    }

    return true
  }
}