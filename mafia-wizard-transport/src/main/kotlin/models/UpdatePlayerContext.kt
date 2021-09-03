package models

import java.util.*

class UpdatePlayerContext(
    var playerUUID: UUID,
    requestUUID: UUID,
    playerModel: PlayerModel
):PlayerContext(requestUUID,playerModel) {

}