package mafia.wizard.repository

import mafia.wizard.entities.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlayerRepo: JpaRepository<Player, UUID> {
    fun findByNickName(userName: String): Player

}