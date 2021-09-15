package mafia.wizard.controllers

import mafia.wizard.openapi.models.CommandResponse
import mafia.wizard.openapi.models.CreateUserRequest
import mafia.wizard.openapi.models.ReadUserResponse
import mafia.wizard.openapi.models.UpdateUserRequest
import mafia.wizard.services.UserCrudService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/user")
class UserController(
    private val userCrudService: UserCrudService
) {
    @GetMapping("/{uuid}")
    fun getByUuid(@PathVariable uuid: UUID): ResponseEntity<ReadUserResponse> {
        return ResponseEntity.ok(userCrudService.getByUuid(uuid))
    }

    @PostMapping
    fun create(@RequestBody createUserRequest: CreateUserRequest): ResponseEntity<CommandResponse> {
        return ResponseEntity.ok(userCrudService.createUser(createUserRequest))
    }

    @PutMapping
    fun update(@RequestBody updateUserRequest: UpdateUserRequest): ResponseEntity<CommandResponse> {
        return ResponseEntity.ok(userCrudService.updateUser(updateUserRequest))
    }
}