package models.game

import models.RequestContext

data class GameContext(
    var requestContext: RequestContext?,
    var gameModel: GameModel
) {

}