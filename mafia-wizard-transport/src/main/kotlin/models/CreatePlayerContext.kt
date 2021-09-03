package models

import mafia.wizard.openapi.models.RequestError
import java.util.*

class CreatePlayerContext(
    requestUUID: UUID,
    playerModel: PlayerModel
):PlayerContext(requestUUID,playerModel) {
}