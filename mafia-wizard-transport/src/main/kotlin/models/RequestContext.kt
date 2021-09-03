package models

import mafia.wizard.openapi.models.RequestError
import java.util.*

open class RequestContext(
    var requestUUID: UUID,
    var status:CorStatus = CorStatus.STARTED
) {
    var errors: MutableList<RequestError> = mutableListOf()

}