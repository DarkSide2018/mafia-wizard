package mafia.wizard.mappers.game

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mafia.wizard.entities.Game
import models.game.GameContext
import models.game.GameModel
import models.game.GamePlayer
import org.springframework.stereotype.Service

@Service
class DataLayer2GameContext(
    private val objectMapper: ObjectMapper
) {
    fun setGameIntoContext(readGameContext: GameContext, game: Game): GameContext {
        readGameContext.gameModel = gameToGameModel(game)
        return readGameContext
    }
    fun setGamesIntoContext(readGameContext: GameContext, games: List<Game>): GameContext {
        readGameContext.gameModelList = games.map { gameToGameModel(it) }
        return readGameContext
    }
        fun gameToGameModel(game: Game): GameModel {
            val model = GameModel(
                gameUUID = game.gameUUID,
                gameNumber = game.gameNumber,
            )
            with(model){
                game.players?.let { players = objectMapper.readValue(it, object : TypeReference<List<GamePlayer>>(){}) }
            }
            return model
        }
    }
