package mafia.wizard.mappers.game

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import exceptions.FieldWasNullException
import mafia.wizard.entities.Game
import mafia.wizard.entities.User
import models.PlayerModel
import models.game.Election
import models.game.GameContext
import models.game.Night
import models.game.PlayerToCardNumber
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

const val EMPTY_ARRAY: String = "[]"

@Component
class GameContext2DataLayer(
    private val objectMapper: ObjectMapper,
) {
    fun toGameEntity(gameContext: GameContext): Game {
        return Game(
            gameUUID = gameContext.gameModel?.gameUUID,
            createdBy = (SecurityContextHolder.getContext().authentication.principal as User).userName,
            name = gameContext.gameModel?.name,
            gameNumber = gameContext.gameModel?.gameNumber,
            elections = gameContext.gameModel?.elections?.let { objectMapper.writeValueAsString(it) },
            nights = gameContext.gameModel?.nights?.let { objectMapper.writeValueAsString(it) },
            players = gameContext.gameModel?.players?.let { objectMapper.writeValueAsString(it) }
        )

    }

    fun updateGameEntity(updateGameContext: GameContext, gameForUpdate: Game): Game {
        val gameModel = updateGameContext.gameModel ?: throw FieldWasNullException("gameModel")
        gameModel.players.takeIf { it.isNotEmpty() }
            ?.let { set ->
                val playerModelSet = objectMapper.readValue(
                    gameForUpdate.players,
                    object : TypeReference<MutableSet<PlayerModel>>() {}) as MutableSet<PlayerModel>
                playerModelSet.addAll(set)
                gameForUpdate.players = objectMapper.writeValueAsString(playerModelSet.distinctBy { it.playerUUID })
            }
        gameModel.gameNumber?.let { gameForUpdate.gameNumber = it }
        gameModel.name?.let { gameForUpdate.name = it }
        gameModel.status?.let { gameForUpdate.status = it }
        gameModel.victory?.let { gameForUpdate.victory = it }
        gameModel.elections.takeIf { it.isNotEmpty() }?.let {electionSet->
            val electionSetRead = objectMapper.readValue(
                gameForUpdate.elections,
                object : TypeReference<MutableSet<Election>>() {}) as MutableSet<Election>
            electionSetRead.addAll(electionSet)
            gameForUpdate.elections=objectMapper.writeValueAsString(electionSetRead)
        }
        updateGameNights(gameModel.nights, gameForUpdate)
        updatePlayerToCardNumber(gameModel.playerToCardNumber, gameForUpdate)
        return gameForUpdate
    }

    private fun updatePlayerToCardNumber(playerToCardNumber: MutableSet<PlayerToCardNumber>?, gameForUpdate: Game) {
        playerToCardNumber?.let { plSet ->
            val plToCardNumbers = objectMapper.readValue(gameForUpdate.playerToCardNumber ?: EMPTY_ARRAY,
                object : TypeReference<MutableSet<PlayerToCardNumber>>() {}) as MutableSet<PlayerToCardNumber>
            plSet.takeIf {
                it.isNotEmpty()
            }?.forEach { firstPl ->
                val filteredPl = plToCardNumbers.firstOrNull {
                    it.cardNumber == firstPl.cardNumber
                } ?: PlayerToCardNumber(cardNumber = firstPl.cardNumber)
                firstPl.gameRole?.let { filteredPl.gameRole = it }
                firstPl.note?.let { filteredPl.note = it }
                firstPl.addPoints?.let { filteredPl.addPoints = it }
                firstPl.playerUuid?.let { filteredPl.playerUuid = it }
                plToCardNumbers.removeIf { it.cardNumber == filteredPl.cardNumber }
                plToCardNumbers.add(filteredPl)
                gameForUpdate.playerToCardNumber = objectMapper.writeValueAsString(plToCardNumbers)
            }
        }
    }

    private fun updateGameNights(nightsIn: MutableSet<Night>?, gameForUpdate: Game) {
        nightsIn?.let { nightSet ->
            val nights = objectMapper.readValue(gameForUpdate.nights ?: EMPTY_ARRAY,
                object : TypeReference<MutableSet<Night>>() {}) as MutableSet<Night>
            nightSet.takeIf {
                it.isNotEmpty()
            }?.first()?.let { firstNight ->
                val filteredNight = nights.firstOrNull {
                    it.nightNumber == firstNight.nightNumber
                } ?: Night(nightNumber = firstNight.nightNumber)
                firstNight.killedPlayer?.let { filteredNight.killedPlayer = it }
                firstNight.donChecked?.let { filteredNight.donChecked = it }
                firstNight.sheriffChecked?.let { filteredNight.sheriffChecked = it }
                firstNight.playerLeftGame?.let { filteredNight.playerLeftGame = it }
                nights.removeIf { it.nightNumber == filteredNight.nightNumber }
                nights.add(filteredNight)
                gameForUpdate.nights = objectMapper.writeValueAsString(nights)
            }
        }
    }
}
