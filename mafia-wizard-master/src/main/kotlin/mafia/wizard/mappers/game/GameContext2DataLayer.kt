package mafia.wizard.mappers.game

import com.fasterxml.jackson.databind.ObjectMapper
import exceptions.FieldWasNullException
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
        val gameModel = updateGameContext.gameModel?:throw FieldWasNullException("gameModel")
        gameModel.players.let { gameForUpdate.players = objectMapper.writeValueAsString(it) }
        gameModel.gameNumber?.let { gameForUpdate.gameNumber = it }
        gameModel.status?.let { gameForUpdate.status = it }
        return gameForUpdate
    }
}
