package models.game

import java.util.*

data class GameModel(
    var gameUUID:UUID,
    var gameNumber:Int?,
    var players:List<GamePlayer>?=null
)
data class GamePlayer(
    val playerUUID: UUID,
    val foulAmount:Int?=null,
    val nickName: String?=null,
    val points: Int?=null,
    val additionalPoints: Int?=null,
    val penalties: Int?=null,
    val bestMove: Int?=null,
    val victoriesRed: Int?=null,
    val defeatRed: Int?=null,
    val victoriesBlack: Int?=null,
    val defeatBlack: Int?=null,
    val don: Int?=null,
    val sheriff: Int?=null,
    val wasKilled: Int?=null
)
