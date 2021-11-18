package mafia.wizard.controllers

import mafia.wizard.entities.ACTIVE_STATUS
import mafia.wizard.entities.DRAFT_STATUS
import mafia.wizard.openapi.models.*
import mafia.wizard.services.GameService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/game")
class GameController(
    private val gameService: GameService,
) {
    var logger: Logger = LoggerFactory.getLogger(GameController::class.java)

    @GetMapping("/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadGameResponse> {
        return ResponseEntity.ok(gameService.getByUuid(uuid))
    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<ReadAllGamesResponse> {
        return ResponseEntity.ok(gameService.getAll())
    }

    @GetMapping("/draft")
    fun draftGame(): ResponseEntity<ReadGameResponse> {
        logger.info("draft game retrieve")
        return ResponseEntity.ok(gameService.getGameByStatus(DRAFT_STATUS))
    }

    @GetMapping("/active")
    fun activeGame(): ResponseEntity<ReadGameResponse> {
        logger.info("active game retrieve")
        return ResponseEntity.ok(gameService.getGameByStatus(ACTIVE_STATUS))
    }

    @PostMapping
    fun createGame(@RequestBody game: CreateGameRequest): ResponseEntity<BaseResponse> {
        logger.info("create game command")
        return ResponseEntity.ok(gameService.createGame(game))
    }
    @PostMapping("/election")
    fun createGame(@RequestBody request: FinishElectionRequest): ResponseEntity<BaseResponse> {
        logger.info("finish election command")
        return ResponseEntity.ok(gameService.finishElection(request))
    }

    @PostMapping("/player")
    fun addPlayer(@RequestBody request: AddPlayerRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(gameService.addPlayer(request))
    }

    @PutMapping("/player")
    fun updatePlayerInGame(@RequestBody request: UpdatePlayerInGameRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(gameService.updatePlayerInGame(request))
    }

    @PutMapping
    fun updateGame(@RequestBody game: UpdateGameRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(gameService.updateGame(game))
    }
    @PutMapping("/finish")
    fun finishGame(@RequestBody game: UpdateGameRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(gameService.finishGame(game))
    }

    @DeleteMapping("{uuid}")
    fun deleteGame(@PathVariable uuid: UUID): ResponseEntity<String> {
        gameService.deleteGame(uuid)
        return ResponseEntity.ok().build<String>()
    }

}