package mafia.wizard.controllers

import mafia.wizard.entities.Player
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
    @GetMapping("/like")
    fun getByNicknameLike(@RequestParam("nick") nickName: String): ResponseEntity<ReadAllPlayersResponse> {

        return ResponseEntity.ok(playerService.getByNickNameLike(nickName))
    }

    @GetMapping("/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadPlayerResponse> {

        return ResponseEntity.ok(playerService.getByUuid(uuid))
    }
    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<Player>>{
        return ResponseEntity.ok(playerService.getAll())
    }

    @PostMapping
    fun createPlayer(@RequestBody createPlayerRequest: CreatePlayerRequest): ResponseEntity<CommandResponse> {

        return ResponseEntity.ok(playerService.save(createPlayerRequest))
    }

    @PutMapping
    fun updatePlayer(@RequestBody updatePlayerRequest: UpdatePlayerRequest): ResponseEntity<CommandResponse> {

        return ResponseEntity.ok(playerService.update(updatePlayerRequest))
    }

    @DeleteMapping("/{uuid}")
    fun deletePlayer(@PathVariable uuid: UUID): ResponseEntity<CommandResponse> {
        playerService.deleteByUuid(uuid)
        return ResponseEntity.ok().build()
    }
}