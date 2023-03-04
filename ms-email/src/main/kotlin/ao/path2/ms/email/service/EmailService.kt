package ao.path2.ms.email.service

import ao.path2.ms.email.models.EmailModel

interface EmailService {
  fun sendSimpleEmail()
  fun sendAttachedWithAssetsEmail(emailModel: EmailModel)
}