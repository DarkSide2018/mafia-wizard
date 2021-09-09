package mappers.game

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.CreateGameRequest
import mafia.wizard.openapi.models.UpdateGameRequest
import models.RequestContext
import models.game.GameContext
import models.game.GameModel
import models.game.GamePlayer
import java.util.*

fun GameContext.setQuery(query: CreateGameRequest) = apply {
    gameModel = query.toModel()
}

fun GameContext.setQuery(query: UpdateGameRequest) = apply {
    gameModel = query.toModel()
}

private fun CreateGameRequest.toModel() = GameModel(
    gameUUID = UUID.randomUUID(),
    gameNumber = this.gameNumber,
    players = this.players.map { GamePlayer(playerUUID = it.playerUuid)}
)

private fun UpdateGameRequest.toModel() = GameModel(
    gameUUID = this.gameUuid?:throw FieldWasNullException("UpdateGameRequest gameUuid"),
    gameNumber = this.gameNumber,
    players = this.players.map { GamePlayer(playerUUID = it.playerUuid)}
)