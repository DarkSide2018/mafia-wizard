package mafia.wizard.repository

import mafia.wizard.entities.Player
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

import java.util.*

@Repository
interface PlayerRepo: JpaRepository<Player, UUID> {

    fun findByNickNameAndCreatedBy(userName: String,createdBy:String): Player?

    @Query("FROM Player p WHERE p.nickName LIKE %:searchText% and p.status = 'FREE' and p.createdBy = :actor ORDER BY p.createdAt DESC ")
    fun findByNickName(page:Pageable,
                       @Param("actor") actor: String,
                       @Param("searchText") searchText: String): Page<Player>?

    fun findAllByStatusAndCreatedByOrderByRating(page: Pageable,status:String,createdBy:String):Page<Player>

}