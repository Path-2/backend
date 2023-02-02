package ao.path2.ms.email.service

interface EmailService {
  fun sendSimpleEmail()
  fun sendAttachedWithAssetsEmail(data: Map<String, Any>, subject: String, to: String, template: String)
}