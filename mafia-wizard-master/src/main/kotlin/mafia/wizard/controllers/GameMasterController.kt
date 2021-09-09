package mafia.wizard.controllers

import mafia.wizard.openapi.models.CreateGameRequest
import mafia.wizard.openapi.models.UpdateGameRequest
import mafia.wizard.services.GameMasterService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/game/master")
class GameMasterController(
    private val gameMasterService: GameMasterService
) {
    @GetMapping("{uuid}")
    fun getByUuid(@PathVariable uuid: UUID) {
        gameMasterService.getByUuid(uuid)
    }

    @GetMapping("/all")
    fun getAll() {
        gameMasterService.getAll()
    }

    @PostMapping
    fun createGame(@RequestBody game: CreateGameRequest) {
     //   gameService.createGame(game)
    }

    @PutMapping
    fun updateGame(@RequestBody game: UpdateGameRequest) {
     //   gameService.updateGame(game)
    }

    @DeleteMapping("{uuid}")
    fun deleteGame(@PathVariable uuid: UUID) {
     //   gameService.deleteGame(uuid)
    }


}