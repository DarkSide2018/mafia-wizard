package mafia.wizard.mappers.game

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import exceptions.FieldWasNullException
import mafia.wizard.entities.Game
import mafia.wizard.entities.User
import models.game.GameContext
import models.game.Night
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

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
            nights = gameContext.gameModel?.nights?.let { objectMapper.writeValueAsString(it) },
            players = gameContext.gameModel?.players?.let { objectMapper.writeValueAsString(it) }
        )

    }

    fun updateGameEntity(updateGameContext: GameContext, gameForUpdate: Game): Game {
        val gameModel = updateGameContext.gameModel ?: throw FieldWasNullException("gameModel")
        gameModel.players.takeIf { it.isNotEmpty() }?.let { gameForUpdate.players = objectMapper.writeValueAsString(it) }
        gameModel.gameNumber?.let { gameForUpdate.gameNumber = it }
        gameModel.status?.let { gameForUpdate.status = it }
        gameModel.nights?.let { nightSet ->
            val nights = objectMapper.readValue(gameForUpdate.nights?:"[]",
                object : TypeReference<MutableSet<Night>>() {}) as MutableSet<Night>
            nightSet.takeIf {
                it.isNotEmpty()
            }?.first()?.let {firstNight->

                val filteredNight = nights.firstOrNull {
                    it.nightNumber == firstNight.nightNumber
                }?: Night(nightNumber =firstNight.nightNumber )
                firstNight.killedPlayer?.let { filteredNight.killedPlayer = it }
                firstNight.donChecked?.let { filteredNight.donChecked = it }
                firstNight.sheriffChecked?.let { filteredNight.sheriffChecked = it }
                nights.removeIf{it.nightNumber == filteredNight.nightNumber}
                nights.add(filteredNight)
                gameForUpdate.nights = objectMapper.writeValueAsString(nights)
            }
        }
        return gameForUpdate
    }
}
