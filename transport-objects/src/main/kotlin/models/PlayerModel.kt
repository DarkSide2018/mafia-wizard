package models

import java.util.*

data class PlayerModel(
    var playerUUID: UUID? = null,
    var ratingId: Long? = null,
    var foulAmount: Long? = null,
    var nickName: String? = null,
    var points: Long? = null,
    var additionalPoints: Long? = null,
    var penalties: Long? = null,
    var bestMove: Long? = null,
    var victories: Long? = null,
    var victoriesPercent: Long? = null,
    var victoriesRed: Long? = null,
    var victoriesRedPercent: Long? = null,
    var defeatRed: Long? = null,
    var victoriesBlack: Long? = null,
    var defeatBlack: Long? = null,
    var status: String? = null,
    var victoriesBlackPercent: Long? = null,
    var don: Long? = null,
    var sheriff: Long? = null,
    var wasKilled: Long? = null,
    var games: Long? = null,
    var remark:Long?=null,
    var rating: Double? = null,
)