package models.gameMaster

import models.RequestContext
import java.util.*

data class GameMasterContext(
    var requestContext: RequestContext = RequestContext(),
    var model: GameMasterModel = GameMasterModel(),
    var modelList: List<GameMasterModel> = listOf()
) {
}
data class GameMasterModel(
    var gameMasterUuid: UUID?=null,
    var nickName:String?=null,
)