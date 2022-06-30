package space.ecotours.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import space.ecotours.database.token.Token
import space.ecotours.database.token.TokenDTO
import space.ecotours.database.users.User
import space.ecotours.database.users.UserDTO
import java.lang.Double.parseDouble
import java.util.*

class RegisterController (private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        //val receive = call.receive<RegisterReceiveRemote>()
        val userDTO = User.fetchUser(registerReceiveRemote.phone)

        var phoneValid = true
        try {
            val num = parseDouble(registerReceiveRemote.phone)
            if(registerReceiveRemote.phone.length != 10) {
                phoneValid = false
            }
        } catch (e: NumberFormatException) {
            phoneValid = false
        }
        if (phoneValid) {
            if(userDTO != null) {
                call.respond(HttpStatusCode.Conflict, "Номер телефона уже существует в системе")
            } else {

                val token = UUID.randomUUID().toString()

                try {
                    User.insert(
                        UserDTO(
                            phone = registerReceiveRemote.phone,
                            password = registerReceiveRemote.password,
                            name = registerReceiveRemote.name,
                            email = registerReceiveRemote.email
                        )
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.Conflict, "Пользователь существует")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Не удалось создать пользователя")
                }

                Token.insert(
                    TokenDTO(
                        phone = registerReceiveRemote.phone,
                        token = token
                    )
                )

                call.respond(RegisterResponseRemote(token = token))
            }
        } else {
            call.respond(HttpStatusCode.Conflict, "Введите корректный номер телефона")
        }
    }
}