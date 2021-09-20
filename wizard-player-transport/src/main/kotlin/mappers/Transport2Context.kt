package mappers

import exceptions.FieldWasNullException
import mafia.wizard.openapi.models.CreatePlayerRequest
import mafia.wizard.openapi.models.SearchPlayerRequest
import mafia.wizard.openapi.models.UpdatePlayerRequest
import models.PlayerContext
import models.PlayerModel
import java.util.*

fun PlayerContext.setCommand(query: CreatePlayerRequest) = apply {
    requestUUID = UUID.randomUUID()
    playerModel = query.toModel()
}

fun PlayerContext.setCommand(query: UpdatePlayerRequest) = apply {
    requestUUID = UUID.randomUUID()
    playerModel = query.toModel()
}

fun PlayerContext.setQuery(searchPlayerRequest: SearchPlayerRequest) = apply {
    requestUUID = UUID.randomUUID()
    pageSize = searchPlayerRequest.pageSize
    pageNumber = searchPlayerRequest.pageNumber
    sortBy = searchPlayerRequest.sortBy
    sortDir = searchPlayerRequest.sortDir
    search = searchPlayerRequest.nickName
}


private fun CreatePlayerRequest.toModel() = PlayerModel(
    playerUUID  = UUID.randomUUID(),
    ratingId = this.ratingId ?: 0,
    foulAmount = this.foulAmount ?: 0,
    nickName = this.nickName ?: throw Exception("CreatePlayerRequest empty nickName"),
    points = this.points ?: 0,
    additionalPoints = this.additionalPoints ?: 0,
    penalties = this.penalties ?: 0,
    bestMove = this.bestMove ?: 0,
    victories = this.victories ?: 0,
    victoriesPercent = this.victoriesPercent ?: 0,
    victoriesRed = this.victoriesRed ?: 0,
    victoriesRedPercent = this.victoriesRedPercent ?: 0,
    defeatRed = this.defeatRed ?: 0,
    victoriesBlack = this.victoriesBlack ?: 0,
    defeatBlack = this.defeatBlack ?: 0,
    victoriesBlackPercent = this.victoriesBlackPercent ?: 0,
    don = this.don ?: 0,
    sheriff = this.sheriff ?: 0,
    wasKilled = this.wasKilled ?: 0,
    games = this.games ?: 0,
    rating = this.rating ?: 0.0,
)

private fun UpdatePlayerRequest.toModel() = PlayerModel(
    playerUUID = this.playerUuid?:throw FieldWasNullException("player uuid"),
    ratingId = this.ratingId,
    foulAmount = this.foulAmount,
    nickName = this.nickName,
    points = this.points,
    additionalPoints = this.additionalPoints,
    penalties = this.penalties,
    bestMove = this.bestMove,
    victories = this.victories,
    victoriesPercent = this.victoriesPercent,
    victoriesRed = this.victoriesRed,
    victoriesRedPercent = this.victoriesRedPercent,
    defeatRed = this.defeatRed,
    victoriesBlack = this.victoriesBlack,
    defeatBlack = this.defeatBlack,
    victoriesBlackPercent = this.victoriesBlackPercent,
    don = this.don,
    sheriff = this.sheriff,
    wasKilled = this.wasKilled,
    games = this.games,
    rating = this.rating,
)