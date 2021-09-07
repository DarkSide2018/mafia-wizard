package models.game

data class CreateGameContext(
    var gameNumber:Int?,
    var players:List<GamePlayer>?
) {

}