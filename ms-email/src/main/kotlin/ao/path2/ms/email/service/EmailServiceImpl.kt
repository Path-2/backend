package ao.path2.ms.email.service

import freemarker.template.Configuration
import freemarker.template.TemplateException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import java.io.IOException
import java.io.StringWriter
import javax.mail.internet.MimeMessage


class EmailServiceImpl(private val configuration: Configuration, private val sender: JavaMailSender): EmailService {

  override fun sendSimpleEmail() {

  }

  override fun sendAttachedWithAssetsEmail() {
    val mimeMessage: MimeMessage = sender.createMimeMessage();
    val helper = MimeMessageHelper(mimeMessage)
    helper.setSubject("Welcome To SpringHow.com");
    helper.setTo("");
    val emailContent: String  = getEmailContent("")
    helper.setText(emailContent, true);
    sender.send(mimeMessage);
  }

  @Throws(IOException::class, TemplateException::class)
  fun getEmailContent(user: String): String {
    val stringWriter = StringWriter()
    val model: MutableMap<String, Any> = HashMap()
    model["user"] = user
    configuration.getTemplate("email.ftlh").process(model, stringWriter)
    return stringWriter.buffer.toString()
  }
}