package mafia.wizard.controllers

import mafia.wizard.services.GameMasterService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/game/master")
class GameMasterController(
    private val gameMasterService: GameMasterService
) {


}