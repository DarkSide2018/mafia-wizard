package mappers.game

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.CreateGameRequest
import mafia.wizard.openapi.models.UpdateGameRequest
import models.PlayerModel
import models.RequestContext
import models.game.GameContext
import models.game.GameModel

import java.util.*

fun GameContext.setQuery(query: CreateGameRequest) = apply {
    gameModel = query.toModel()
}

fun GameContext.setQuery(query: UpdateGameRequest) = apply {
    gameModel = query.toModel()
}
fun GameContext.addPlayerToGame(player:PlayerModel,gameModel: GameModel) = apply {
    gameModel.addPlayer(player)
    this.gameModel = gameModel
}

private fun CreateGameRequest.toModel() = GameModel(
    gameUUID = UUID.randomUUID(),
    gameNumber = this.gameNumber,
    players = this.players?.map { PlayerModel(playerUUID = it.playerUuid?:throw FieldWasNullException("playerUuid"))}?.toMutableList()?: mutableListOf()
)

private fun UpdateGameRequest.toModel() = GameModel(
    gameUUID = this.gameUuid?:throw FieldWasNullException("UpdateGameRequest gameUuid"),
    gameNumber = this.gameNumber,
    players = this.players?.map { PlayerModel(playerUUID = it.playerUuid?:throw FieldWasNullException("playerUuid"))}?.toMutableList()?: mutableListOf()
)