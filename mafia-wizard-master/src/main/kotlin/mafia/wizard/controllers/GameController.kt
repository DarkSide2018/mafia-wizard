package mafia.wizard.controllers

import mafia.wizard.config.Trace
import mafia.wizard.entities.ACTIVE_STATUS
import mafia.wizard.entities.DRAFT_STATUS
import mafia.wizard.openapi.models.*
import mafia.wizard.services.GameService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/game")
class GameController(
    private val gameService: GameService,
) {
    var logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadGameResponse> {
        return ResponseEntity.ok(gameService.getByUuid(uuid))
    }
    @GetMapping("/export/{uuid}")
    fun exportByUuid(@PathVariable uuid: UUID): ResponseEntity<Resource> {
        return ResponseEntity.ok(gameService.exportByUuid(uuid))
    }

    @PostMapping("/all")
    fun getAll(@RequestBody request: ReadAllGamesRequest): ResponseEntity<ReadAllGamesResponse> {
        return ResponseEntity.ok(gameService.getAll(request))
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
    @Trace
    fun finishElection(@RequestBody request: FinishElectionRequest): ResponseEntity<BaseResponse> {
        logger.info("finish election command")
        return ResponseEntity.ok(gameService.finishElection(request))
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

    @DeleteMapping("/election/{game}/{election}")
    fun deleteElectionFromGame(
        @PathVariable game: UUID,
        @PathVariable election: UUID,
    ): ResponseEntity<String> {
        gameService.deleteElectionFromGame(game, election)
        return ResponseEntity.ok().build<String>()
    }

    @PostMapping("/role")
    fun updateRoleInGame(
        @RequestBody updateRoleRequest: UpdateRoleRequest,
    ): ResponseEntity<String> {
        gameService.updateRolesInGame(updateRoleRequest)
        return ResponseEntity.ok().build<String>()
    }

    @PutMapping("/notes")
    fun updateNotesIngGame(@RequestBody game: UpdateNotesRequest): ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(gameService.updateNotesInGame(game))
    }
}