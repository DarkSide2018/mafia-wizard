package models

import mafia.wizard.openapi.models.RequestError
import java.util.*

class ReadPlayerContext(
):PlayerContext() {
    var playerUUID: UUID?=null
}