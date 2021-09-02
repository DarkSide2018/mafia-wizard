package mafia.wizard.controllers

import mafia.wizard.services.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/player")
class PlayerController(

) {
    @Autowired
    lateinit var playerService: PlayerService
    @GetMapping
    fun getByNickname(nickName: String){

    }
    @PostMapping
    fun createPlayer(){

    }
    @PutMapping
    fun updatePlayer(){

    }
    @DeleteMapping
    fun deletePlayer(uuid: UUID){

    }
}