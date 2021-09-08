package mappers.game

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.CommandResponse
import mafia.wizard.openapi.models.GamePlayerInfo
import mafia.wizard.openapi.models.ReadGameResponse
import models.game.GameContext
import models.game.GamePlayer


fun GameContext.toReadGameResponse(): ReadGameResponse {
    val playerModelNotNull = this.gameModel
    return ReadGameResponse(
        requestUUID = this.requestContext.requestUUID,
        errors = this.requestContext.errors.takeIf { it.isNotEmpty() },
        gameNumber = gameModel.gameNumber,
        gameUuid = gameModel.gameUUID,
        players = gameModel.players?.map { it.toGamePlayerInfo() }
            ?: throw FieldWasNullException("players toReadGameResponse"),
        result = if (this.requestContext.errors.isEmpty()) ReadGameResponse.Result.SUCCESS else ReadGameResponse.Result.ERROR
    )
}

fun GameContext.toCreateGameResponse(): CommandResponse {
    val errors = this.requestContext.errors
    return CommandResponse(
        requestUUID = this.requestContext.requestUUID,
        errors = errors.takeIf { it.isNotEmpty() },
        result = if (errors.isEmpty()) CommandResponse.Result.SUCCESS else CommandResponse.Result.ERROR
    )
}

fun GameContext.toUpdateGameResponse(): CommandResponse {
    val errors = this.requestContext.errors
    return CommandResponse(
        requestUUID = this.requestContext.requestUUID,
        errors = errors.takeIf { it.isNotEmpty() },
        result = if (errors.isEmpty()) CommandResponse.Result.SUCCESS else CommandResponse.Result.ERROR
    )
}

fun GamePlayer.toGamePlayerInfo(): GamePlayerInfo {
    return GamePlayerInfo(
        playerUuid = this.playerUUID,
        nickName = this.nickName,
        foulAmount = this.foulAmount,
        points = this.points,
        additionalPoints = this.additionalPoints,
        penalties = this.penalties,
        bestMove = this.bestMove,
        victoriesRed = this.victoriesRed,
        victoriesBlack = this.victoriesBlack,
        defeatBlack = this.defeatBlack,
        defeatRed = this.defeatRed,
        don = this.don,
        sheriff = this.sheriff,
        wasKilled = this.wasKilled
    )
}