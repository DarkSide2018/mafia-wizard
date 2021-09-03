package models

import java.util.*

open class PlayerContext(
    requestUUID: UUID,
    var playerModel:PlayerModel
):RequestContext(requestUUID) {

}