package models.game

import models.RequestContext

data class GameContext(
    var page:Int?=null,
    var requestContext: RequestContext =RequestContext(),
    var gameModel: GameModel?=null,
    var gameModelList: List<GameModel>?=null
) {

}