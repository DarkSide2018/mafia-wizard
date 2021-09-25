package mafia.wizard.mappers.game

import com.fasterxml.jackson.databind.ObjectMapper
import mafia.wizard.entities.Game
import mafia.wizard.entities.User
import models.game.GameContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameContext2DataLayer(
    private val objectMapper: ObjectMapper
) {
    fun toGameEntity(gameContext: GameContext): Game {
        return Game(
            gameUUID = gameContext.gameModel?.gameUUID,
            createdBy = (SecurityContextHolder.getContext().authentication.principal as User).userName,
            name = gameContext.gameModel?.name,
            gameNumber = gameContext.gameModel?.gameNumber,
            players = gameContext.gameModel?.players?.let { objectMapper.writeValueAsString(it) }
        )

    }

    fun updateGameEntity(updateGameContext: GameContext, gameForUpdate: Game): Game {
        val players = updateGameContext.gameModel?.players
        val gameNumber = updateGameContext.gameModel?.gameNumber
        players?.let { gameForUpdate.players = objectMapper.writeValueAsString(it) }
        gameNumber?.let { gameForUpdate.gameNumber = it }
        return gameForUpdate
    }
}
