package mafia.wizard.entities

import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Table(name = "game_masters")
@Entity
data class GameMaster(
    @Id
    @Column(name = "game_master_uuid", columnDefinition = "BINARY(16)")
    @GeneratedValue
    var masterUuid: UUID= UUID.randomUUID(),
    @Column(name = "nick_name", unique = true)
    var nickName: String? = null,
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "game_to_game_masters",
        joinColumns = [JoinColumn(referencedColumnName = "game_master_uuid")],
        inverseJoinColumns = [JoinColumn(referencedColumnName = "game_uuid")]
    )
    var games:MutableList<Game> = mutableListOf(),
    @Column(name = "created_at")
    var createdAt: OffsetDateTime? = null,
    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime? = OffsetDateTime.now()
){
    fun addGame(game: Game){
        games.add(game)
    }

}