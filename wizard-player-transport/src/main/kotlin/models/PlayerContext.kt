package models

open class PlayerContext(
    var playerModel: PlayerModel? = null,
    var playerModelList: List<PlayerModel>? = null,
    var totalPages: Int? = null,
    var totalElements: Long? = null,
    var pageSize: Int? = null,
    var pageNumber: Int? = null,
    var sortBy: String? = null,
    var sortDir: String? = null,
    var search:String? = null
) : RequestContext() {

}