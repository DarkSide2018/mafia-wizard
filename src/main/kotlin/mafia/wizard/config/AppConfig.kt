package mafia.wizard.config

import mafia.wizard.entities.Authority
import mafia.wizard.entities.User
import mafia.wizard.repository.UserDetailsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import javax.annotation.PostConstruct

@Configuration
open class AppConfig {
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userDetailsRepository: UserDetailsRepo

    @PostConstruct
    protected open fun init() {
        val authorityList: MutableList<Authority> = ArrayList<Authority>()
        authorityList.add(createAuthority("USER", "User role"))
        val user = User()
        user.userName = ("pardeep161")
        user.firstName = ("Pardeep")
        user.lastName = ("K")
        user.password = passwordEncoder.encode("pardeep@123")
        user.enabled = (true)
        user.setAuthorities(authorityList)
        userDetailsRepository.save(user)
    }


    open fun createAuthority(roleCode: String, roleDescription: String): Authority {
        val authority = Authority()
        authority.roleCode = (roleCode)
        authority.roleDescription = (roleDescription)
        return authority
    }
}