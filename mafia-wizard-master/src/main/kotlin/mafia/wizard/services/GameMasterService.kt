package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.mappers.gameMaster.createGameMasterEntity
import mafia.wizard.mappers.gameMaster.setGameMaster
import mafia.wizard.mappers.gameMaster.setGameMasterList
import mafia.wizard.mappers.gameMaster.updateGameMasterEntity
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.GameMasterRepository
import mappers.gameMaster.*
import models.gameMaster.GameMasterContext
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameMasterService(
    private val gameMasterRepository: GameMasterRepository
) {
    fun getByUuid(uuid: UUID): ReadGameMasterResponse {
        return GameMasterContext()
            .setGameMaster(gameMasterRepository.findById(uuid).orElseThrow())
            .toReadGameMasterResponse()
    }

    fun getAll(): ReadAllGameMastersResponse {
        return GameMasterContext()
            .setGameMasterList(gameMasterRepository.findAllWithPagination(PageRequest.of(0, 100)))
            .toReadAllGameMastersResponse()
    }

    fun createGameMaster(gameMaster: CreateGameMasterRequest): BaseResponse {
        val gameMasterContext = GameMasterContext()
            .setQuery(gameMaster)
        gameMasterRepository.save(gameMasterContext.createGameMasterEntity())
        return gameMasterContext.toCreateGameMasterResponse()
    }

    fun updateGameMaster(gameMaster: UpdateGameMasterRequest): BaseResponse {
        val gameMasterForUpdate = gameMasterRepository.getById(
            gameMaster.gameMasterUuid ?: throw FieldWasNullException("updateGameMaster gameMasterUuid")
        )
        val gameMasterContext = GameMasterContext()
            .setQuery(gameMaster)
        gameMasterRepository.save(gameMasterContext.updateGameMasterEntity(gameMasterForUpdate))
        return gameMasterContext.toUpdateGameMasterResponse()
    }

    fun deleteGameMaster(uuid: UUID) {
        gameMasterRepository.deleteById(uuid)
    }
}