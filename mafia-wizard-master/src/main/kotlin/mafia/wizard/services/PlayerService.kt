package mafia.wizard.services

import mafia.wizard.entities.Player
import mafia.wizard.mappers.player.setPlayer
import mafia.wizard.mappers.player.setPlayers
import mafia.wizard.mappers.player.toPlayerEntity
import mafia.wizard.mappers.player.updatePlayer
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.PlayerRepo
import mappers.*
import models.PlayerContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

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
        return PlayerContext()
            .setPlayer(player)
            .toReadPlayerResponse()
    }

    fun getByNickName(nick: String): ReadPlayerResponse {
        val player = playerRepo.findByNickName(nick) ?: throw RuntimeException("no such player")
        return PlayerContext()
            .setPlayer(player)
            .toReadPlayerResponse()
    }

    fun getByNickNameLike(nick: String): ReadAllPlayersResponse {
        val players = playerRepo.findByNickNameLike("%${nick}%") ?: throw RuntimeException("no such players")
        return PlayerContext()
            .setPlayers(players)
            .toReadAllPlayersResponse()
    }

    fun save(createPlayerRequest: CreatePlayerRequest): CommandResponse {
        val createPlayerContext = PlayerContext()
            .setQuery(createPlayerRequest)
        playerRepo.save(createPlayerContext.toPlayerEntity())
        return createPlayerContext.toCommandResponse()
    }

    fun update(updatePlayerRequest: UpdatePlayerRequest): CommandResponse {
        val playerUuid =
            updatePlayerRequest.playerUuid ?: throw RuntimeException("UpdatePlayerRequest empty player uuid")
        val playerForUpdate = playerRepo.findById(playerUuid)
            .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $playerUuid") }
        val updatePlayerContext = PlayerContext()
            .setQuery(updatePlayerRequest)

        playerRepo.save(updatePlayerContext.updatePlayer(playerForUpdate))
        return updatePlayerContext.toCommandResponse()
    }

    fun deleteByUuid(uuid: UUID) {
        playerRepo.deleteById(uuid)
    }
}