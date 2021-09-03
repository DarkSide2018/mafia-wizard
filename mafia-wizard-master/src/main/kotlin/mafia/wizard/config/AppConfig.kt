package mafia.wizard.config

import mafia.wizard.entities.Authority
import mafia.wizard.entities.Player
import mafia.wizard.entities.User
import mafia.wizard.repository.PlayerRepo
import mafia.wizard.repository.UserDetailsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import javax.annotation.PostConstruct

@Configuration
 class AppConfig {
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    lateinit var userDetailsRepository: UserDetailsRepo
    @Autowired
    lateinit var playerRepo: PlayerRepo

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
        user.setAuthorities(authorityList)
        userDetailsRepository.save(user)

        val player = Player()
        player.nickName = "player1"
        val player2 = Player()
        player2.nickName = "player2"
        playerRepo.save(player)
        playerRepo.save(player2)
    }


     fun createAuthority(roleCode: String, roleDescription: String): Authority {
        val authority = Authority()
        authority.roleCode = (roleCode)
        authority.roleDescription = (roleDescription)
        return authority
    }
}