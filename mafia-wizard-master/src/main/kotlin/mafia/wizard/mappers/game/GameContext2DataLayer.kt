package mafia.wizard.mappers.game

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import exceptions.FieldWasNullException
import mafia.wizard.common.toJson
import mafia.wizard.entities.Game
import mafia.wizard.entities.User
import models.PlayerModel
import models.game.*
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

    fun toGameEntity(gameModel: GameModel): Game {
        return Game(
            gameUUID = gameModel.gameUUID,
            createdBy = (SecurityContextHolder.getContext().authentication.principal as User).userName,
            name = gameModel.name,
            gameNumber = gameModel.gameNumber,
            elections = gameModel.elections?.let { objectMapper.writeValueAsString(it) },
            nights = gameModel.nights?.let { objectMapper.writeValueAsString(it) },
            players = gameModel.players?.let { objectMapper.writeValueAsString(it) },
            playerToCardNumber = gameModel.playerToCardNumber.toJson(),
            victory = gameModel.victory
        )
    }

    fun updateNotesBySlot(updateGameContext: GameContext, gameForUpdate: Game) {
        val plsSet = objectMapper.readValue(
            gameForUpdate.playerToCardNumber,
            object : TypeReference<MutableSet<PlayerToCardNumber>>() {}) as MutableSet<PlayerToCardNumber>
        plsSet.forEach {
            val first = updateGameContext.gameModel?.playerToCardNumber?.first()
            if (it.cardNumber == first?.cardNumber) {
                it.note = first?.note ?: throw Exception("note was null")
            }
        }
        gameForUpdate.playerToCardNumber = objectMapper.writeValueAsString(plsSet)
    }

    fun updateRoleBySlot(updateGameContext: GameContext, gameForUpdate: Game) {
        val plsSet = objectMapper.readValue(
            gameForUpdate.playerToCardNumber?:"[]",
            object : TypeReference<MutableSet<PlayerToCardNumber>>() {}) as MutableSet<PlayerToCardNumber>
        val first = updateGameContext.gameModel?.playerToCardNumber?.first()
        plsSet.forEach {
            if(it.gameRole == first?.gameRole){
                it.gameRole = null
            }
        }
        plsSet.firstOrNull { it.cardNumber == first?.cardNumber }?.let {
            it.gameRole = first?.gameRole ?: throw Exception("game role was null")
        } ?: run {
            plsSet.add(PlayerToCardNumber(cardNumber = first?.cardNumber, gameRole = first?.gameRole))
        }
        gameForUpdate.playerToCardNumber = objectMapper.writeValueAsString(plsSet)
    }

    fun updateGameEntity(updateGameContext: GameContext, gameForUpdate: Game): Game {
        val gameModel = updateGameContext.gameModel ?: throw FieldWasNullException("gameModel")
        val gameModelPlayers = gameModel.players
        gameModelPlayers.takeIf { !it.isNullOrEmpty() }
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
        gameModel.elections?.takeIf { it.isNotEmpty() }?.let { electionSet ->
            val electionSetRead = objectMapper.readValue(
                gameForUpdate.elections,
                object : TypeReference<MutableSet<Election>>() {}) as MutableSet<Election>
            electionSet.first().sortOrder = electionSetRead.size
            electionSetRead.addAll(electionSet)
            electionSetRead.sortedBy { it.sortOrder }
            gameForUpdate.elections = electionSetRead.toJson()
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
                firstPl.playerNickName?.let { filteredPl.playerNickName = it }
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
