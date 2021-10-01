package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.entities.DRAFT_STATUS
import mafia.wizard.entities.Game
import mafia.wizard.entities.User
import mafia.wizard.mappers.game.DataLayer2GameContext
import mafia.wizard.mappers.game.GameContext2DataLayer
import mafia.wizard.mappers.player.toModel
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.GameRepository
import mafia.wizard.repository.PlayerRepo
import mappers.game.*
import models.game.GameContext
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
    private val playerRepo: PlayerRepo,
    private val gameRepository: GameRepository,
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

    fun addPlayer(request: AddPlayerRequest): BaseResponse {
        val game = gameRepository.getById(request.gameUuid ?: throw FieldWasNullException("gameUuid"))
        val player = playerRepo.getById(request.playerUuid ?: throw FieldWasNullException("playerUuid"))
        val gameContext = GameContext()
            .addPlayerToGame(
                player.toModel(),
                dataLayer2GameContext.gameToGameModel(game)
            )
        val gameEntity = gameContext2DataLayer.updateGameEntity(gameContext, game)
        gameRepository.save(gameEntity)
        player.status = BUSY_STATUS
        playerRepo.save(player)
        return gameContext.toCommandResponse()
    }

    fun updatePlayerInGame(request: UpdatePlayerInGameRequest): BaseResponse {
        val game = gameRepository.getById(request.gameUuid ?: throw FieldWasNullException("gameUuid"))
        val gameContext = GameContext()
            .updatePlayerInGame(
                request.toModel(),
                dataLayer2GameContext.gameToGameModel(game))
        val gameEntity = gameContext2DataLayer.updateGameEntity(gameContext, game)
        gameRepository.save(gameEntity)
        return gameContext.toCommandResponse()
    }

    fun createOrGetDraft(game: CreateGameRequest): BaseResponse {
        getDraftGame(game).takeIf { it.isNotEmpty() }?.get(0)?.let {
            val gameContext =
                GameContext().setQuery(it.gameUUID ?: throw FieldWasNullException("createOrGetDraft gameUuid"), game)
            return gameContext.toCommandResponse()
        } ?: return createGame(game)
    }

    fun getDraftGame(gameRequest: CreateGameRequest): MutableList<Game?> {
        val createdBy = (SecurityContextHolder.getContext().authentication.principal as User).userName
            ?: throw FieldWasNullException("userName")
        return gameRepository.getDraftGame(PageRequest.of(0, 1),
            createdBy,
            DRAFT_STATUS
        ).content
    }

    fun createGame(game: CreateGameRequest): BaseResponse {
        val gameContext = GameContext().setQuery(UUID.randomUUID(), game)
        val gameEntity = gameContext2DataLayer.toGameEntity(gameContext)
        gameEntity.createdAt = OffsetDateTime.now()
        gameRepository.save(gameEntity)
        return gameContext.toCommandResponse()
    }

    fun updateGame(game: UpdateGameRequest): BaseResponse {
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