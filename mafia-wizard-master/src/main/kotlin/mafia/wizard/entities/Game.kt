package mafia.wizard.entities

import com.vladmihalcea.hibernate.type.json.JsonType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*


@Table(name = "games")
@Entity
@TypeDef(
    name = "json",
    typeClass = JsonType::class
)
data class Game(
    @Id
    @Column(name = "game_uuid", columnDefinition = "BINARY(16)")
    @GeneratedValue
    var gameUUID: UUID = UUID.randomUUID(),
    @ManyToMany(mappedBy = "games")
    var gameMaster: List<GameMaster> = mutableListOf(),
    @Column(name = "game_number")
    var gameNumber: Long = 0,

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    var players: String? = null,

    @Column(name = "created_at")
    var createdAt: OffsetDateTime? = null,

    @Column(name = "updated_at")
    var updatedAt: OffsetDateTime? = OffsetDateTime.now(),
) {

}

