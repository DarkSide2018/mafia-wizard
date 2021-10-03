package mafia.wizard.entities

import com.vladmihalcea.hibernate.type.json.JsonType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*
const val DRAFT_STATUS = "DRAFT"

@Table(name = "games")
@Entity
@TypeDef(
    name = "json",
    typeClass = JsonType::class
)
data class Game(
    @Id
    @Column(name = "game_uuid", columnDefinition = "BINARY(16)")
    var gameUUID: UUID?=null,
    @ManyToMany(mappedBy = "games")
    var gameMaster: List<GameMaster> = mutableListOf(),
    @Column(name = "game_number")
    var gameNumber: Long? = null,
    @Column(name = "name")
    var name: String?=null,
    @Column(name = "status")
    var status: String?= DRAFT_STATUS,
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    var players: String? = null,
    @Column(name = "created_at")
    var createdAt: OffsetDateTime? = null,
    @Column(name = "created_by")
    var createdBy: String? = null,
    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime? = OffsetDateTime.now(),
) {

}

