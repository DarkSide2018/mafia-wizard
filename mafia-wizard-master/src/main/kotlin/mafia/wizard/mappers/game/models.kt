package mafia.wizard.mappers.game

data class CsvModel(
    var gameMaster:String,
    var gameNumber:String,
    var dateGame: String,
    var gamblers:List<Gambler>,
    var roles:List<Role>,
    var nights:List<CsvNight>,
    var notes:List<Note>,
    var victory:String
)
data class Gambler(
    var id:String,
    var slot:Int
)
data class Role(
    var role:String,
    var slot:Int
)
data class CsvNight(
    var id:Int,
    var don:Int,
    var sheriff:Int,
    var mafia:Int
)
data class Note(
    var gamblerId:Int,
    var notes:Int
)
