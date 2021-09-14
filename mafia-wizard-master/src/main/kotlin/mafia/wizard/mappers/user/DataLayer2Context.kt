package mafia.wizard.mappers.user

import mafia.wizard.entities.User
import models.user.UserContext
import models.user.UserModel

fun UserContext.setUser(user: User) = apply {
    userModel = user.toModel()
}

private fun User.toModel() = UserModel(
    userUuid = this.user_id,
    userName = this.userName,
    password = this.password,
    firstName = this.firstName,
    lastName = this.lastName,
    email= this.email,
    phoneNumber = this.phoneNumber
)