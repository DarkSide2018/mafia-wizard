package mafia.wizard.entities


import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Table(name = "players")
@Entity
data class Player(
    @Id
    @Column(name = "player_uuid", columnDefinition = "BINARY(16)")
    @GeneratedValue
    val playerUuid: UUID = UUID.randomUUID(),
    //1. Порядковый номер в рейтинге +
    var ratingId: Long = 0,

    var status: String = "FREE",

    var image: ByteArray? = null,
    //2. Количество фолов +
    var foulAmount: Long = 0,
    //3. Игровой Ник +
    var nickName: String? = null,
    var createdBy: String? = null,
    var updatedBy: String? = null,
    //4. Сумма баллов +
    var points: Long = 0,
    //5. Дополнительные баллы за игру (сумма) +
    var additionalPoints: Long = 0,
    //6. Штрафные баллы (сумма) +
    var penalties: Long = 0,
    //7. Лучший ход я(сумма) +
    var bestMove: Long = 0,
    //8. Число побед всего +
    var victories: Long = 0,
    //9. Процент побед
    var victoriesPercent: Long = 0,
    //10. Количество побед за красных +
    var victoriesRed: Long = 0,
    //11. Процент побед за красных +
    var victoriesRedPercent: Long = 0,
    //12. Количество поражений за красных +
    var defeatRed: Long = 0,
    //13. Количество побед за чёрных +
    var victoriesBlack: Long = 0,
    //14. Количество поражений за чёрных +
    var defeatBlack: Long = 0,
    //15. Процент побед за черных +
    var victoriesBlackPercent: Long = 0,
    //16. Количество побед за Дона +
    var don: Long = 0,
    //17. Количество побед за шерифа +
    var sheriff: Long = 0,
    //18. Количество первых отстрелов +
    var wasKilled: Long = 0,
    //19. Число игр всего +
    var games: Long = 0,
    //20. Итоговый рейтинг (процент побед умножить на сумму баллов)
    var rating: Double = 0.0,
    @Column(name = "CREATED_AT")
    var createdAt: OffsetDateTime? = null,
    @Column(name = "UPDATED_AT")
    var updatedAt: OffsetDateTime? = null,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (playerUuid != other.playerUuid) return false
        if (ratingId != other.ratingId) return false
        if (status != other.status) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (foulAmount != other.foulAmount) return false
        if (nickName != other.nickName) return false
        if (points != other.points) return false
        if (additionalPoints != other.additionalPoints) return false
        if (penalties != other.penalties) return false
        if (bestMove != other.bestMove) return false
        if (victories != other.victories) return false
        if (victoriesPercent != other.victoriesPercent) return false
        if (victoriesRed != other.victoriesRed) return false
        if (victoriesRedPercent != other.victoriesRedPercent) return false
        if (defeatRed != other.defeatRed) return false
        if (victoriesBlack != other.victoriesBlack) return false
        if (defeatBlack != other.defeatBlack) return false
        if (victoriesBlackPercent != other.victoriesBlackPercent) return false
        if (don != other.don) return false
        if (sheriff != other.sheriff) return false
        if (wasKilled != other.wasKilled) return false
        if (games != other.games) return false
        if (rating != other.rating) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = playerUuid.hashCode()
        result = 31 * result + ratingId.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + foulAmount.hashCode()
        result = 31 * result + (nickName?.hashCode() ?: 0)
        result = 31 * result + points.hashCode()
        result = 31 * result + additionalPoints.hashCode()
        result = 31 * result + penalties.hashCode()
        result = 31 * result + bestMove.hashCode()
        result = 31 * result + victories.hashCode()
        result = 31 * result + victoriesPercent.hashCode()
        result = 31 * result + victoriesRed.hashCode()
        result = 31 * result + victoriesRedPercent.hashCode()
        result = 31 * result + defeatRed.hashCode()
        result = 31 * result + victoriesBlack.hashCode()
        result = 31 * result + defeatBlack.hashCode()
        result = 31 * result + victoriesBlackPercent.hashCode()
        result = 31 * result + don.hashCode()
        result = 31 * result + sheriff.hashCode()
        result = 31 * result + wasKilled.hashCode()
        result = 31 * result + games.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        return result
    }
}




















