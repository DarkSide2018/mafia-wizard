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
    var masterUuid: UUID?=null,
    @Column(name = "nick_name", unique = true)
    var nickName: String? = null,
    @OneToMany(mappedBy="gameMaster")
    var games:List<Game>?=null,
    @Column(name = "created_at")
    var createdAt: OffsetDateTime? = null,
    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime? = null
)