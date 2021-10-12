package models.game

import models.PlayerModel
import java.util.*

data class GameModel(
    var gameUUID: UUID,
    var gameNumber: Long?,
    var gameTable: String? = null,
    var victory: String? = null,
    var name: String? = null,
    var status: String? = null,
    var playerToCardNumber: MutableSet<PlayerToCardNumber>? = mutableSetOf<PlayerToCardNumber>(),
    var players: MutableSet<PlayerModel> = mutableSetOf<PlayerModel>(),
    var nights: MutableSet<Night>? = mutableSetOf(),
) {
    fun addPlayer(value: PlayerModel) {
        players.add(value)
    }
}

data class Night(
    var nightNumber:Int?=null,
    var killedPlayer: UUID?=null,
    var sheriffChecked: UUID?=null,
    var donChecked: UUID?=null,
    var playerLeftGame: UUID?=null,
)

data class PlayerToCardNumber(
    var playerUuid: java.util.UUID? = null,
    var cardNumber: kotlin.Int? = null,
    var gameRole: kotlin.String? = null,
    var note:Int?=null,
)
