package mafia.wizard.services

import mafia.wizard.entities.User
import mafia.wizard.repository.UserDetailsRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserCrudService(
    private val userDetailsRepo: UserDetailsRepo
) {

    fun getByUuid(uuid: UUID) {
        userDetailsRepo.getById(uuid)
    }

    fun createUser(user: User) {
        userDetailsRepo.save(user)
    }

    fun updateUser(user: User) {
        userDetailsRepo.save(user)
    }
}