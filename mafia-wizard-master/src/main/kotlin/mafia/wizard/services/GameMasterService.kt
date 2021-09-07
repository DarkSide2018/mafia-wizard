package mafia.wizard.services

import mafia.wizard.repository.GameMasterRepository
import org.springframework.stereotype.Service

@Service
class GameMasterService(
    private val gameMasterRepository: GameMasterRepository
) {

}