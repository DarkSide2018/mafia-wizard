package models.game

import models.PlayerModel
import java.util.*

data class GameModel(
    var gameUUID:UUID,
    var gameNumber:Long?,
    var gameTable:String?=null,
    var victory:String?=null,
    var don:UUID?=null,
    var sheriff:UUID?=null,
    var criminalOne:UUID?=null,
    var criminalTwo: UUID?=null,
    var name:String?=null,
    var status:String?=null,
    var players:MutableSet<PlayerModel> = mutableSetOf<PlayerModel>()
){
    fun addPlayer(value:PlayerModel){
        players.add(value)
    }
}
