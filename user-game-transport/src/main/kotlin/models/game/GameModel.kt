package models.game

import models.PlayerModel
import java.util.*

data class GameModel(
    var gameUUID:UUID,
    var gameNumber:Long?,
    var name:String?=null,
    var players:MutableList<PlayerModel> = mutableListOf()
){
    fun addPlayer(value:PlayerModel){
        players.add(value)
    }
}
