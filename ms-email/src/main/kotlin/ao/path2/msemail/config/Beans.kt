@Configuration
class Beans {

    @Value("\${secrets.password}")
    lateinit var password

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()

        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587

        mailSender.username = "path2app@gmail.com"
        mailSender.password = "whobszogylonjthz"

        val props = mailSender.getJavaMailProperties()

        props.put("mail.transport.protocol", "smtp")
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.debug", "true")
    }

    @Bean
    fun velocityEngine(): VelocityEngine {
        val factory = VelocityEngineFactory()
        val props = Properties()
        
        props.put("resource.loader", "class")
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader")

        return factory.createVelocityEngine(props);
    }
}