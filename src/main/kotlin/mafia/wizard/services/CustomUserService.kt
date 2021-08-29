package mafia.wizard.services

import mafia.wizard.repository.UserDetailsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserService: UserDetailsService{
    @Autowired
    private var userDetailsRepository: UserDetailsRepo? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        // TODO Auto-generated method stub
        return userDetailsRepository!!.findByUserName(username)
            ?: throw UsernameNotFoundException("User Not Found with userName $username")
    }
}