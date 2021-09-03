package mafia.wizard.controllers

import mafia.wizard.openapi.models.*
import mafia.wizard.services.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.websocket.server.PathParam

@RestController
@CrossOrigin
@RequestMapping("/player")
class PlayerController(

) {
    @Autowired
    lateinit var playerService: PlayerService
    @GetMapping
    fun getByNickname(@PathParam("nickname") nickName: String):ResponseEntity<ReadPlayerResponse>{

        return ResponseEntity.ok().build()
    }
    @PostMapping
    fun createPlayer(@RequestBody createPlayerRequest: CreatePlayerRequest):ResponseEntity<CreatePlayerResponse>{
        
        return ResponseEntity.ok().build()
    }
    @PutMapping
    fun updatePlayer(@RequestBody updatePlayerRequest: UpdatePlayerRequest):ResponseEntity<UpdatePlayerResponse>{

        return ResponseEntity.ok().build()
    }
    @DeleteMapping
    fun deletePlayer(@PathParam("uuid") uuid: UUID):ResponseEntity<DeletePlayerResponse>{

        return ResponseEntity.ok().build()
    }
}