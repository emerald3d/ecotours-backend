package space.ecotours.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object User: Table("user") {
    private val phone = User.varchar("phone", 10)
    private val password = User.varchar("password", 45)
    private val name = User.varchar("name", 45)
    private val email = User.varchar("email", 45).nullable()

    fun insert(userDTO: UserDTO) {
        transaction {
            User.insert{
                it[phone] = userDTO.phone
                it[password] = userDTO.password
                it[name] = userDTO.name
                it[email] = userDTO.email ?: ""
            }
        }
    }

    fun fetchUser(phone: String): UserDTO? {
        return try {
            transaction {
                val userModel = User.select { User.phone.eq(phone) }.single()
                UserDTO(
                    phone = userModel[User.phone],
                    password = userModel[password],
                    name = userModel[name],
                    email = userModel[email]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}