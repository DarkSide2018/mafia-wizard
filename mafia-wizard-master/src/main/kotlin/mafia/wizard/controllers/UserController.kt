package mafia.wizard.controllers

import mafia.wizard.services.PlayerService
import mafia.wizard.services.UserCrudService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/user")
class UserController(
    private val userCrudService: UserCrudService
) {
    @Autowired
    lateinit var playerService: PlayerService
    @GetMapping("/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<String> {
        return ResponseEntity.ok().build<String>()
    }
    @PostMapping
    fun create(): ResponseEntity<String> {
        return ResponseEntity.ok().build<String>()
    }
    @PutMapping
    fun update(): ResponseEntity<String> {
        return ResponseEntity.ok().build<String>()
    }
}