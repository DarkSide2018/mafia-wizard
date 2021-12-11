package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.common.getActorName
import mafia.wizard.config.NotFoundException
import mafia.wizard.entities.User
import mafia.wizard.mappers.game.DataLayer2GameContext
import mafia.wizard.mappers.game.GameContext2DataLayer
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.GameRepository
import mappers.game.*
import models.game.GameContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

const val BUSY_STATUS = "BUSY"


@Service
class GameService(
    private val dataLayer2GameContext: DataLayer2GameContext,
    private val gameContext2DataLayer: GameContext2DataLayer,
    private val playerCalculator: PlayerCalculator,
    private val gameRepository: GameRepository,
) {
    var logger: Logger = LoggerFactory.getLogger(javaClass)
    fun getByUuid(uuid: UUID): ReadGameResponse {
        val game = gameRepository.findById(uuid)
            .orElseThrow { return@orElseThrow NotFoundException("no such element with uuid : $uuid") }
        return dataLayer2GameContext
            .setGameIntoContext(GameContext(), game)
            .toReadGameResponse()
    }

    fun getAll(): ReadAllGamesResponse {
        val games = gameRepository.findAllByCreatedBy(getActorName())
        return dataLayer2GameContext
            .setGamesIntoContext(GameContext(), games)
            .toReadAllGamesResponse()
    }
    fun getGameByStatus(status: String): ReadGameResponse {
        val createdBy = (SecurityContextHolder.getContext().authentication.principal as User).userName
            ?: throw FieldWasNullException("userName")
        val gamePage = gameRepository.getDraftGame(PageRequest.of(0, 1), createdBy, status).takeIf { !it.isEmpty }
            ?: throw NotFoundException("game")
        val game = gamePage.content[0] ?: throw NotFoundException("game in array")
        return dataLayer2GameContext
            .setGameIntoContext(GameContext(), game)
            .toReadGameResponse()
    }

    fun finishElection(request: FinishElectionRequest):BaseResponse{
        val context = request.toGameContext()
        val gameForUpdate = gameRepository
            .findById(context.gameModel?.gameUUID ?: throw FieldWasNullException("updateGame gameUUID"))
            .orElseThrow()
        val updatedGame = gameContext2DataLayer.updateGameEntity(context, gameForUpdate)
        gameRepository.save(updatedGame)
        return context.toCommandResponse()
    }

    fun createGame(game: CreateGameRequest): BaseResponse {
        val gameContext = GameContext().setQuery(UUID.randomUUID(), game)
        val gameEntity = gameContext2DataLayer.toGameEntity(gameContext)
        gameEntity.createdAt = OffsetDateTime.now()
        gameRepository.saveAndFlush(gameEntity)
        logger.debug("game created")
        return gameContext.toCommandResponse()
    }

    fun updateGame(game: UpdateGameRequest): BaseResponse {
        val gameContext = GameContext().setQuery(game)
        val gameForUpdate = gameRepository
            .findById(game.gameUuid ?: throw FieldWasNullException("updateGame gameUUID"))
            .orElseThrow()
        if(gameForUpdate.status != "FINISHED"){
            val updatedGame = gameContext2DataLayer.updateGameEntity(gameContext, gameForUpdate)
            gameRepository.save(updatedGame)
        }
        return gameContext.toCommandResponse()
    }

    fun finishGame(game: UpdateGameRequest): BaseResponse {
        val gameContext = GameContext().finishGame(game)
        val gameForUpdate = gameRepository
            .findById(game.gameUuid ?: throw FieldWasNullException("updateGame gameUUID"))
            .orElseThrow()
        dataLayer2GameContext.finishingGame(gameContext,gameForUpdate)
        playerCalculator.playerBusyToFreeStatus(gameContext)
        val updatedGame = gameContext2DataLayer.updateGameEntity(gameContext, gameForUpdate)
        gameRepository.save(updatedGame)
        return gameContext.toCommandResponse()
    }

    fun deleteGame(uuid: UUID) {
        gameRepository.deleteById(uuid)
    }
}