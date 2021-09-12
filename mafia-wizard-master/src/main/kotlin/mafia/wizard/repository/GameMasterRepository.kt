package mafia.wizard.repository

import mafia.wizard.entities.GameMaster
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GameMasterRepository : JpaRepository<GameMaster, UUID> {

    @Query(value = "SELECT gm FROM GameMaster gm ORDER BY gm.masterUuid")
    fun findAllWithPagination(pageable:Pageable):List<GameMaster>
}