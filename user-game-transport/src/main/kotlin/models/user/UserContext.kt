package models.user

import models.RequestContext
import java.util.*

data class UserContext(
     var requestContext: RequestContext = RequestContext(),
     var userModel: UserModel?=null
) {
}

data class UserModel(
   var userUuid: UUID,
   var userName: String? = null,
   var password: String? = null,
   var firstName: String? = null,
   var lastName: String? = null,
   var email: String? = null,
   var phoneNumber: String? = null,
) {
}
