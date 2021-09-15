package mafia.wizard.services

import mafia.wizard.mappers.user.createUserEntity
import mafia.wizard.mappers.user.setUser
import mafia.wizard.mappers.user.updateUserEntity
import mafia.wizard.openapi.models.CommandResponse
import mafia.wizard.openapi.models.CreateUserRequest
import mafia.wizard.openapi.models.ReadUserResponse
import mafia.wizard.openapi.models.UpdateUserRequest
import mafia.wizard.repository.UserDetailsRepo
import mappers.user.setQuery
import mappers.user.toCommandResponse
import mappers.user.toReadUserResponse
import models.user.UserContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserCrudService(
    private val userDetailsRepo: UserDetailsRepo
) {

    fun getByUuid(uuid: UUID): ReadUserResponse {
        val user = userDetailsRepo.findById(uuid)
            .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $uuid") }
        return UserContext()
            .setUser(user)
            .toReadUserResponse()

    }

    fun createUser(createUserRequest: CreateUserRequest): CommandResponse {
        val createUserContext = UserContext()
            .setQuery(createUserRequest)
        userDetailsRepo.save(createUserContext.createUserEntity())
        return createUserContext.toCommandResponse()

    }

    fun updateUser(updateUserRequest: UpdateUserRequest): CommandResponse {
        val userUuid = updateUserRequest.userUuid ?: throw RuntimeException("UpdateUserRequest empty player uuid")
        val userForUpdate = userDetailsRepo.findById(userUuid)
            .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $userUuid") }
        val updateUserContext = UserContext()
            .setQuery(updateUserRequest)
        userDetailsRepo.save(updateUserContext.updateUserEntity(userForUpdate))
        return updateUserContext.toCommandResponse()
    }
}