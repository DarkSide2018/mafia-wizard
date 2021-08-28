package mafia.wizard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class AppGeneral{

}

fun main(args: Array<String>) {
    runApplication<AppGeneral>(*args)
}