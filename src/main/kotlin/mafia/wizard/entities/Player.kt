package mafia.wizard.entities

import java.time.OffsetDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "players")
@Entity
data class Player(
    @Id
    var uuid: UUID = UUID.randomUUID(),
    var nickName: String? = null,
    var points: Long = 0,
    var additionalPoints: Long = 0,
    var penalties: Long = 0,
    var lxPoints: Long = 0,
    var victories: Long = 0,
    var don: Long = 0,
    var sheriff: Long = 0,
    var wasKilled: Long = 0,
    var games: Long = 0,
    var koef: Double = 0.0,
    var rating: Double = 0.0,
    @Column(name = "CREATED_ON")
    var createdAt: OffsetDateTime? = null,
    @Column(name = "UPDATED_ON")
    var updatedAt: OffsetDateTime? = null,
) {
}