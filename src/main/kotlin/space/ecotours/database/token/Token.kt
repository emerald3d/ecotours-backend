package space.ecotours.database.token

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import space.ecotours.database.users.User

object Token: Table("token") {
    private val id = Token.integer("id")
    private val phone = Token.varchar("user_phone", 10)
    private val token = Token.varchar("token", 45)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Token.insert{
                it[phone] = tokenDTO.phone
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchToken(phone: String): List<TokenDTO> {
        return try {
            transaction {
                Token.selectAll().toList()
                    .map {
                        TokenDTO(
                            phone = it[Token.phone],
                            token = it[Token.token]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun fetchPhoneByToken(token: String): TokenDTO? {
        return try {
            transaction {
                val tokenModel = Token.select { Token.token.eq(token) }.single()
                TokenDTO(
                            phone = tokenModel[Token.phone],
                            token = tokenModel[Token.token]
                        )
            }
        } catch (e: Exception) {
            null
        }
    }
}