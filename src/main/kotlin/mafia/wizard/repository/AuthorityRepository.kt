package mafia.wizard.repository

import mafia.wizard.entities.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthorityRepository: JpaRepository<Authority, UUID> {
}