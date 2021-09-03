package mafia.wizard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
 class AppGeneral
fun main(args: Array<String>) {
    runApplication<AppGeneral>(*args)
}
