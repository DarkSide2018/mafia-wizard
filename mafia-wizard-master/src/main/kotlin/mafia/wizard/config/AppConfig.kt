package mafia.wizard.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import mafia.wizard.entities.Game
import mafia.wizard.entities.GameMaster
import mafia.wizard.entities.Player
import mafia.wizard.repository.GameMasterRepository
import mafia.wizard.repository.PlayerRepo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct


@Configuration
class AppConfig(
    private val gameMasterRepository: GameMasterRepository
) {
    @PostConstruct
    protected fun init() {

        val player = Player()
        player.nickName = "player1"
        val player2 = Player()
        player2.nickName = "player2"

        val gameMaster = GameMaster()
        gameMaster.nickName = "nick"
        val game = Game()
        game.gameNumber = 2
        gameMaster.games = mutableListOf(game)
        gameMaster.addPlayer(player)
        gameMaster.addPlayer(player2)
        gameMasterRepository.save(gameMaster)


    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .registerModule(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

}