package mafia.wizard.entities

import org.springframework.security.core.userdetails.UserDetails
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Table(name = "AUTH_USER_DETAILS")
@Entity
data class User(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "USER_NAME", unique = true)
    var userName: String? = null,

    @Column(name = "USER_KEY")
    private var password: String? = null,

    @Column(name = "CREATED_ON")
    var createdAt: OffsetDateTime? = null,

    @Column(name = "UPDATED_ON")
    var updatedAt: OffsetDateTime? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "enabled")
    var enabled: Boolean = true,


    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "AUTH_USER_AUTHORITY",
        joinColumns = [JoinColumn(referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(referencedColumnName = "id")]
    )
    private var authorities: List<Authority> = mutableListOf()
) : UserDetails {
    override fun getAuthorities(): List<Authority>? {
        return authorities
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {

        return userName!!
    }

    override fun isAccountNonExpired(): Boolean {

        return enabled
    }

    override fun isAccountNonLocked(): Boolean {
        return enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return enabled
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    fun setPassword(value: String) {
        password = value
    }

    fun setAuthorities(value: List<Authority>) {
        authorities = value
    }
}