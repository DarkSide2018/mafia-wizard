package mafia.wizard.repository

import mafia.wizard.entities.Game
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GameRepository: JpaRepository<Game, UUID> {
}