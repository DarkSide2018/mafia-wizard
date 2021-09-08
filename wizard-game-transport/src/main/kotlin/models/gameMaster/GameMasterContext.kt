package models.gameMaster

import models.RequestContext

data class GameMasterContext(
    var requestContext: RequestContext,
    var gameMasterModel: GameMasterModel,
) {
}