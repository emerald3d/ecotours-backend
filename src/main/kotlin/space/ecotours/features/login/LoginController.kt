package space.ecotours.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import space.ecotours.database.token.Token
import space.ecotours.database.token.TokenDTO
import space.ecotours.database.users.User
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = User.fetchUser(receive.phone)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "Неверный телефон или пароль")
        } else {
            if (userDTO.password == receive.password) {
                val token = UUID.randomUUID().toString()
                Token.insert(
                    TokenDTO(
                        phone = receive.phone,
                        token = token
                    )
                )

                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Неверный телефон или пароль")
            }
        }
    }
}