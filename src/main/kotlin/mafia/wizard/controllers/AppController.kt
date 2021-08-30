package mafia.wizard.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class AppController {
    @GetMapping("/")
    fun testApp(): String {
        return "Hello Spring Security!"
    }

    @GetMapping("/test")
    fun testEndPont(): String{
        return "Hello test!"
    }
}