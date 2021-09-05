package models

import java.util.*

data class GameModel(
    var gameUUID:UUID,
    var gameMaster:GameMaster,
    var playerList:List<GamePlayer>
)
data class GamePlayer(
    val playerUUID: UUID?,
    val foulAmount:Int?,
    val nickName: String?,
    val points: Int?,
    val additionalPoints: Int?,
    val penalties: Int?,
    val bestMove: Int?,
    val victoriesRed: Int?,
    val defeatRed: Int?,
    val victoriesBlack: Int?,
    val defeatBlack: Int?,
    val don: Int?,
    val sheriff: Int?,
    val wasKilled: Int?
)
data class GameMaster(
    val gameMasterUuid:UUID,
    val name:String,
)