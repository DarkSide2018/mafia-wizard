package models.game

import models.PlayerModel
import java.math.BigDecimal
import java.util.*

data class GameModel(
    var gameUUID: UUID,
    var gameNumber: Long?=null,
    var gameTable: String? = null,
    var victory: String? = null,
    var name: String? = null,
    var status: String? = null,
    var playerToCardNumber: MutableSet<PlayerToCardNumber>? = mutableSetOf<PlayerToCardNumber>(),
    var players: MutableSet<PlayerModel> = mutableSetOf<PlayerModel>(),
    var nights: MutableSet<Night>? = mutableSetOf(),
    var elections: MutableSet<Election> = mutableSetOf<Election>(),

    ) {
    fun addPlayer(value: PlayerModel) {
        players.add(value)
    }
}

data class Election(
    var electionId:UUID?=null,
    var sortOrder:Int?=null,
    var slot: Int? = null,
    var playerUuid: UUID? = null,
    var playerName: String? = null,
    var numberOfVotes: Int? = null,
) {

}

data class Night(
    var nightNumber: Int? = null,
    var killedPlayer: UUID? = null,
    var sheriffChecked: UUID? = null,
    var donChecked: UUID? = null,
    var playerLeftGame: UUID? = null,
)

data class PlayerToCardNumber(
    var playerUuid: java.util.UUID? = null,
    var cardNumber: kotlin.Int? = null,
    var gameRole: kotlin.String? = null,
    var note: kotlin.Int? = null,
    var addPoints: BigDecimal? = null,
)
