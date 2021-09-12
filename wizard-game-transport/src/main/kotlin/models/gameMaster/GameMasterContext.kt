package models.gameMaster

import models.RequestContext

data class GameMasterContext(
    var requestContext: RequestContext = RequestContext(),
    var model: GameMasterModel = GameMasterModel(),
    var modelList: List<GameMasterModel> = listOf()
) {
}