package mafia.wizard.repository

import mafia.wizard.entities.Game
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GameRepository: JpaRepository<Game, UUID> {
    @Query("SELECT * FROM wizard.games g WHERE g.created_by=:createdBy and g.status=:gameStatus ORDER BY g.updated_at DESC"
        ,nativeQuery = true)
    fun getDraftGame(page: Pageable,
                     @Param("createdBy") createdBy:String,
                     @Param("gameStatus") gstatus:String): Page<Game?>

    fun findAllByCreatedByOrderByCreatedAtDesc(createdBy: String,page: Pageable):Page<Game>
}