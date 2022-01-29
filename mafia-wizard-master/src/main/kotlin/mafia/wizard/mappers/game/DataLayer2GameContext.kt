package mafia.wizard.mappers.game

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import exceptions.FieldWasNullException
import mafia.wizard.config.DbException
import mafia.wizard.entities.Game
import models.PlayerModel
import models.game.*

import org.springframework.stereotype.Service

const val DEFAULT = "DEFAULT"

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
        context.gameModel?.nights = gameToGameModel(game).nights
        return context
    }

    fun setGamesIntoContext(readGameContext: GameContext, games: List<Game>): GameContext {
        readGameContext.gameModelList = games.map { gameToGameModel(it) }
        return readGameContext
    }

    fun getCsvModel(context: GameContext): CsvModel {
        val gameModel = context.gameModel
        return CsvModel(
            gameNumber = gameModel?.name ?: DEFAULT,
            dateGame = gameModel?.gameTable ?: DEFAULT,
            gameMaster = gameModel?.createdBy?:throw DbException("createdBy was null"),
            gamblers = gameModel?.playerToCardNumber?.map {
                Gambler(
                    it.playerNickName ?: DEFAULT,
                    it.cardNumber ?: 0
                )
            } ?: listOf(),
            roles = gameModel?.playerToCardNumber?.map {
                Role(
                    it.gameRole ?: DEFAULT,
                    it.cardNumber ?: 0
                )
            } ?: listOf(),
            nights = gameModel?.nights?.map {
                mafia.wizard.mappers.game.CsvNight(
                    it.nightNumber ?: 0,
                    it.donChecked ?: 0,
                    it.killedPlayer ?: 0,
                    mafia = it.killedPlayer ?: 0
                )
            } ?: listOf(),
            victory = gameModel?.victory ?: DEFAULT,
            notes = gameModel?.playerToCardNumber?.map {
                Note(it.cardNumber ?: 0,
                    it.note ?: 0
                )
            } ?: listOf()
        )
    }

    fun gameToGameModel(game: Game): GameModel {
        val model = GameModel(
            createdBy = game.createdBy,
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
            game.elections?.let {
                elections = objectMapper.readValue(
                    it,
                    object : TypeReference<MutableSet<Election>>() {}) as MutableSet<Election>

            }
        }
        return model
    }
}
