package mappers.user

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.CreateUserRequest
import mafia.wizard.openapi.models.UpdateUserRequest
import models.user.UserContext
import models.user.UserModel
import java.util.*

fun UserContext.setQuery(query: CreateUserRequest) = apply {
    userModel = query.toModel()
}

fun UserContext.setQuery(query: UpdateUserRequest) = apply {
    userModel = query.toModel()
}

private fun CreateUserRequest.toModel() = UserModel(
    userUuid = UUID.randomUUID(),
    userName = this.userName,
    password = this.password,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    phoneNumber = this.phoneNumber
)

private fun UpdateUserRequest.toModel() = UserModel(
    userUuid = this.userUuid?:throw FieldWasNullException("UpdateUserRequest userUuid"),
    userName = this.userName,
    password = this.password,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    phoneNumber = this.phoneNumber
)