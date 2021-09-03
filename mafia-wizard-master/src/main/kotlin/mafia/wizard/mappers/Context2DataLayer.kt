package mafia.wizard.mappers

import mafia.wizard.entities.Player
import models.CreatePlayerContext
import models.UpdatePlayerContext
import java.lang.Exception
import java.time.OffsetDateTime
import java.util.*

fun CreatePlayerContext.toPlayerEntity() = Player(
    playerUuid = UUID.randomUUID(),
    ratingId = this.playerModel.ratingId,
    foulAmount = this.playerModel.foulAmount,
    nickName = this.playerModel.nickName,
    points = this.playerModel.points,
    additionalPoints = this.playerModel.additionalPoints,
    penalties= this.playerModel.penalties,
    bestMove = this.playerModel.bestMove,
    victories = this.playerModel.victories,
    victoriesPercent = this.playerModel.victoriesPercent,
    victoriesRed = this.playerModel.victoriesRed,
    victoriesRedPercent = this.playerModel.victoriesRedPercent,
    defeatRed = this.playerModel.defeatRed,
    victoriesBlack = this.playerModel.victoriesBlack,
    defeatBlack = this.playerModel.defeatBlack,
    victoriesBlackPercent = this.playerModel.victoriesBlackPercent,
    don = this.playerModel.don,
    sheriff = this.playerModel.sheriff,
    wasKilled = this.playerModel.wasKilled,
    games = this.playerModel.games,
    rating = this.playerModel.rating,
    createdAt = OffsetDateTime.now(),
    updatedAt = OffsetDateTime.now()
)

fun UpdatePlayerContext.toPlayerEntity() = Player(
    playerUuid = this.playerModel.playerUUID?:throw Exception("empty player uuid for update"),
    ratingId = this.playerModel.ratingId,
    foulAmount = this.playerModel.foulAmount,
    nickName = this.playerModel.nickName,
    points = this.playerModel.points,
    additionalPoints = this.playerModel.additionalPoints,
    penalties= this.playerModel.penalties,
    bestMove = this.playerModel.bestMove,
    victories = this.playerModel.victories,
    victoriesPercent = this.playerModel.victoriesPercent,
    victoriesRed = this.playerModel.victoriesRed,
    victoriesRedPercent = this.playerModel.victoriesRedPercent,
    defeatRed = this.playerModel.defeatRed,
    victoriesBlack = this.playerModel.victoriesBlack,
    defeatBlack = this.playerModel.defeatBlack,
    victoriesBlackPercent = this.playerModel.victoriesBlackPercent,
    don = this.playerModel.don,
    sheriff = this.playerModel.sheriff,
    wasKilled = this.playerModel.wasKilled,
    games = this.playerModel.games,
    rating = this.playerModel.rating,
    updatedAt = OffsetDateTime.now()
)