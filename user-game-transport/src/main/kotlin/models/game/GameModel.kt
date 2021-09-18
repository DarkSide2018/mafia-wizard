package models.game

import java.util.*

data class GameModel(
    var gameUUID:UUID,
    var gameNumber:Long?,
    var players:List<GamePlayer>?=null
)
data class GamePlayer(
    val playerUUID: UUID,
    val foulAmount:Long?=null,
    val nickName: String?=null,
    val points: Long?=null,
    val additionalPoints: Long?=null,
    val penalties: Long?=null,
    val bestMove: Long?=null,
    val victoriesRed: Long?=null,
    val defeatRed: Long?=null,
    val victoriesBlack: Long?=null,
    val defeatBlack: Long?=null,
    val don: Long?=null,
    val sheriff: Long?=null,
    val wasKilled: Long?=null
)
