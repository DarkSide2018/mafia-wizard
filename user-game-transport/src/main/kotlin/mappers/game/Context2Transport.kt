package mappers.game

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.*
import models.game.GameContext
import models.game.GamePlayer


fun GameContext.toReadGameResponse(): ReadGameResponse {
    return ReadGameResponse(
        requestUUID = this.requestContext.requestUUID,
        errors = this.requestContext.errors.takeIf { it.isNotEmpty() },
        gameNumber = gameModel?.gameNumber,
        gameUuid = gameModel?.gameUUID,
        players = gameModel?.players?.map { it.toGamePlayerInfo() } ?: listOf(),
        result = if (this.requestContext.errors.isEmpty()) ReadGameResponse.Result.SUCCESS else ReadGameResponse.Result.ERROR
    )
}

fun GameContext.toReadAllGamesResponse(): ReadAllGamesResponse {
    return ReadAllGamesResponse(
        requestUUID = this.requestContext.requestUUID,
        games = this.gameModelList?.map {
            return@map GameMetaInfo(
                gameNumber = it.gameNumber,
                gameUuid = it.gameUUID,
                players = it.players?.map { gamePlayer -> gamePlayer.toGamePlayerInfo() } ?: listOf()
            )
        },
        errors = this.requestContext.errors.takeIf { it.isNotEmpty() },
        result = if (this.requestContext.errors.isEmpty()) ReadAllGamesResponse.Result.SUCCESS else ReadAllGamesResponse.Result.ERROR
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