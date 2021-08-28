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
        user.setUserName("pardeep161")
        user.setFirstName("Pardeep")
        user.setLastName("K")
        user.setPassword(passwordEncoder!!.encode("pardeep@123"))
        user.setEnabled(true)
        user.setAuthorities(authorityList)
        userDetailsRepository.save(user)
    }


    open fun createAuthority(roleCode: String, roleDescription: String): Authority {
        val authority = Authority()
        authority.setRoleCode(roleCode)
        authority.setRoleDescription(roleDescription)
        return authority
    }
}