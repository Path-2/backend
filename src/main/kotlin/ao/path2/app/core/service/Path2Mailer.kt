package ao.path2.app.core.service

import ao.path2.app.core.domain.mail.Email

interface Path2Mailer {
  fun send(email: Email): Boolean
}