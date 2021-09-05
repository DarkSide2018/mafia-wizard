package mafia.wizard.repository

import mafia.wizard.entities.GameMaster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GameMasterRepository : JpaRepository<GameMaster, UUID> {
}