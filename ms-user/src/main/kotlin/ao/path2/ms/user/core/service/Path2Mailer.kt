package ao.path2.ms.user.core.service

import ao.path2.ms.user.core.domain.mail.Email

interface Path2Mailer {
  fun send(email: Email): Boolean
}