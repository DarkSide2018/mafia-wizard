package mafia.wizard.mappers.user

import mafia.wizard.entities.User
import models.user.UserContext
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.OffsetDateTime

fun UserContext.createUserEntity(): User {
    val userModelNotNull = this.userModel ?: throw Exception("createUserEntity null user model")
    return User(
        user_id = userModelNotNull.userUuid,
        userName = userModelNotNull.userName,
        password = userModelNotNull.password,
        firstName = userModelNotNull.firstName,
        lastName = userModelNotNull.lastName,
        email = userModelNotNull.email,
        phoneNumber = userModelNotNull.phoneNumber,
        createdAt = OffsetDateTime.now(),
        updatedAt = OffsetDateTime.now()
    )
}

fun UserContext.updateUserEntity(userForUpdate:User): User {
    val userModelNotNull = this.userModel?:throw Exception("updateUserEntity null user model")
    with(userModelNotNull){
        userName?.let { userForUpdate.userName = it }
        password?.let { userForUpdate.password = it }
        firstName?.let{userForUpdate.firstName = it}
        lastName?.let { userForUpdate.lastName = it }
        email?.let { userForUpdate.email = it }
        phoneNumber?.let { userForUpdate.phoneNumber = it }
        userForUpdate.updatedAt = OffsetDateTime.now()
    }
    return userForUpdate
}


fun UserContext.encodePassword(passwordEncoder: PasswordEncoder) = apply {
    val model = this.userModel
    this.userModel?.password = passwordEncoder.encode(model?.password)
}