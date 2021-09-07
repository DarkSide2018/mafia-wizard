package models

import java.util.*

class DeletePlayerContext(
    requestUUID: UUID,
    var playerUuid: UUID
):RequestContext(requestUUID) {
}