package mafia.wizard.services

import exceptions.FieldWasNullException
import mafia.wizard.mappers.player.*
import mafia.wizard.openapi.models.*
import mafia.wizard.repository.PlayerRepo
import mappers.*
import models.PlayerContext
import org.imgscalr.Scalr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO


const val FREE_STATUS = "FREE"

@Service
class PlayerService {
    @Autowired
    private lateinit var playerRepo: PlayerRepo

    fun getAll(readAllPlayersRequest: ReadAllPlayersRequest): ReadAllPlayersResponse {
        val context = PlayerContext().setQuery(readAllPlayersRequest)
        val all = playerRepo.findAllByStatus(context.createPageRequest(), FREE_STATUS)
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
        val players = playerRepo.findByNickName(context.createPageRequest(), "%${context.search}%")
            ?: throw RuntimeException("no such players")
        return context
            .setPlayers(players)
            .toReadAllPlayersResponse()
    }

    fun save(createPlayerRequest: CreatePlayerRequest): BaseResponse {
        val createPlayerContext = PlayerContext()
            .setCommand(createPlayerRequest)
        playerRepo.save(createPlayerContext.toPlayerEntity())
        return createPlayerContext.toCommandResponse()
    }

    fun update(updatePlayerRequest: UpdatePlayerRequest): BaseResponse {
        val playerUuid =
            updatePlayerRequest.playerUuid ?: throw RuntimeException("UpdatePlayerRequest empty player uuid")
        val playerForUpdate = playerRepo.findById(playerUuid)
            .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $playerUuid") }
        val updatePlayerContext = PlayerContext()
            .setCommand(updatePlayerRequest)

        playerRepo.save(updatePlayerContext.updatePlayer(playerForUpdate))
        return updatePlayerContext.toCommandResponse()
    }

    fun updateArray(updatePlayerRequest: UpdatePlayerArrayRequest): BaseResponse {
        val updatePlayerContext = PlayerContext()
        updatePlayerRequest.players?.forEach {
            val playerUuid = it.playerUuid
            val playerForUpdate = playerRepo.findById(playerUuid ?: throw FieldWasNullException("playerUUID"))
                .orElseThrow { return@orElseThrow RuntimeException("no such element with uuid : $playerUuid") }
            updatePlayerContext.setCommand(it)
            playerRepo.save(updatePlayerContext.updatePlayer(playerForUpdate))
        }
        return updatePlayerContext.toCommandResponse()
    }

    fun saveImage(uuid: UUID, multipartFile: MultipartFile): BaseResponse {
        val player = playerRepo.getById(uuid)
        val bais: ByteArrayInputStream = ByteArrayInputStream(multipartFile.bytes);
        val bufferedImage = resizeImage(ImageIO.read(bais), 200)
        player.image = toByteArray(bufferedImage)
        playerRepo.save(player)
        return PlayerContext().toCommandResponse()
    }

    fun toByteArray(bi: BufferedImage): ByteArray? {
        val baos = ByteArrayOutputStream()
        ImageIO.write(bi, "jpeg", baos)
        return baos.toByteArray()
    }

    fun resizeImage(originalImage: BufferedImage, targetWidth: Int): BufferedImage {
        return Scalr.resize(originalImage, targetWidth)
    }


    fun deleteByUuid(uuid: UUID) {
        playerRepo.deleteById(uuid)
    }
}