package mappers.gameMaster

import mafia.wizard.openapi.models.CommandResponse
import mafia.wizard.openapi.models.GameMasterInfo
import mafia.wizard.openapi.models.ReadAllGameMastersResponse
import mafia.wizard.openapi.models.ReadGameMasterResponse
import models.gameMaster.GameMasterContext


fun GameMasterContext.toReadGameMasterResponse(): ReadGameMasterResponse {
    return ReadGameMasterResponse(
        gameMasterUuid = this.model.gameMasterUuid,
        nickName = this.model.nickName,
        requestUUID = this.requestContext.requestUUID,
        errors = this.requestContext.errors.takeIf { it.isNotEmpty() },
        result = if (this.requestContext.errors.isEmpty()) ReadGameMasterResponse.Result.SUCCESS else ReadGameMasterResponse.Result.ERROR
    )
}

fun GameMasterContext.toCreateGameMasterResponse(): CommandResponse {
    val errors = this.requestContext.errors
    return CommandResponse(
        requestUUID = this.requestContext.requestUUID,
        errors = errors.takeIf { it.isNotEmpty() },
        result = if (errors.isEmpty()) CommandResponse.Result.SUCCESS else CommandResponse.Result.ERROR
    )
}

fun GameMasterContext.toUpdateGameMasterResponse(): CommandResponse {
    val errors = this.requestContext.errors
    return CommandResponse(
        requestUUID = this.requestContext.requestUUID,
        errors = errors.takeIf { it.isNotEmpty() },
        result = if (errors.isEmpty()) CommandResponse.Result.SUCCESS else CommandResponse.Result.ERROR
    )
}

fun GameMasterContext.toReadAllGameMastersResponse(): ReadAllGameMastersResponse {
    return ReadAllGameMastersResponse(
        gameMasters = this.modelList.map { GameMasterInfo(it.gameMasterUuid,it.nickName)},
        errors = this.requestContext.errors.takeIf { it.isNotEmpty() },
        result = if (this.requestContext.errors.isEmpty()) ReadAllGameMastersResponse.Result.SUCCESS else ReadAllGameMastersResponse.Result.ERROR
    )
}
