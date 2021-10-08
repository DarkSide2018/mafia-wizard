package models.game

import com.fasterxml.jackson.annotation.JsonProperty
import models.PlayerModel
import java.util.*

data class GameModel(
    var gameUUID: UUID,
    var gameNumber: Long?,
    var gameTable: String? = null,
    var victory: String? = null,
    var don: UUID? = null,
    var sheriff: UUID? = null,
    var criminalOne: UUID? = null,
    var criminalTwo: UUID? = null,
    var name: String? = null,
    var status: String? = null,
    var players: MutableSet<PlayerModel> = mutableSetOf<PlayerModel>(),
    var nights: MutableSet<Night>?= mutableSetOf(),
) {
    fun addPlayer(value: PlayerModel) {
        players.add(value)
    }
}

data class Night(
    var killedPlayer: UUID?,
    var sheriffChecked: UUID?,
    var donChecked: UUID?,
    var playerLeftGame: List<UUID>?,
    var playerToCardNumber: List<PlayerToCardNumber>?,
)

data class PlayerToCardNumber(
    val playerUuid: java.util.UUID? = null,
    val cardNumber: kotlin.Int? = null,
)
