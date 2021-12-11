package mafia.wizard.controllers

import mafia.wizard.openapi.models.AddPlayerRequest
import mafia.wizard.openapi.models.BaseResponse
import mafia.wizard.openapi.models.DeletePlayerFromGameRequest
import mafia.wizard.openapi.models.UpdatePlayerInGameRequest
import mafia.wizard.services.PlayerInGameService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/game/player")
class PlayerInGameController(
    private val playerInGameService: PlayerInGameService
) {
    @PostMapping
    fun addPlayer(@RequestBody request: AddPlayerRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(playerInGameService.addPlayer(request))
    }
    @PutMapping
    fun updatePlayerInGame(@RequestBody request: UpdatePlayerInGameRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(playerInGameService.updatePlayerInGame(request))
    }
    @DeleteMapping
    fun deletePlayerFromGame(@RequestBody request: DeletePlayerFromGameRequest): ResponseEntity<String> {
        playerInGameService.deletePlayerFromGame(request)
        return ResponseEntity.ok().build<String>()
    }

}