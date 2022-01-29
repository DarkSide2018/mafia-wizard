package mafia.wizard.config

class NotFoundException(
    private val value:String
):Exception(value) {
}

class BadRequestException(
    private val value:String
):Exception(value) {
}

class DbException(
    private val value:String
):Exception(value) {
}