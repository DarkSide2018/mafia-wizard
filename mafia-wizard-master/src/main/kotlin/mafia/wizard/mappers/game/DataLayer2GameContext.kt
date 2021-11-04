package mafia.wizard.mappers.game

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import exceptions.FieldWasNullException
import mafia.wizard.entities.Game
import models.PlayerModel
import models.game.GameContext
import models.game.GameModel
import models.game.Night
import models.game.PlayerToCardNumber

import org.springframework.stereotype.Service

@Service
class DataLayer2GameContext(
    private val objectMapper: ObjectMapper,
) {
    fun setGameIntoContext(readGameContext: GameContext, game: Game): GameContext {
        readGameContext.gameModel = gameToGameModel(game)
        return readGameContext
    }

    fun finishingGame(context: GameContext, game: Game): GameContext {
        context.gameModel?.victory = game.victory
        context.gameModel?.playerToCardNumber = gameToGameModel(game).playerToCardNumber
        return context
    }

    fun setGamesIntoContext(readGameContext: GameContext, games: List<Game>): GameContext {
        readGameContext.gameModelList = games.map { gameToGameModel(it) }
        return readGameContext
    }

    fun gameToGameModel(game: Game): GameModel {
        val model = GameModel(
            gameUUID = game.gameUUID ?: throw FieldWasNullException("gameToGameModel gameUUID"),
            name = game.name,
            gameNumber = game.gameNumber,
            victory = game.victory
        )
        with(model) {
            game.players?.let {
                players =
                    objectMapper.readValue(
                        it,
                        object : TypeReference<MutableSet<PlayerModel>>() {}) as MutableSet<PlayerModel>
            }
            game.nights?.let {
                nights = objectMapper.readValue(
                    it,
                    object : TypeReference<MutableSet<Night>>() {}) as MutableSet<Night>
            }
            game.playerToCardNumber?.let {
                playerToCardNumber = objectMapper.readValue(
                    it,
                    object : TypeReference<MutableSet<PlayerToCardNumber>>() {}) as MutableSet<PlayerToCardNumber>
            }
        }
        return model
    }
}
