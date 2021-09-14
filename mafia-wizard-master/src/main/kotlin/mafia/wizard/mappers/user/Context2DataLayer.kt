package mafia.wizard.mappers.user

import mafia.wizard.entities.Player
import models.CreatePlayerContext
import models.UpdatePlayerContext
import java.lang.Exception
import java.time.OffsetDateTime
import java.util.*

fun CreatePlayerContext.toPlayerEntity(): Player {
    val playerModelNotNull = this.playerModel ?: throw Exception("CreatePlayerContext null player model")
    return Player(
        playerUuid = UUID.randomUUID(),
        ratingId = playerModelNotNull.ratingId ?:throw Exception("empty ratingId"),
        foulAmount = playerModelNotNull.foulAmount?:throw Exception("empty foulAmount"),
        nickName = playerModelNotNull.nickName?:throw Exception("empty nickName"),
        points = playerModelNotNull.points?:throw Exception("empty points"),
        additionalPoints = playerModelNotNull.additionalPoints?:throw Exception("empty additionalPoints"),
        penalties= playerModelNotNull.penalties?:throw Exception("empty penalties"),
        bestMove = playerModelNotNull.bestMove?:throw Exception("empty bestMove"),
        victories = playerModelNotNull.victories?:throw Exception("empty victories"),
        victoriesPercent = playerModelNotNull.victoriesPercent?:throw Exception("empty victoriesPercent"),
        victoriesRed = playerModelNotNull.victoriesRed?:throw Exception("empty victoriesRed"),
        victoriesRedPercent = playerModelNotNull.victoriesRedPercent?:throw Exception("empty victoriesRedPercent"),
        defeatRed = playerModelNotNull.defeatRed?:throw Exception("empty defeatRed"),
        victoriesBlack = playerModelNotNull.victoriesBlack?:throw Exception("empty victoriesBlack"),
        defeatBlack = playerModelNotNull.defeatBlack?:throw Exception("empty defeatBlack"),
        victoriesBlackPercent = playerModelNotNull.victoriesBlackPercent?:throw Exception("empty victoriesBlackPercent"),
        don = playerModelNotNull.don?:throw Exception("empty don"),
        sheriff = playerModelNotNull.sheriff?:throw Exception("empty sheriff"),
        wasKilled = playerModelNotNull.wasKilled?:throw Exception("empty wasKilled"),
        games = playerModelNotNull.games?:throw Exception("empty games"),
        rating = playerModelNotNull.rating?:throw Exception("empty rating"),
        createdAt = OffsetDateTime.now(),
        updatedAt = OffsetDateTime.now()
    )
}

fun UpdatePlayerContext.toPlayerEntity(playerToUpdate:Player): Player {
    val playerModelNotNull = this.playerModel?:throw Exception("UpdatePlayerContext null player model")
    with(playerModelNotNull){
        ratingId?.let { playerToUpdate.ratingId = it}
        foulAmount?.let { playerToUpdate.foulAmount = it}
       nickName?.let { playerToUpdate.nickName = it}
       additionalPoints?.let { playerToUpdate.additionalPoints = it}
       points?.let { playerToUpdate.points = it}
       penalties?.let { playerToUpdate.penalties = it}
       bestMove?.let { playerToUpdate.bestMove = it}
       victories?.let { playerToUpdate.victories = it}
       victoriesPercent?.let { playerToUpdate.victoriesPercent = it}
        victoriesRed?.let { playerToUpdate.victoriesRed = it}
        victoriesRedPercent?.let { playerToUpdate.victoriesRedPercent = it}
        defeatRed?.let { playerToUpdate.defeatRed = it}
        victoriesBlack?.let { playerToUpdate.victoriesBlack = it}
        defeatBlack?.let { playerToUpdate.defeatBlack = it}
        victoriesBlackPercent?.let { playerToUpdate.victoriesBlackPercent = it}
        don?.let { playerToUpdate.don = it}
        sheriff?.let { playerToUpdate.sheriff = it}
        wasKilled?.let { playerToUpdate.wasKilled = it}
        games?.let { playerToUpdate.games = it}
        rating?.let { playerToUpdate.rating = it}
        playerToUpdate.updatedAt = OffsetDateTime.now()
    }
    return playerToUpdate
}