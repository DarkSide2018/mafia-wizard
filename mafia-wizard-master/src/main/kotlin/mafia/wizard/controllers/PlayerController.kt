package mafia.wizard.controllers

import mafia.wizard.openapi.models.*
import mafia.wizard.services.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/player")
class PlayerController(

) {
    @Autowired
    private lateinit var playerService: PlayerService

    @GetMapping
    fun getByNickname(@RequestParam("nickname") nickName: String): ResponseEntity<ReadPlayerResponse> {

        return ResponseEntity.ok(playerService.getByNickName(nickName))
    }

    @GetMapping("/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadPlayerResponse> {

        return ResponseEntity.ok(playerService.getByUuid(uuid))
    }

    @PostMapping
    fun createPlayer(@RequestBody createPlayerRequest: CreatePlayerRequest): ResponseEntity<CreatePlayerResponse> {

        return ResponseEntity.ok(playerService.save(createPlayerRequest))
    }

    @PutMapping
    fun updatePlayer(@RequestBody updatePlayerRequest: UpdatePlayerRequest): ResponseEntity<UpdatePlayerResponse> {

        return ResponseEntity.ok(playerService.update(updatePlayerRequest))
    }

    @DeleteMapping("/{uuid}")
    fun deletePlayer(@PathVariable uuid: UUID): ResponseEntity<DeletePlayerResponse> {
        playerService.deleteByUuid(uuid)
        return ResponseEntity.ok().build()
    }
}