package models

import java.util.*

data class PlayerModel(
    var playerUUID: UUID?=null,
    val ratingId:Long?=null,
    val foulAmount:Long?=null,
    val nickName: String?=null,
    val points: Long?=null,
    val additionalPoints: Long?=null,
    val penalties: Long?=null,
    val bestMove: Long?=null,
    val victories: Long?=null,
    val victoriesPercent: Long?=null,
    val victoriesRed: Long?=null,
    val victoriesRedPercent: Long?=null,
    val defeatRed: Long?=null,
    val victoriesBlack: Long?=null,
    val defeatBlack: Long?=null,
    val victoriesBlackPercent: Long?=null,
    val don: Long?=null,
    val sheriff: Long?=null,
    val wasKilled: Long?=null,
    val games: Long?=null,
    val rating: Double?=null
)