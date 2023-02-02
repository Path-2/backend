package ao.path2.ms.email.service

import freemarker.template.Configuration
import freemarker.template.TemplateException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.StringWriter
import javax.mail.internet.MimeMessage

@Service
class EmailServiceImpl(private val configuration: Configuration, private val sender: JavaMailSender) : EmailService {

  override fun sendSimpleEmail() {

  }

  override fun sendAttachedWithAssetsEmail(data: Map<String, Any>, subject: String, to: String, template: String) {
    val mimeMessage: MimeMessage = sender.createMimeMessage();
    val helper = MimeMessageHelper(mimeMessage)
    helper.setSubject(subject);
    helper.setTo(to);
    val emailContent: String = getEmailContent(data, template)
    helper.setText(emailContent, true);
    sender.send(mimeMessage);
  }

  @Throws(IOException::class, TemplateException::class)
  fun getEmailContent(data: Map<String, Any>, template: String): String {
    val stringWriter = StringWriter()
    configuration.getTemplate("$template.ftlh").process(data, stringWriter)
    return stringWriter.buffer.toString()
  }
}