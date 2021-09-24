package mafia.wizard.controllers

import mafia.wizard.openapi.models.*
import mafia.wizard.services.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/game")
class GameController(
    private val gameService: GameService
) {
    @GetMapping("/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadGameResponse> {
        return ResponseEntity.ok(gameService.getByUuid(uuid))
    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<ReadAllGamesResponse> {
        return ResponseEntity.ok(gameService.getAll())
    }

    @PostMapping
    fun createGame(@RequestBody game: CreateGameRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(gameService.createGame(game))
    }
    @PostMapping("/player")
    fun addPlayer(@RequestBody request: AddPlayerRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(gameService.addPlayer(request))
    }

    @PutMapping
    fun updateGame(@RequestBody game: UpdateGameRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(gameService.updateGame(game))
    }

    @DeleteMapping("{uuid}")
    fun deleteGame(@PathVariable uuid: UUID): ResponseEntity<String> {
        gameService.deleteGame(uuid)
        return ResponseEntity.ok().build<String>()
    }

}