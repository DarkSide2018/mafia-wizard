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
    fun findByNickName(userName: String): Player?

    @Query("FROM Player p WHERE p.nickName LIKE %:searchText% ORDER BY p.createdAt DESC ")
    fun findByNickName(page:Pageable, @Param("searchText") searchText: String): Page<Player>?

//    fun findAllBy(page: Pageable): Page<Player>?
}