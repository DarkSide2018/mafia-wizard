package mafia.wizard.mappers.gameMaster

import exceptions.FieldWasNullException
import mafia.wizard.entities.GameMaster
import models.gameMaster.GameMasterContext
import java.time.OffsetDateTime
import java.util.*

fun GameMasterContext.createGameMasterEntity(): GameMaster {
    return GameMaster(
        masterUuid = UUID.randomUUID(),
        nickName = this.gameMasterModel?.nickName?:throw FieldWasNullException("nickName"),
        createdAt = OffsetDateTime.now(),
        updatedAt = OffsetDateTime.now()
    )
}

fun GameMasterContext.updateGameMasterEntity(gameMasterForUpdate: GameMaster): GameMaster {
    this.gameMasterModel?.nickName?.let { gameMasterForUpdate.nickName = it }
    gameMasterForUpdate.updatedAt = OffsetDateTime.now()
    return gameMasterForUpdate
}