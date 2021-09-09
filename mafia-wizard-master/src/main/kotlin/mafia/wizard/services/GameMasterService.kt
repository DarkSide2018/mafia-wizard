package mafia.wizard.services

import mafia.wizard.openapi.models.CreateGameMasterRequest
import mafia.wizard.openapi.models.UpdateGameRequest
import mafia.wizard.repository.GameMasterRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GameMasterService(
    private val gameMasterRepository: GameMasterRepository
) {
    fun getByUuid(uuid: UUID){
        gameMasterRepository.findById(uuid)
    }
    fun getAll(){
        gameMasterRepository.findAll()
    }

    fun createGameMaster(gameMaster: CreateGameMasterRequest) {
       // ResponseEntity.ok(gameMasterRepository.save(gameMaster))
    }

    fun updateGameMaster(game: UpdateGameRequest) {

    }

    fun deleteGameMaster(uuid: UUID){

    }
}