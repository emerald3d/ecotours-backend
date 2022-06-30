package space.ecotours.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val login: String
)

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond("Hello Emerald")
        }

        @Serializable
        data class IdDTO(
            val rowId: Int,
            val name: String?
        )
        get("/{id}/{name}") {
            val id = call.parameters["id"]?.toInt() ?: -1
            val name = call.parameters["name"]?.toString() ?: ""
            call.respond(
                IdDTO(
                    rowId = id,
                    name = name
                )
            )
        }
    }
}
