package space.ecotours.database.tour

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import space.ecotours.database.token.Token
import space.ecotours.database.token.TokenDTO

object Type: Table() {
    private val id = Type.integer("id")
    private val name = Type.varchar("name", 45)

    fun insert(typeDTO: TypeDTO) {
        transaction {
            Type.insert{
                it[id] = typeDTO.rowId
                it[name] = typeDTO.name

            }
        }
    }

    fun fetchType(type_id: Int): TypeDTO? {
        return try {
            transaction {
                val typeModel = Type.select { Type.id.eq(type_id) }.single()
                TypeDTO(
                    rowId = typeModel[Type.id],
                    name = typeModel[Type.name],
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}