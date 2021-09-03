package mafia.wizard.services

import mafia.wizard.entities.Player
import mafia.wizard.repository.PlayerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlayerService {
    @Autowired
    lateinit var playerRepo: PlayerRepo

    fun getAll(): MutableList<Player> {
        return playerRepo.findAll()
    }
}