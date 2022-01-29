package models.game

import models.PlayerModel
import java.math.BigDecimal
import java.util.*

data class GameModel(
    var gameUUID: UUID,
    var createdBy:String?=null,
    var gameNumber: Long? = null,
    var gameTable: String? = null,
    var victory: String? = null,
    var name: String? = null,
    var status: String? = null,
    var playerToCardNumber: MutableSet<PlayerToCardNumber> = mutableSetOf<PlayerToCardNumber>(),
    var players: MutableSet<PlayerModel>? = mutableSetOf<PlayerModel>(),
    var nights: MutableSet<Night>? = mutableSetOf(),
    var elections: MutableSet<Election>? = mutableSetOf<Election>(),

    ) {
    fun addPlayer(value: PlayerModel) {
        players?.add(value)
    }

    fun addPlayerSlotRelation(value: PlayerModel, slot: Int) {
        val firstEntry = playerToCardNumber?.firstOrNull {
            it.cardNumber == slot
        }
        firstEntry?.let {
            it.playerUuid = value.playerUUID
            it.playerNickName = value.nickName
        }
        if (firstEntry == null) {
            playerToCardNumber?.add(PlayerToCardNumber(
                playerUuid = value.playerUUID,
                playerNickName = value.nickName,
                cardNumber = slot
            ))
        }
    }
}

class Election(
    var electionId: UUID? = null,
    var sortOrder: Int? = null,
    var drops: List<ElectionDropDownBusiness>? = mutableListOf(),
)

data class ElectionDropDownBusiness(
    val slot: Int? = null,
    val playerUuid: UUID? = null,
    val playerName: String? = null,
    val numberOfVotes: Int? = null,
)

data class Night(
    var nightNumber: Int? = null,
    var killedPlayer: Int? = null,
    var sheriffChecked: Int? = null,
    var donChecked: Int? = null,
    var playerLeftGame: Int? = null,
)

data class PlayerToCardNumber(
    var playerUuid: java.util.UUID? = null,
    var playerNickName: String? = null,
    var cardNumber: kotlin.Int? = null,
    var gameRole: kotlin.String? = null,
    var note: kotlin.Int? = null,
    var addPoints: BigDecimal? = null,
)
