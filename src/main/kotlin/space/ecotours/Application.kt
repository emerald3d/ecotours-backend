package space.ecotours

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import space.ecotours.features.booking.configureBookingRouting
import space.ecotours.features.login.configureLoginRouting
import space.ecotours.features.register.configureRegisterRouting
import space.ecotours.features.tour.configureTourRouting
import space.ecotours.plugins.*

fun main() {
    Database.connect("jdbc:mysql://localhost:3306/ecotours22?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", driver = "com.mysql.cj.jdbc.Driver",
        user = "root", password = "")


    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureLoginRouting()
        configureRegisterRouting()
        configureTourRouting()
        configureBookingRouting()
    }.start(wait = true)
}
