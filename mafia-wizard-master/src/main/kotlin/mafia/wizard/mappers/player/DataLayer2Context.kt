package mafia.wizard.mappers.player

import mafia.wizard.entities.Player
import models.PlayerContext
import models.PlayerModel

fun PlayerContext.setPlayer(player: Player) = apply {
    playerModel = player.toModel()
}

fun PlayerContext.setPlayers(players: List<Player>) = apply {
    playerModelList = players.map { it.toModel() }
}

private fun Player.toModel() = PlayerModel(
    playerUUID = this.playerUuid,
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