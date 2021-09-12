package mappers.gameMaster

import mafia.wizard.openapi.models.CreateGameMasterRequest
import mafia.wizard.openapi.models.UpdateGameMasterRequest
import models.RequestContext
import models.gameMaster.GameMasterContext
import models.gameMaster.GameMasterModel
import java.util.*

fun GameMasterContext.setQuery(query: CreateGameMasterRequest) = apply {
    model = query.toModel()
}

fun GameMasterContext.setQuery(query: UpdateGameMasterRequest) = apply {
    model = query.toModel()
}

private fun CreateGameMasterRequest.toModel() = GameMasterModel(
    gameMasterUuid = UUID.randomUUID(),
    nickName = this.nickName ?: throw Exception("CreatePlayerRequest empty nickName"),
)

private fun UpdateGameMasterRequest.toModel() = GameMasterModel(
    gameMasterUuid = this.gameMasterUuid?:throw Exception("UpdateGameMasterRequest gameMasterUuid was null"),
    nickName = this.nickName,
)