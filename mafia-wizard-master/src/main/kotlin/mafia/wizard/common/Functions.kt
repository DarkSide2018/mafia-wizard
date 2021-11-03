package mafia.wizard.common

import mafia.wizard.config.NotFoundException
import mafia.wizard.entities.User
import org.springframework.security.core.context.SecurityContextHolder

fun getActorName(): String {
    val user = SecurityContextHolder.getContext().authentication.principal as User
    return user.userName ?: throw NotFoundException("empty Username")
}