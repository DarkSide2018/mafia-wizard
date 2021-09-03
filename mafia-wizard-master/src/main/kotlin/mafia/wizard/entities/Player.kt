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
    val playerUuid: UUID=UUID.randomUUID(),
    //1. Порядковый номер в рейтинге +
    var ratingId:Int = 0,
    //2. Количество фолов +
    var foulAmount:Int=0,
    //3. Игровой Ник +
    var nickName: String? = null,
    //4. Сумма баллов +
    var points: Int = 0,
    //5. Дополнительные баллы за игру (сумма) +
    var additionalPoints: Int = 0,
    //6. Штрафные баллы (сумма) +
    var penalties: Int = 0,
    //7. Лучший ход я(сумма) +
    var bestMove: Int = 0,
    //8. Число побед всего +
    var victories: Int = 0,
    //9. Процент побед
    var victoriesPercent: Int = 0,
    //10. Количество побед за красных +
    var victoriesRed: Int = 0,
    //11. Процент побед за красных +
    var victoriesRedPercent: Int = 0,
    //12. Количество поражений за красных +
    var defeatRed: Int = 0,
    //13. Количество побед за чёрных +
    var victoriesBlack: Int = 0,
    //14. Количество поражений за чёрных +
    var defeatBlack: Int = 0,
    //15. Процент побед за черных +
    var victoriesBlackPercent: Int = 0,
    //16. Количество побед за Дона +
    var don: Int = 0,
    //17. Количество побед за шерифа +
    var sheriff: Int = 0,
    //18. Количество первых отстрелов +
    var wasKilled: Int = 0,
    //19. Число игр всего +
    var games: Int = 0,
    //20. Итоговый рейтинг (процент побед умножить на сумму баллов)
    var rating: Double = 0.0,
    @Column(name = "CREATED_AT")
    var createdAt: OffsetDateTime? = null,
    @Column(name = "UPDATED_AT")
    var updatedAt: OffsetDateTime? = null,
) {
}




















