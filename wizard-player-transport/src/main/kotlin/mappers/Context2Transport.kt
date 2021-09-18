package mappers

import mafia.wizard.openapi.models.CommandResponse
import mafia.wizard.openapi.models.PlayerMetaInfo
import mafia.wizard.openapi.models.ReadAllPlayersResponse
import mafia.wizard.openapi.models.ReadPlayerResponse
import models.PlayerContext

fun PlayerContext.toReadPlayerResponse(): ReadPlayerResponse {
    val playerModelNotNull = this.playerModel ?: throw Exception("ReadPlayerResponse emtpy player model")
    return ReadPlayerResponse(
        requestUUID = this.requestUUID,
        errors = errors.takeIf { it.isNotEmpty() },
        playerUuid = playerModelNotNull.playerUUID,
        ratingId = playerModelNotNull.ratingId,
        foulAmount = playerModelNotNull.foulAmount,
        nickName = playerModelNotNull.nickName,
        points = playerModelNotNull.points,
        additionalPoints = playerModelNotNull.additionalPoints,
        penalties = playerModelNotNull.penalties,
        bestMove = playerModelNotNull.bestMove,
        victories = playerModelNotNull.victories,
        victoriesPercent = playerModelNotNull.victoriesPercent,
        victoriesRed = playerModelNotNull.victoriesRed,
        victoriesRedPercent = playerModelNotNull.victoriesRedPercent,
        defeatRed = playerModelNotNull.defeatRed,
        victoriesBlack = playerModelNotNull.victoriesBlack,
        defeatBlack = playerModelNotNull.defeatBlack,
        victoriesBlackPercent = playerModelNotNull.victoriesBlackPercent,
        don = playerModelNotNull.don,
        sheriff = playerModelNotNull.sheriff,
        wasKilled = playerModelNotNull.wasKilled,
        games = playerModelNotNull.games,
        rating = playerModelNotNull.rating,
        result = if (this.errors.isEmpty()) ReadPlayerResponse.Result.SUCCESS else ReadPlayerResponse.Result.ERROR
    )
}

fun PlayerContext.toReadAllPlayersResponse(): ReadAllPlayersResponse {
    return ReadAllPlayersResponse(
        requestUUID = this.requestUUID,
        players = this.playerModelList?.map {
            PlayerMetaInfo(
                playerUuid = it.playerUUID,
                ratingId = it.ratingId,
                foulAmount = it.foulAmount,
                nickName = it.nickName,
                points = it.points,
                additionalPoints = it.additionalPoints,
                penalties = it.penalties,
                bestMove = it.bestMove,
                victories = it.victories,
                victoriesPercent = it.victoriesPercent,
                victoriesRed = it.victoriesRed,
                victoriesRedPercent = it.victoriesRedPercent,
                defeatRed = it.defeatRed,
                victoriesBlack = it.victoriesBlack,
                defeatBlack = it.defeatBlack,
                victoriesBlackPercent = it.victoriesBlackPercent,
                don = it.don,
                sheriff = it.sheriff,
                wasKilled = it.wasKilled,
                games = it.games,
                rating = it.rating,
            )
        },
        errors = this.errors.takeIf { it.isNotEmpty() },
        result = if (this.errors.isEmpty()) ReadAllPlayersResponse.Result.SUCCESS else ReadAllPlayersResponse.Result.ERROR
    )
}

fun PlayerContext.toCommandResponse() = CommandResponse(
    requestUUID = this.requestUUID,
    errors = errors.takeIf { it.isNotEmpty() },
    result = if (this.errors.isEmpty()) CommandResponse.Result.SUCCESS else CommandResponse.Result.ERROR
)