package mafia.wizard.repository

import mafia.wizard.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserDetailsRepo: JpaRepository<User, UUID> {
    fun findByUserName(userName: String): User
}