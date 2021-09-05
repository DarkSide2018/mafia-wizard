package mafia.wizard.entities

import com.vladmihalcea.hibernate.type.json.JsonType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
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
    @ManyToOne
    @JoinColumn(name = "game_master_uuid", nullable = false)
    var gameMaster: GameMaster = GameMaster(),

    @Column(name = "game_number")
    var gameNumber: Int = 0,
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    var players: String? = null

)

