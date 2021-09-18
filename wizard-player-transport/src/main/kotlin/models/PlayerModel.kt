package models

import java.util.*

data class PlayerModel(
    var playerUUID: UUID,
    val ratingId:Long?,
    val foulAmount:Long?,
    val nickName: String?,
    val points: Long?,
    val additionalPoints: Long?,
    val penalties: Long?,
    val bestMove: Long?,
    val victories: Long?,
    val victoriesPercent: Long?,
    val victoriesRed: Long?,
    val victoriesRedPercent: Long?,
    val defeatRed: Long?,
    val victoriesBlack: Long?,
    val defeatBlack: Long?,
    val victoriesBlackPercent: Long?,
    val don: Long?,
    val sheriff: Long?,
    val wasKilled: Long?,
    val games: Long?,
    val rating: Double?
)