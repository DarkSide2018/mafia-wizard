package mappers.user

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.BaseResponse

import mafia.wizard.openapi.models.ReadUserResponse
import models.user.UserContext


fun UserContext.toReadUserResponse(): ReadUserResponse {
    return ReadUserResponse(
        userUuid = this.userModel?.userUuid?:throw FieldWasNullException("ReadUserResponse userUuid"),
        userName = this.userModel?.userName,
        requestUUID = this.requestContext.requestUUID,
        errors = this.requestContext.errors.takeIf { it.isNotEmpty() },
        result = if (this.requestContext.errors.isEmpty()) ReadUserResponse.Result.SUCCESS else ReadUserResponse.Result.ERROR
    )
}

fun UserContext.toCommandResponse(): BaseResponse {
    val errors = this.requestContext.errors
    return BaseResponse(
        requestUUID = this.requestContext.requestUUID,
        errors = errors.takeIf { it.isNotEmpty() },
        result = if (errors.isEmpty()) BaseResponse.Result.SUCCESS else BaseResponse.Result.ERROR
    )
}