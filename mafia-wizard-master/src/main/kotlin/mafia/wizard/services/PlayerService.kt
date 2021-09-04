package mafia.wizard.services

import mafia.wizard.entities.Player
import mafia.wizard.mappers.setPlayer
import mafia.wizard.mappers.toPlayerEntity
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.PlayerRepo
import mappers.setQuery
import mappers.toCreatePlayerResponse
import mappers.toReadPlayerResponse
import mappers.toUpdatePlayerResponse
import models.CreatePlayerContext
import models.ReadPlayerContext
import models.UpdatePlayerContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.RuntimeException

@Service
class PlayerService {
    @Autowired
    private lateinit var playerRepo: PlayerRepo

    fun getAll(): MutableList<Player> {
        return playerRepo.findAll()
    }

    fun getByUuid(uuid: UUID): ReadPlayerResponse {
        val player = playerRepo.findById(uuid)
            .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $uuid") }
        return ReadPlayerContext()
            .setPlayer(player)
            .toReadPlayerResponse()
    }

    fun getByNickName(nick: String): ReadPlayerResponse {
        val player = playerRepo.findByNickName(nick) ?: throw RuntimeException("no such player")
        return ReadPlayerContext()
            .setPlayer(player)
            .toReadPlayerResponse()
    }

    fun save(createPlayerRequest: CreatePlayerRequest): CreatePlayerResponse {
        val createPlayerContext = CreatePlayerContext()
            .setQuery(createPlayerRequest)
        playerRepo.save(createPlayerContext.toPlayerEntity())
        return createPlayerContext.toCreatePlayerResponse()
    }

    fun update(updatePlayerRequest: UpdatePlayerRequest): UpdatePlayerResponse {
        val playerUuid =
            updatePlayerRequest.playerUuid ?: throw RuntimeException("UpdatePlayerRequest empty player uuid")
        val playerForUpdate = playerRepo.findById(playerUuid)
            .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $playerUuid") }
        val updatePlayerContext = UpdatePlayerContext(playerUuid)
            .setQuery(updatePlayerRequest)

        playerRepo.save(updatePlayerContext.toPlayerEntity(playerForUpdate))
        return updatePlayerContext.toUpdatePlayerResponse()
    }

    fun deleteByUuid(uuid: UUID) {
        playerRepo.deleteById(uuid)
    }
}