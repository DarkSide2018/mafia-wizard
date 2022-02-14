package mappers.game

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.*
import models.PlayerModel
import models.game.Election
import models.game.GameContext
import models.game.Night
import java.util.*


fun GameContext.toReadGameResponse(): ReadGameResponse {
    return ReadGameResponse(
        requestUUID = this.requestContext.requestUUID,
        errors = this.requestContext.errors.takeIf { it.isNotEmpty() },
        gameNumber = gameModel?.gameNumber,
        gameUuid = gameModel?.gameUUID ?: throw FieldWasNullException("wrong gameUuid"),
        victory = gameModel?.victory,
        name = gameModel?.name,
        playerToCardNumber = gameModel?.playerToCardNumber?.map {
            return@map PlayerToCardNumberDTO(
                playerUuid = it.playerUuid,
                playerNickName = it.playerNickName,
                slot = it.cardNumber,
                role = it.gameRole,
                note = it.note,
                addPoints = it.addPoints
            )
        } ?: listOf(),
        elections = gameModel?.elections?.map { it.toElectionDto(this.gameModel?.gameUUID) } ?: listOf(),
        players = gameModel?.players?.map { it.toGamePlayerInfo() } ?: listOf(),
        nights = gameModel?.nights?.map { it.toNightInfo() } ?: listOf(),
        result = if (this.requestContext.errors.isEmpty()) ReadGameResponse.Result.SUCCESS else ReadGameResponse.Result.ERROR
    )
}

fun GameContext.toReadAllGamesResponse(): ReadAllGamesResponse {
    return ReadAllGamesResponse(
        requestUUID = this.requestContext.requestUUID,
        pageNumber = this.page,
        games = this.gameModelList?.map {
            return@map GameMetaInfo(
                name = it.name,
                gameNumber = it.gameNumber,
                gameUuid = it.gameUUID,
                players = it.players?.map { gamePlayer -> gamePlayer.toGamePlayerInfo() }
            )
        },
        errors = this.requestContext.errors.takeIf { it.isNotEmpty() },
        result = if (this.requestContext.errors.isEmpty()) ReadAllGamesResponse.Result.SUCCESS else ReadAllGamesResponse.Result.ERROR
    )
}

fun GameContext.toCommandResponse(): BaseResponse {
    val errors = this.requestContext.errors
    return BaseResponse(
        entityUuid = this.gameModel?.gameUUID ?: throw FieldWasNullException("gameUUID"),
        requestUUID = this.requestContext.requestUUID,
        errors = errors.takeIf { it.isNotEmpty() },
        result = if (errors.isEmpty()) BaseResponse.Result.SUCCESS else BaseResponse.Result.ERROR
    )
}


fun PlayerModel.toGamePlayerInfo(): UpdatePlayerInGameRequest {
    return UpdatePlayerInGameRequest(
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

fun Night.toNightInfo(): NightInfo {
    return NightInfo(
        nightNumber = this.nightNumber,
        killedPlayer = this.killedPlayer,
        sheriffChecked = this.sheriffChecked,
        donChecked = this.donChecked,
        playerLeftGame = this.playerLeftGame?.map {LeftGame(it.index,it.playerSlot)  }
    )
}

fun Election.toElectionDto(gameUUID: UUID?): ElectionDTO {
    return ElectionDTO(
        electionId = this.electionId,
        sortOrder = this.sortOrder,
        gameUuid = gameUUID,
        dropdowns = this.drops?.map {
            ElectionDropDown(
                it.slot,
                it.playerUuid,
                it.playerName,
                it.numberOfVotes
            )
        }
    )
}