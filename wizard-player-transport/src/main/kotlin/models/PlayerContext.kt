package models

open class PlayerContext(
    var playerModel: PlayerModel? = null,
    var playerModelList: List<PlayerModel>? = null
) : RequestContext() {

}