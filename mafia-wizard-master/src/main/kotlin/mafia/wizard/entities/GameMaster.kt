package mafia.wizard.entities

import java.util.*
import javax.persistence.*

@Table(name = "game_masters")
@Entity
data class GameMaster(
    @Id
    @Column(name = "game_master_uuid", columnDefinition = "BINARY(16)")
    @GeneratedValue
    var masterUuid: UUID = UUID.randomUUID(),
    @Column(name = "nick_name", unique = true)
    var nickName: String? = null,
    @OneToMany(mappedBy="gameMaster")
    var gameMaster:List<Game> = mutableListOf(),
)