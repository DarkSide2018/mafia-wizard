package models.gameMaster

import models.RequestContext

data class GameMasterContext(
    var requestContext: RequestContext=RequestContext(),
    var gameMasterModel: GameMasterModel=GameMasterModel()
) {
}