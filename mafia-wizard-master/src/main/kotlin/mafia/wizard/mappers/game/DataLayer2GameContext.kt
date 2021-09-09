package mafia.wizard.mappers.game

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mafia.wizard.entities.Game
import models.game.GameContext
import models.game.GamePlayer
import org.springframework.stereotype.Service

@Service
class DataLayer2GameContext(
    private val objectMapper: ObjectMapper
) {
    fun setGameIntoContext(readGameContext: GameContext, game: Game): GameContext {
        readGameContext.gameModel?.gameNumber = game.gameNumber
        readGameContext.gameModel?.players = objectMapper.readValue(
            game.players, object : TypeReference<List<GamePlayer>>(){})
        return readGameContext
    }


    }
