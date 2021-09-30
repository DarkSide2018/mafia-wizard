package mappers.game

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.CreateGameRequest
import mafia.wizard.openapi.models.UpdateGameRequest
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
    gameModel.addPlayer(player)
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