package models

data class PlayerModel(
    val ratingId:Int?,
    val foulAmount:Int?,
    val nickName: String?,
    val points: Int?,
    val additionalPoints: Int?,
    val penalties: Int?,
    val bestMove: Int?,
    val victories: Int?,
    val victoriesPercent: Int?,
    val victoriesRed: Int?,
    val victoriesRedPercent: Int?,
    val defeatRed: Int?,
    val victoriesBlack: Int?,
    val defeatBlack: Int?,
    val victoriesBlackPercent: Int?,
    val don: Int?,
    val sheriff: Int?,
    val wasKilled: Int?,
    val games: Int?,
    val rating: Double?
)