package models

import mafia.wizard.openapi.models.RequestError
import java.util.*

class ReadPlayerContext(
    requestUUID: UUID,
    var playerUUID: UUID,
    playerModel: PlayerModel
):PlayerContext(requestUUID,playerModel) {

}