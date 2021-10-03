package mafia.wizard.config

class NotFoundException(
    private val value:String
):Exception(value) {
}