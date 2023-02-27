package ao.path2.ms.user.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("#username == authentication.principal.username and #user.username == authentication.principal.username or hasRole('ROLE_ADMIN')")
annotation class AuthorizeOwner()
