package models.game

import com.fasterxml.jackson.annotation.JsonProperty
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
    var playerLeftGame: List<UUID>?=null,
)

data class PlayerToCardNumber(
    val playerUuid: java.util.UUID? = null,
    val cardNumber: kotlin.Int? = null,
    val gameRole: kotlin.String? = null,
)
