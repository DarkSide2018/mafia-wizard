package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.mappers.game.DataLayer2GameContext
import mafia.wizard.mappers.game.GameContext2DataLayer
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.GameRepository
import mappers.game.*
import models.game.GameContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameService(
    private val dataLayer2GameContext: DataLayer2GameContext,
    private val gameContext2DataLayer: GameContext2DataLayer,
    private val gameRepository: GameRepository
) {

    fun getByUuid(uuid: UUID): ReadGameResponse {
        val game = gameRepository.findById(uuid)
            .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $uuid") }
        return dataLayer2GameContext
            .setGameIntoContext(GameContext(), game)
            .toReadGameResponse()
    }
    fun getAll(): ReadAllGamesResponse {
        val games = gameRepository.findAll()
        return dataLayer2GameContext
            .setGamesIntoContext(GameContext(), games)
            .toReadAllGamesResponse()
    }

    fun createGame(game: CreateGameRequest): CommandResponse {
        val gameContext = GameContext().setQuery(game)
        val gameEntity = gameContext2DataLayer.toGameEntity(gameContext)
        gameRepository.save(gameEntity)
        return gameContext.toCreateGameResponse()
    }

    fun updateGame(game: UpdateGameRequest): CommandResponse {
        val gameContext = GameContext().setQuery(game)
        val gameForUpdate = gameRepository
            .findById(game.gameUuid ?: throw FieldWasNullException("updateGame gameUUID"))
            .orElseThrow()
        val updatedGame = gameContext2DataLayer.updateGameEntity(gameContext,gameForUpdate)
        gameRepository.save(updatedGame)
        return gameContext.toUpdateGameResponse()
    }

    fun deleteGame(uuid: UUID) {
        gameRepository.deleteById(uuid)
    }
}