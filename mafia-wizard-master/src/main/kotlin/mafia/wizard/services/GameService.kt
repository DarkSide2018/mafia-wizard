package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.mappers.game.DataLayer2GameContext
import mafia.wizard.mappers.game.GameContext2DataLayer
import mafia.wizard.mappers.player.toModel
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.GameRepository
import mafia.wizard.repository.PlayerRepo
import mappers.game.*
import models.game.GameContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameService(
    private val dataLayer2GameContext: DataLayer2GameContext,
    private val gameContext2DataLayer: GameContext2DataLayer,
    private val playerRepo: PlayerRepo,
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

    fun addPlayer(request: AddPlayerRequest): CommandResponse {
        val game = gameRepository.getById(request.gameUuid?:throw FieldWasNullException("gameUuid"))
        val player = playerRepo.getById(request.playerUuid?:throw FieldWasNullException("playerUuid"))
        val gameContext = GameContext()
            .addPlayerToGame(
                player.toModel(),
                dataLayer2GameContext.gameToGameModel(game)
            )
        val gameEntity = gameContext2DataLayer.updateGameEntity(gameContext,game)
        gameRepository.save(gameEntity)
        return gameContext.toCommandResponse()
    }

    fun createGame(game: CreateGameRequest): CommandResponse {
        val gameContext = GameContext().setQuery(game)
        val gameEntity = gameContext2DataLayer.toGameEntity(gameContext)
        gameRepository.save(gameEntity)
        return gameContext.toCommandResponse()
    }

    fun updateGame(game: UpdateGameRequest): CommandResponse {
        val gameContext = GameContext().setQuery(game)
        val gameForUpdate = gameRepository
            .findById(game.gameUuid ?: throw FieldWasNullException("updateGame gameUUID"))
            .orElseThrow()
        val updatedGame = gameContext2DataLayer.updateGameEntity(gameContext, gameForUpdate)
        gameRepository.save(updatedGame)
        return gameContext.toCommandResponse()
    }

    fun deleteGame(uuid: UUID) {
        gameRepository.deleteById(uuid)
    }
}