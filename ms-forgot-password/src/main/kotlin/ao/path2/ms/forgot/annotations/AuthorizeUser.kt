package ao.path2.ms.forgot.annotations

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
annotation class AuthorizeUser()
