package mappers

import mafia.wizard.openapi.models.CreatePlayerResponse
import mafia.wizard.openapi.models.DeletePlayerResponse
import mafia.wizard.openapi.models.ReadPlayerResponse
import mafia.wizard.openapi.models.UpdatePlayerResponse
import models.CreatePlayerContext
import models.DeletePlayerContext
import models.ReadPlayerContext
import models.UpdatePlayerContext

fun ReadPlayerContext.toReadPlayerResponse(): ReadPlayerResponse {
    val playerModelNotNull = this.playerModel ?: throw Exception("ReadPlayerResponse emtpy player model")
   return ReadPlayerResponse(
        requestUUID = this.requestUUID,
        errors = errors.takeIf { it.isNotEmpty() },
        playerUuid = this.playerUUID,
        ratingId = playerModelNotNull.ratingId,
        foulAmount = playerModelNotNull.foulAmount,
        nickName = playerModelNotNull.nickName,
        points =playerModelNotNull.points,
        additionalPoints = playerModelNotNull.additionalPoints,
        penalties= playerModelNotNull.penalties,
        bestMove = playerModelNotNull.bestMove,
        victories = playerModelNotNull.victories,
        victoriesPercent = playerModelNotNull.victoriesPercent,
        victoriesRed = playerModelNotNull.victoriesRed,
        victoriesRedPercent = playerModelNotNull.victoriesRedPercent,
        defeatRed = playerModelNotNull.defeatRed,
        victoriesBlack =playerModelNotNull.victoriesBlack,
        defeatBlack =playerModelNotNull.defeatBlack,
        victoriesBlackPercent = playerModelNotNull.victoriesBlackPercent,
        don = playerModelNotNull.don,
        sheriff = playerModelNotNull.sheriff,
        wasKilled =playerModelNotNull.wasKilled,
        games = playerModelNotNull.games,
        rating = playerModelNotNull.rating,
        result = if (this.errors.isEmpty()) ReadPlayerResponse.Result.SUCCESS else ReadPlayerResponse.Result.ERROR
    )
}
fun CreatePlayerContext.toCreatePlayerResponse()=CreatePlayerResponse(
    requestUUID = this.requestUUID,
    errors = errors.takeIf { it.isNotEmpty() },
    result = if (this.errors.isEmpty()) CreatePlayerResponse.Result.SUCCESS else CreatePlayerResponse.Result.ERROR
)
fun UpdatePlayerContext.toUpdatePlayerResponse()= UpdatePlayerResponse(
    requestUUID = this.requestUUID,
    errors = errors.takeIf { it.isNotEmpty() },
    result = if (this.errors.isEmpty()) UpdatePlayerResponse.Result.SUCCESS else UpdatePlayerResponse.Result.ERROR
)
fun DeletePlayerContext.toDeletePlayerResponse()= DeletePlayerResponse(
    requestUUID = this.requestUUID,
    errors = errors.takeIf { it.isNotEmpty() },
    result = if (this.errors.isEmpty()) DeletePlayerResponse.Result.SUCCESS else DeletePlayerResponse.Result.ERROR
)