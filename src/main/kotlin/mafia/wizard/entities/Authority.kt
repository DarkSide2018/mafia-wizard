package mafia.wizard.entities

import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.persistence.*

@Table(name = "AUTH_AUTHORITY")
@Entity
data class Authority(
    @Id
    @Column(name = "auth_id", columnDefinition = "BINARY(16)")
    @GeneratedValue
    var authUuid: UUID = UUID.randomUUID(),
    @javax.persistence.Column(name = "ROLE_CODE")
    var roleCode: String? = null,

    @Column(name = "ROLE_DESCRIPTION")
    var roleDescription: String? = null,

    @ManyToMany(mappedBy = "authorities")
    private var users: List<User> = mutableListOf()

) : GrantedAuthority {
    override fun getAuthority(): String? {

        return roleCode
    }
}