package space.ecotours.database.tour

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Tour: Table() {
    private val id = Tour.integer("id")
    private val date_start = Tour.varchar("date_start", 45)
    private val date_end = Tour.varchar("date_end", 45)
    private val price = Tour.integer("price")
    private val max_capacity = Tour.integer("max_capacity")
    private val tourtemplate_id = Tour.integer("tourtemplate_id")

    fun insert(tourDTO: TourDTO) {
        transaction {
            Tour.insert{
                it[id] = tourDTO.rowId
                it[date_start] = tourDTO.date_start
                it[date_end] = tourDTO.date_end
                it[price] = tourDTO.price
                it[max_capacity] = tourDTO.max_capacity
                it[tourtemplate_id] = tourDTO.tourtemplate_id
            }
        }
    }

    fun fetchTourList(): List<TourDTO> {
        return try {
            transaction {
                Tour.selectAll().toList()
                    .map {
                        TourDTO(
                            rowId = it[Tour.id],
                            date_start = it[Tour.date_start],
                            date_end = it[Tour.date_end],
                            price = it[Tour.price],
                            max_capacity = it[Tour.max_capacity],
                            tourtemplate_id = it[Tour.tourtemplate_id]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun fetchTour(tour_id: Int): TourDTO? {
        return try {
            transaction {
                val tourModel = Tour.select { Tour.id.eq(tour_id) }.single()
                TourDTO(
                    rowId = tourModel[Tour.id],
                    date_start = tourModel[Tour.date_start],
                    date_end = tourModel[Tour.date_end],
                    price = tourModel[Tour.price],
                    max_capacity = tourModel[Tour.max_capacity],
                    tourtemplate_id = tourModel[Tour.tourtemplate_id]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun updateMaxCapacity (tour_id: Int) {
        val tourDTO = fetchTour(tour_id)
        try {
            transaction {
                Tour.update ( { Tour.id.eq(tour_id) } ) {
                    it[max_capacity] = tourDTO!!.max_capacity - 1
                }
            }
        } catch (e: Exception) {

        }
    }

}