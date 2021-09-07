package mafia.wizard.mappers.game

import com.fasterxml.jackson.databind.ObjectMapper
import mafia.wizard.entities.Game
import models.game.CreateGameContext
import models.game.UpdateGameContext
import org.springframework.stereotype.Component
import java.util.*

@Component
class Context2DataLayer(
    private val objectMapper: ObjectMapper
) {
    fun toGameEntity(gameContext: CreateGameContext): Game {
        return Game(
            gameUUID = UUID.randomUUID(),
            gameNumber = gameContext.gameNumber ?: throw Exception("game number was null"),
            players = objectMapper.writeValueAsString(gameContext.players?:throw Exception ("CreateGameContext null players"))
        )
    }

    fun updateGameEntity(updateGameContext: UpdateGameContext, gameForUpdate: Game): Game {
        val players = updateGameContext.players
        val gameNumber = updateGameContext.gameNumber
        players?.let {gameForUpdate.players = objectMapper.writeValueAsString(it)}
        gameNumber?.let { gameForUpdate.gameNumber = it }
        return gameForUpdate
    }
}
