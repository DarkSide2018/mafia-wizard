package mafia.wizard.repository

import mafia.wizard.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDetailsRepo: JpaRepository<User, Long> {
    fun findByUserName(userName: String): User
}