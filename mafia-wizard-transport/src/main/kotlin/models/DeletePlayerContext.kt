package models

import mafia.wizard.openapi.models.RequestError
import java.util.*

class DeletePlayerContext(
    requestUUID: UUID,
    var playerUuid: UUID
):RequestContext(requestUUID) {
}