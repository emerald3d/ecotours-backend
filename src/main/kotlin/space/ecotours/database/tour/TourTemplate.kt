package space.ecotours.database.tour

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import space.ecotours.database.token.Token
import space.ecotours.database.token.TokenDTO

object TourTemplate: Table() {
    private val id = TourTemplate.integer("id")
    private val name = TourTemplate.varchar("name", 45)
    private val slug = TourTemplate.varchar("slug", 45)
    private val description = TourTemplate.varchar("description", 2040)
    private val type_id = TourTemplate.integer("type_id")
    private val region_id = TourTemplate.integer("region_id")

    fun insert(tourTemplateDTO: TourTemplateDTO) {
        transaction {
            TourTemplate.insert{
                it[id] = tourTemplateDTO.rowId
                it[name] = tourTemplateDTO.name
                it[slug] = tourTemplateDTO.slug
                it[description] = tourTemplateDTO.description
                it[type_id] = tourTemplateDTO.type_id
                it[region_id] = tourTemplateDTO.region_id

            }
        }
    }

    fun fetchTourTemplate(tourtemplate_id: Int): TourTemplateDTO? {
        return try {
            transaction {
                val tourTemplateModel = TourTemplate.select { TourTemplate.id.eq(tourtemplate_id) }.single()
                TourTemplateDTO(
                    rowId = tourTemplateModel[TourTemplate.id],
                    name = tourTemplateModel[TourTemplate.name],
                    slug = tourTemplateModel[TourTemplate.slug],
                    description = tourTemplateModel[TourTemplate.description],
                    type_id = tourTemplateModel[TourTemplate.type_id],
                    region_id = tourTemplateModel[TourTemplate.region_id]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}