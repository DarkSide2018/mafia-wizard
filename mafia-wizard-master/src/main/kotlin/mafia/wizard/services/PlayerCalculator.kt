package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.entities.Player
import mafia.wizard.repository.PlayerRepo
import models.game.GameContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.RoundingMode

@Service
class PlayerCalculator(
    private val playerRepo: PlayerRepo,
) {
    var logger: Logger = LoggerFactory.getLogger(javaClass)
    private val BLACK_VICTORY = "Черные"
    private val FREE_STATUS = "FREE"
    private val RED_VICTORY = "Красные"
    private val maf: String = "Мафиози"
    private val citizen: String = "Мирный"
    val BLACK_ROLES = listOf<String>(
        "Дон",
        "${maf}1",
        "${maf}2")
    val RED_ROLES =
        listOf<String>(
            "Шериф",
            "${citizen}1",
            "${citizen}2",
            "${citizen}3",
            "${citizen}4",
            "${citizen}5",
            "${citizen}6")

    fun playerBusyToFreeStatus(game: GameContext) {
        game.gameModel?.players?.forEach {
            val player = playerRepo.getById(it.playerUUID ?: throw FieldWasNullException("playerUuid"))
            setPlayerProperties(player, game)
            calculatePercentage(player, game)
            calculateRating(player)
            playerRepo.save(player)
        }
    }
    fun calculateRating(player: Player) {
        player.rating = (player.points.toBigDecimal()
            .plus(player.additionalPoints)
            .minus(player.penalties.toBigDecimal()))
            .multiply(player.victoriesPercent.toBigDecimal().divide(100.00.toBigDecimal(),2,RoundingMode.HALF_EVEN))
    }

    fun calculatePercentage(player: Player, game: GameContext) {
        player.victoriesPercent =
            player.victories.toBigDecimal()
                .divide(player.games.toBigDecimal(), 2, RoundingMode.HALF_EVEN)
                .multiply(100.00.toBigDecimal())
                .toLong()
        game.gameModel?.playerToCardNumber?.firstOrNull { it.playerUuid == player.playerUuid }?.let {
            it.gameRole?.let { gameRole ->
                if (RED_ROLES.contains(gameRole)) {
                    player.victoriesRedPercent =
                        player.victoriesRed.toBigDecimal()
                            .divide(player.games.toBigDecimal()
                                .minus(player.victoriesBlack.toBigDecimal().minus(player.defeatBlack.toBigDecimal())),
                                2,
                                RoundingMode.HALF_UP)
                            .multiply(100.00.toBigDecimal())
                            .toLong()
                } else {
                    player.victoriesBlackPercent =
                        player.victoriesBlack.toBigDecimal()
                            .divide(player.games.toBigDecimal()
                                .minus(player.victoriesRed.toBigDecimal().minus(player.defeatRed.toBigDecimal())),
                                2,
                                RoundingMode.HALF_UP)
                            .multiply(100.00.toBigDecimal())
                            .toLong()
                }
            }
        }
    }

    fun setPlayerProperties(player: Player, game: GameContext) {
        player.games++
        player.status = FREE_STATUS
        calculateVictories(player, game)
        calculatePoints(player, game)
        calculateDonAndSheriff(player, game)
        calculateFirstNightKill(player, game)
    }

    fun calculatePoints(player: Player, game: GameContext) {
        game.gameModel?.playerToCardNumber?.firstOrNull { it.playerUuid == player.playerUuid }?.let { pcn ->
            pcn.note?.let { player.foulAmount += it }
            pcn.addPoints?.let { player.additionalPoints += it }
        }
    }

    fun calculateDonAndSheriff(player: Player, game: GameContext) {
        game.gameModel?.playerToCardNumber?.firstOrNull { it.playerUuid == player.playerUuid }?.let {
            if (it.gameRole == "Дон") {
                player.don++
                if (game.gameModel?.victory == BLACK_VICTORY) player.points++
            }

            if (it.gameRole == "Шериф") {
                player.sheriff++
                if (game.gameModel?.victory == RED_VICTORY) player.points++
            }
        }
    }

    fun calculateFirstNightKill(player: Player, game: GameContext) {
        game.gameModel?.nights?.firstOrNull { it.killedPlayer == player.playerUuid }?.let {
            if (it.nightNumber == 0) player.wasKilled++
        }
    }

    fun calculateVictories(player: Player, game: GameContext) {
        game.gameModel?.playerToCardNumber?.firstOrNull { it.playerUuid == player.playerUuid }?.let {
            it.gameRole?.let { gameRole ->
                if (RED_ROLES.contains(gameRole)) {
                    calculateVictoriesAsRedRole(player, game)
                } else {
                    calculateVictoriesAsBlackRole(player, game)
                }
            }
        }
    }

    fun calculateVictoriesAsRedRole(player: Player, game: GameContext) {
        when (game.gameModel?.victory) {
            RED_VICTORY -> {
                player.points++
                player.victories++
                player.victoriesRed++
            }
            BLACK_VICTORY -> {
                player.defeatRed++
            }
        }
    }

    fun calculateVictoriesAsBlackRole(player: Player, game: GameContext) {
        when (game.gameModel?.victory) {
            RED_VICTORY -> {
                player.defeatBlack++
            }
            BLACK_VICTORY -> {
                player.victories++
                player.points++
                player.victoriesBlack++
            }
        }
    }
}