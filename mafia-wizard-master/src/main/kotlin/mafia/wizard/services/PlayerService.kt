package mafia.wizard.services

import mafia.wizard.entities.Player
import mafia.wizard.mappers.player.*
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.PlayerRepo
import mappers.*
import models.PlayerContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlayerService {
    @Autowired
    private lateinit var playerRepo: PlayerRepo

    fun getAll(readAllPlayersRequest: ReadAllPlayersRequest): ReadAllPlayersResponse {
        val context = PlayerContext().setQuery(readAllPlayersRequest)
        val all = playerRepo.findAll(context.createPageRequest())
        return context
            .setPlayers(all)
            .toReadAllPlayersResponse()
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

    fun getByNickNameLike(searchPlayerRequest: SearchPlayerRequest): ReadAllPlayersResponse {
        val context = PlayerContext().setQuery(searchPlayerRequest)
        val players = playerRepo.findByNickName(context.createPageRequest(),"%${context.search}%") ?: throw RuntimeException("no such players")
        return context
            .setPlayers(players)
            .toReadAllPlayersResponse()
    }

    fun save(createPlayerRequest: CreatePlayerRequest): CommandResponse {
        val createPlayerContext = PlayerContext()
            .setCommand(createPlayerRequest)
        playerRepo.save(createPlayerContext.toPlayerEntity())
        return createPlayerContext.toCommandResponse()
    }

    fun update(updatePlayerRequest: UpdatePlayerRequest): CommandResponse {
        val playerUuid =
            updatePlayerRequest.playerUuid ?: throw RuntimeException("UpdatePlayerRequest empty player uuid")
        val playerForUpdate = playerRepo.findById(playerUuid)
            .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $playerUuid") }
        val updatePlayerContext = PlayerContext()
            .setCommand(updatePlayerRequest)

        playerRepo.save(updatePlayerContext.updatePlayer(playerForUpdate))
        return updatePlayerContext.toCommandResponse()
    }

    fun deleteByUuid(uuid: UUID) {
        playerRepo.deleteById(uuid)
    }
}