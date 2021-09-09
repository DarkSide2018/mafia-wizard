package mafia.wizard.mappers.gameMaster

import exceptions.FieldWasNullException
import mafia.wizard.entities.GameMaster

import models.gameMaster.GameMasterContext

fun GameMasterContext.setGameMaster(gameMaster: GameMaster) = apply {
    this.gameMasterModel?.gameMasterUuid = gameMaster.masterUuid?:throw FieldWasNullException("masterUuid")
    this.gameMasterModel?.nickName = gameMaster.nickName?:throw FieldWasNullException("nickName")
}