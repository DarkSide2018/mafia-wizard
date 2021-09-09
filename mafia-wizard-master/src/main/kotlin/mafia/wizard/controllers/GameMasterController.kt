package mafia.wizard.controllers

import mafia.wizard.openapi.models.*
import mafia.wizard.services.GameMasterService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/game/master/")
class GameMasterController(
    private val gameMasterService: GameMasterService
) {
    @GetMapping("{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadGameMasterResponse> {
        return ResponseEntity.ok(gameMasterService.getByUuid(uuid))
    }
    @PostMapping
    fun createGame(@RequestBody gameMaster: CreateGameMasterRequest): ResponseEntity<CommandResponse> {

       return ResponseEntity.ok(gameMasterService.createGameMaster(gameMaster))
    }

    @PutMapping
    fun updateGame(@RequestBody game: UpdateGameMasterRequest): ResponseEntity<CommandResponse> {
        return ResponseEntity.ok(gameMasterService.updateGameMaster(game))
    }

    @DeleteMapping("{uuid}")
    fun deleteGame(@PathVariable uuid: UUID) {
        gameMasterService.deleteGameMaster(uuid)
    }


}