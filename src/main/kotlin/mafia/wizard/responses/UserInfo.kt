package mafia.wizard.responses

class UserInfo {
    var firstName: String? = null
    var lastName: String? = null
    private var userName: String? = null

    var roles: Any? = null

    fun getUserName(): String? {
        return userName
    }

    fun setUserName(userName: String?) {
        this.userName = userName
    }
}