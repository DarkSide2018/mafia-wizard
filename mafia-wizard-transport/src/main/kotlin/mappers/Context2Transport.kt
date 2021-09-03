package mappers

import mafia.wizard.openapi.models.CreatePlayerResponse
import mafia.wizard.openapi.models.DeletePlayerResponse
import mafia.wizard.openapi.models.ReadPlayerResponse
import mafia.wizard.openapi.models.UpdatePlayerResponse
import models.CreatePlayerContext
import models.DeletePlayerContext
import models.ReadPlayerContext
import models.UpdatePlayerContext

fun ReadPlayerContext.toReadPlayerResponse() = ReadPlayerResponse(
    requestUUID = this.requestUUID,
    errors = errors.takeIf { it.isNotEmpty() },
    playerUuid = this.playerUUID,
    ratingId = this.playerModel.ratingId,
    foulAmount = this.playerModel.foulAmount,
    nickName = this.playerModel.nickName,
    points = this.playerModel.points,
    additionalPoints = this.playerModel.additionalPoints,
    penalties= this.playerModel.penalties,
    bestMove = this.playerModel.bestMove,
    victories = this.playerModel.victories,
    victoriesPercent = this.playerModel.victoriesPercent,
    victoriesRed = this.playerModel.victoriesRed,
    victoriesRedPercent = this.playerModel.victoriesRedPercent,
    defeatRed = this.playerModel.defeatRed,
    victoriesBlack = this.playerModel.victoriesBlack,
    defeatBlack = this.playerModel.defeatBlack,
    victoriesBlackPercent = this.playerModel.victoriesBlackPercent,
    don = this.playerModel.don,
    sheriff = this.playerModel.sheriff,
    wasKilled = this.playerModel.wasKilled,
    games = this.playerModel.games,
    rating = this.playerModel.rating,
    result = if (this.errors.isEmpty()) ReadPlayerResponse.Result.SUCCESS else ReadPlayerResponse.Result.ERROR
)
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