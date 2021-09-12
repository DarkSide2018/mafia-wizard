package mafia.wizard.mappers.gameMaster

import exceptions.FieldWasNullException
import mafia.wizard.entities.GameMaster

import models.gameMaster.GameMasterContext
import models.gameMaster.GameMasterModel

fun GameMasterContext.setGameMaster(gameMaster: GameMaster) = apply {
    this.model.gameMasterUuid = gameMaster.masterUuid?:throw FieldWasNullException("masterUuid")
    this.model.nickName = gameMaster.nickName?:throw FieldWasNullException("nickName")
}

fun GameMasterContext.setGameMasterList(gameMasterList: List<GameMaster>) = apply {
    this.modelList = gameMasterList.map { GameMasterModel(it.masterUuid,it.nickName) }
}