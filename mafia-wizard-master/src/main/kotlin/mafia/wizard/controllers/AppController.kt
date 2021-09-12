package mafia.wizard.controllers

import mafia.wizard.entities.Player
import mafia.wizard.services.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class AppController {
    @Autowired
    lateinit var playerService: PlayerService
    @GetMapping("/")
    fun testApp(): String {
        return "Hello Spring Security!"
    }

}