package mafia.wizard.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import mafia.wizard.entities.*
import mafia.wizard.repository.GameMasterRepository
import mafia.wizard.repository.GameRepository
import mafia.wizard.repository.PlayerRepo
import mafia.wizard.repository.UserDetailsRepo
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import javax.annotation.PostConstruct
import javax.persistence.PersistenceContext


@Configuration
class AppConfig(
    private var passwordEncoder: PasswordEncoder,
    private var userDetailsRepository: UserDetailsRepo,
    private var playerRepo: PlayerRepo,
    private val gameMasterRepository: GameMasterRepository,
    private val gameRepository: GameRepository
) {
    @PostConstruct
    protected fun init() {
        val authorityList: MutableList<Authority> = ArrayList<Authority>()
        val authority = createAuthority("USER", "User role")
        authorityList.add(authority)
        val user = User()
        user.userName = ("admin")
        user.firstName = ("game")
        user.lastName = ("manager")
        user.password = passwordEncoder.encode("admin@123")
        user.enabled = (true)
        user.authorities = authorityList
        userDetailsRepository.save(user)

        val player = Player()
        player.nickName = "player1"
        val player2 = Player()
        player2.nickName = "player2"
        playerRepo.save(player)
        playerRepo.save(player2)

        val gameMaster = GameMaster()
        gameMaster.nickName = "nick"
        val game = Game()
        game.gameNumber = 2
        gameMaster.games = mutableListOf(game)

        gameMasterRepository.save(gameMaster)



    }


    fun createAuthority(roleCode: String, roleDescription: String): Authority {
        val authority = Authority()
        authority.roleCode = (roleCode)
        authority.roleDescription = (roleDescription)
        return authority
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

}