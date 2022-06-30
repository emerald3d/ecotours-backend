package space.ecotours.features.booking

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import space.ecotours.database.booking.Booking
import space.ecotours.database.booking.Booking.fetchBooking
import space.ecotours.database.booking.BookingDTO
import space.ecotours.database.booking.mapToBookingResponse
import space.ecotours.database.token.Token
import space.ecotours.database.tour.Region
import space.ecotours.database.tour.Tour
import space.ecotours.database.tour.TourTemplate
import space.ecotours.database.tour.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BookingController(private val call: ApplicationCall) {


    suspend fun bookingTour() {
        val formatter: DateTimeFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val bookingReceiveRemote = call.receive<BookingReceiveRemote>()
        val token = Token.fetchPhoneByToken(bookingReceiveRemote.token)
        val currentTour = Tour.fetchTour(bookingReceiveRemote.tour_id)
        try {
            val date = LocalDateTime.now().format(formatter).toString()

            if (currentTour!!.max_capacity > 0) {
                Booking.insert(BookingDTO(
                    date = date,
                    user_phone = token!!.phone,
                    tour_id = bookingReceiveRemote.tour_id
                ))
                Tour.updateMaxCapacity(bookingReceiveRemote.tour_id)
                call.respond("Бронирование удалось")
            } else {
                call.respond("Мест больше нет")
            }
        } catch (e: Exception) {
            call.respond("Бронирование не удалось>")
        }
    }

    suspend fun bookingList(token: String) {
        try {
            val token = Token.fetchPhoneByToken(token)
            val bookingList = fetchBooking(token!!.phone)
            call.respond(BookingListResponseRemote(
                bookingList = bookingList.map { it.mapToBookingResponse() }
            ))
        } catch (e: Exception) {
            call.respond("Пользователь не найден")
        }

    }
}