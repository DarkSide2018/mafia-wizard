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
            calculatePercentage(player)
            playerRepo.save(player)
        }
    }

    fun calculatePercentage(player: Player) {
        player.victoriesPercent =
            player.victories.toBigDecimal()
                .divide(player.games.toBigDecimal(),RoundingMode.HALF_UP)
                .multiply(100.00.toBigDecimal())
                .toLong()
        player.victoriesRedPercent =
            player.victoriesRed.toBigDecimal()
                .divide(player.games.toBigDecimal(),RoundingMode.HALF_UP)
                .multiply(100.00.toBigDecimal())
                .toLong()
        player.victoriesBlackPercent =
            player.victoriesBlack.toBigDecimal()
                .divide(player.games.toBigDecimal(),RoundingMode.HALF_UP)
                .multiply(100.00.toBigDecimal())
                .toLong()
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
            if (it.nightNumber == 1) player.wasKilled++
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
                player.victoriesRed++
            }
            BLACK_VICTORY -> {
                player.defeatBlack++
            }
        }
    }

    fun calculateVictoriesAsBlackRole(player: Player, game: GameContext) {
        when (game.gameModel?.victory) {
            RED_VICTORY -> {
                player.defeatRed++
            }
            BLACK_VICTORY -> {
                player.points++
                player.victoriesBlack++
            }
        }
    }
}