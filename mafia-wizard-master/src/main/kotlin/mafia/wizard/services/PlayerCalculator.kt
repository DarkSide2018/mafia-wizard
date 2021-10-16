package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.entities.Player
import mafia.wizard.repository.PlayerRepo
import models.game.GameContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class PlayerCalculator(
    private val playerRepo: PlayerRepo,
) {
    var logger: Logger = LoggerFactory.getLogger(javaClass)
    private val FREE_STATUS = "FREE"
    private final val maf: String = "Мафиози"
    private final val citizen: String = "Мирный"
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
                .divide(player.games.toBigDecimal())
                .multiply(100.00.toBigDecimal())
                .toLong()
        player.victoriesRedPercent =
            player.victoriesRed.toBigDecimal()
                .divide(player.games.toBigDecimal())
                .multiply(100.00.toBigDecimal())
                .toLong()
        player.victoriesBlackPercent =
            player.victoriesBlack.toBigDecimal()
                .divide(player.games.toBigDecimal())
                .multiply(100.00.toBigDecimal())
                .toLong()
    }

    fun setPlayerProperties(player: Player, game: GameContext) {
        player.games++
        player.status = FREE_STATUS
        calculatePoints(player, game)
        calculateDonAndSheriff(player, game)
        calculateFirstNightKill(player, game)
        calculateVictories(player, game)
    }

    fun calculatePoints(player: Player, game: GameContext) {
        game.gameModel?.players?.firstOrNull { it.playerUUID == player.playerUuid }?.let { playerModel ->
            playerModel.penalties?.let { player.penalties += it }
            playerModel.foulAmount?.let { player.foulAmount += it }
            playerModel.points?.let { player.points += it }
            playerModel.additionalPoints?.let { player.additionalPoints += it }
        }

    }

    fun calculateDonAndSheriff(player: Player, game: GameContext) {
        game.gameModel?.playerToCardNumber?.firstOrNull { it.playerUuid == player.playerUuid }?.let {
            if (it.gameRole == "Дон") player.don++
            if (it.gameRole == "Шериф") player.sheriff++
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
                player.victories++
            }
        }
    }

    fun calculateVictoriesAsRedRole(player: Player, game: GameContext) {
        when (game.gameModel?.victory) {
            "Красные" -> {
                player.victoriesRed++
            }
            "Черные" -> {
                player.victoriesBlack++
            }
            else -> throw Exception("Unexpected victory")
        }
    }

    fun calculateVictoriesAsBlackRole(player: Player, game: GameContext) {
        when (game.gameModel?.victory) {
            "Красные" -> {
                player.defeatBlack++
            }
            "Черные" -> {
                player.victoriesBlack++
            }
            else -> throw Exception("Unexpected victory")
        }
    }

}