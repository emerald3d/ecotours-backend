package space.ecotours.database.tour

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import space.ecotours.database.token.Token
import space.ecotours.database.token.TokenDTO

object Region: Table() {
    private val id = Region.integer("id")
    private val name = Region.varchar("name", 45)

    fun insert(regionDTO: RegionDTO) {
        transaction {
            Region.insert{
                it[id] = regionDTO.rowId
                it[name] = regionDTO.name

            }
        }
    }

    fun fetchRegion(region_id: Int): RegionDTO? {
        return try {
            transaction {
                val regionModel = Region.select { Region.id.eq(region_id) }.single()
                RegionDTO(
                    rowId = regionModel[Region.id],
                    name = regionModel[Region.name],
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}