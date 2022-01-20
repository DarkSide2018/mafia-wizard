package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.common.toJson
import mafia.wizard.config.BadRequestException
import mafia.wizard.config.NotFoundException
import mafia.wizard.mappers.game.DataLayer2GameContext
import mafia.wizard.mappers.game.GameContext2DataLayer
import mafia.wizard.mappers.player.toModel
import mafia.wizard.openapi.models.AddPlayerRequest
import mafia.wizard.openapi.models.BaseResponse
import mafia.wizard.openapi.models.DeletePlayerFromGameRequest
import mafia.wizard.openapi.models.UpdatePlayerInGameRequest
import mafia.wizard.repository.GameRepository
import mafia.wizard.repository.PlayerRepo
import mappers.game.*
import models.game.GameContext
import models.game.GameModel
import org.springframework.stereotype.Service

@Service
class PlayerInGameService(
    private val dataLayer2GameContext: DataLayer2GameContext,
    private val gameContext2DataLayer: GameContext2DataLayer,
    private val playerRepo: PlayerRepo,
    private val gameRepository: GameRepository,
) {

    fun addPlayer(request: AddPlayerRequest): BaseResponse {
        val game = gameRepository.getById(request.gameUuid ?: throw FieldWasNullException("gameUuid"))
        val player = playerRepo.findByNickName(request.nickName ?: throw FieldWasNullException("nickName"))
        player ?: throw NotFoundException("no such player by nickname")
        val gameContext = GameContext(
            gameModel = GameModel(request.gameUuid?:throw BadRequestException("game uuid was null"))
        )
        gameContext.addPlayerToGame(
            player.toModel(),
            request.slot ?: throw FieldWasNullException("slot")
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

    fun deletePlayerFromGame(request: DeletePlayerFromGameRequest): BaseResponse {
        val game = gameRepository.getById(request.gameUuid ?: throw FieldWasNullException("gameUuid"))
        val gameModel = dataLayer2GameContext.gameToGameModel(game)
        val gameContext = GameContext()
            .deletePlayerFromGame(request.playerUuid ?: throw Exception("player Uuid was null"), gameModel)
        game.playerToCardNumber = gameContext.gameModel?.playerToCardNumber.toJson()
        gameRepository.save(game)
        return gameContext.toCommandResponse()
    }
}