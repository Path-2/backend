package ao.path2.ms.email.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class Beans {

  //@Value("\${secrets.password}")
  //lateinit var password

  @Bean
  fun getJavaMailSender(): JavaMailSender {
    val mailSender = JavaMailSenderImpl()

    mailSender.host = "smtp.gmail.com"
    mailSender.port = 587

    mailSender.username = "path2app@gmail.com"
    mailSender.password = "whobszogylonjthz"

    val props = mailSender.javaMailProperties

    props["mail.transport.protocol"] = "smtp"
    props["mail.smtp.auth"] = "true"
    props["mail.smtp.starttls.enable"] = "true"
    props["mail.debug"] = "true"

    return mailSender
  }
}