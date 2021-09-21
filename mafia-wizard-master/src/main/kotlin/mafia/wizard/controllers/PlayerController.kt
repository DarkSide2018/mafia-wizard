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
    @PostMapping("/like")
    fun getByNicknameLike(@RequestBody searchPlayerRequest: SearchPlayerRequest): ResponseEntity<ReadAllPlayersResponse> {

        return ResponseEntity.ok(playerService.getByNickNameLike(searchPlayerRequest))
    }

    @GetMapping("/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadPlayerResponse> {

        return ResponseEntity.ok(playerService.getByUuid(uuid))
    }
    @PostMapping("/all")
    fun getAll(@RequestBody readAllPlayersRequest: ReadAllPlayersRequest): ResponseEntity<ReadAllPlayersResponse> {
        return ResponseEntity.ok(playerService.getAll(readAllPlayersRequest))
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