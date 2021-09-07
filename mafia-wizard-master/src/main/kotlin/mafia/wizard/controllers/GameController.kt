package mafia.wizard.controllers

import mafia.wizard.services.GameService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/game")
class GameController(
    private val gameService: GameService
) {

}