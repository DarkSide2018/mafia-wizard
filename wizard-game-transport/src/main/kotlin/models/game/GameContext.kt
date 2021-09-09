package models.game

import models.RequestContext

data class GameContext(
    var requestContext: RequestContext=RequestContext(),
    var gameModel: GameModel?=null
) {

}