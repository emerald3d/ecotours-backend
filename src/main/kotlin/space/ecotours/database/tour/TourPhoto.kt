package space.ecotours.database.tour

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import space.ecotours.database.token.Token
import space.ecotours.database.token.TokenDTO

object TourPhoto: Table() {
    private val id = TourPhoto.integer("id")
    private val url = TourPhoto.varchar("url", 2040)
    private val tour_id = TourPhoto.integer("tour_id")

    fun insert(tourPhotoDTO: TourPhotoDTO) {
        transaction {
            TourPhoto.insert{
                it[id] = tourPhotoDTO.rowId
                it[url] = tourPhotoDTO.url
                it[tour_id] = tourPhotoDTO.tour_id

            }
        }
    }

    fun fetchTourPhotoList(tourtemplate_id: Int): List<TourPhotoUrlDTO> {
        return try {
            transaction {
                TourPhoto.select { TourPhoto.tour_id.eq(tourtemplate_id) }.toList()
                    .map {
                        TourPhotoUrlDTO(
                            url = it[TourPhoto.url]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    fun fetchTourPhoto(tourtemplate_id: Int): TourPhotoUrlDTO? {
        return try {
            transaction {
                val tourModel = TourPhoto.select { TourPhoto.tour_id.eq(tourtemplate_id) }.limit(1).firstOrNull()
                TourPhotoUrlDTO(
                    url = tourModel!![TourPhoto.url]
                )
            }
        } catch (e: Exception) {
            TourPhotoUrlDTO(
                url = e.toString()
            )
        }
    }
}