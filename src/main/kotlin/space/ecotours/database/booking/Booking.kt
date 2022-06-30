package space.ecotours.database.booking

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import space.ecotours.database.token.Token
import space.ecotours.database.token.TokenDTO
import space.ecotours.database.users.User

object Booking: Table() {
    private val date = Booking.varchar("date", 45)
    private val user_phone = Booking.varchar("user_phone", 10)
    private val tour_id = Booking.integer("tour_id")

    fun insert(bookingDTO: BookingDTO) {
        transaction {
            Booking.insert{
                it[date] = bookingDTO.date
                it[user_phone] = bookingDTO.user_phone
                it[tour_id] = bookingDTO.tour_id

            }
        }
    }

    fun fetchBooking(phone: String): List<BookingDTO> {
        return try {
            transaction {
                Booking.select{ Booking.user_phone.eq(phone) }.toList()
                    .map {
                        BookingDTO(
                            date = it[Booking.date],
                            user_phone = it[Booking.user_phone],
                            tour_id = it[Booking.tour_id]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}