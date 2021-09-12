package mafia.wizard.controllers

import mafia.wizard.openapi.models.CommandResponse
import mafia.wizard.openapi.models.CreateGameRequest
import mafia.wizard.openapi.models.ReadGameResponse
import mafia.wizard.openapi.models.UpdateGameRequest
import mafia.wizard.services.GameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/game/")
class GameController(
    private val gameService: GameService
) {
    @GetMapping("{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadGameResponse> {
        return ResponseEntity.ok(gameService.getByUuid(uuid))
    }

    @PostMapping
    fun createGame(@RequestBody game: CreateGameRequest): ResponseEntity<CommandResponse> {
        return ResponseEntity.ok(gameService.createGame(game))
    }

    @PutMapping
    fun updateGame(@RequestBody game: UpdateGameRequest): ResponseEntity<CommandResponse> {
        return ResponseEntity.ok(gameService.updateGame(game))
    }

    @DeleteMapping("{uuid}")
    fun deleteGame(@PathVariable uuid: UUID): ResponseEntity<String> {
        gameService.deleteGame(uuid)
        return ResponseEntity.ok().build<String>()
    }
}