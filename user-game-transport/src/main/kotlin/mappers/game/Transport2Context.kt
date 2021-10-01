package mappers.game

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.CreateGameRequest
import mafia.wizard.openapi.models.UpdateGameRequest
import mafia.wizard.openapi.models.UpdatePlayerInGameRequest
import models.PlayerModel
import models.game.GameContext
import models.game.GameModel

import java.util.*

fun GameContext.setQuery(gameUUID: UUID, query: CreateGameRequest) = apply {
    gameModel = query.toModel(gameUUID)
}

fun GameContext.setQuery(query: UpdateGameRequest) = apply {
    gameModel = query.toModel()
}

fun GameContext.addPlayerToGame(player: PlayerModel, gameModel: GameModel) = apply {
    gameModel.addPlayer(player)
    this.gameModel = gameModel
}

fun GameContext.updatePlayerInGame(player: PlayerModel, gameModel: GameModel) = apply {


    val updatedPlayers = gameModel.players.map {
        if (it.playerUUID == player.playerUUID) {
            it.additionalPoints = player.additionalPoints
            it.foulAmount = player.foulAmount
            it.nickName = player.nickName
            it.points = player.points
            it.penalties = player.penalties
            it.bestMove = player.bestMove
            it.victoriesRed = player.victoriesRed
            it.defeatRed = player.defeatRed
            it.victoriesBlack = player.victoriesBlack
            it.defeatBlack = player.defeatBlack
            it.don = player.don
            it.sheriff = player.sheriff
            it.wasKilled = player.wasKilled
        }
        return@map it
    }.toMutableSet()
    gameModel.players = updatedPlayers
    this.gameModel = gameModel
}

private fun CreateGameRequest.toModel(gameUUID: UUID) = GameModel(
    gameUUID = gameUUID,
    name = this.name,
    gameNumber = this.gameNumber,
    players = this.players?.map { PlayerModel(playerUUID = it.playerUuid ?: throw FieldWasNullException("playerUuid")) }
        ?.toMutableSet() ?: mutableSetOf<PlayerModel>()
)

private fun UpdateGameRequest.toModel() = GameModel(
    gameUUID = this.gameUuid ?: throw FieldWasNullException("UpdateGameRequest gameUuid"),
    name = this.name,
    gameNumber = this.gameNumber,
    players = this.players?.map { PlayerModel(playerUUID = it.playerUuid ?: throw FieldWasNullException("playerUuid")) }
        ?.toMutableSet() ?: mutableSetOf()
)

fun UpdatePlayerInGameRequest.toModel() = PlayerModel(
    playerUUID = this.playerUuid ?: throw FieldWasNullException("player uuid"),
    foulAmount = this.foulAmount,
    nickName = this.nickName,
    points = this.points,
    additionalPoints = this.additionalPoints,
    penalties = this.penalties,
    bestMove = this.bestMove,
    victoriesRed = this.victoriesRed,
    defeatRed = this.defeatRed,
    victoriesBlack = this.victoriesBlack,
    defeatBlack = this.defeatBlack,
    don = this.don,
    sheriff = this.sheriff,
    wasKilled = this.wasKilled,
)