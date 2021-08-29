package mafia.wizard.entities

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Table(name = "AUTH_AUTHORITY")
@Entity
data class Authority(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     var id: Long? = null,
    @javax.persistence.Column(name = "ROLE_CODE")
    var roleCode: String? = null,

    @Column(name = "ROLE_DESCRIPTION")
     var roleDescription: String? = null
) : GrantedAuthority {
    override fun getAuthority(): String? {

        return roleCode
    }
}