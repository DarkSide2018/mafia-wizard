package mafia.wizard.mappers.player

import mafia.wizard.entities.Player
import models.PlayerModel
import models.ReadPlayerContext

fun ReadPlayerContext.setPlayer(player: Player) = apply {
    playerUUID = player.playerUuid
    playerModel = player.toModel()
}

private fun Player.toModel() = PlayerModel(
    ratingId = this.ratingId,
    foulAmount = this.foulAmount,
    nickName = this.nickName,
    points = this.points,
    additionalPoints = this.additionalPoints,
    penalties= this.penalties,
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