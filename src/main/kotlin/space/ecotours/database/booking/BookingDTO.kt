package space.ecotours.database.booking

import space.ecotours.features.booking.BookingListResponseRemote
import space.ecotours.features.booking.BookingResponseRemote

class BookingDTO(
     val date: String,
     val user_phone: String,
     val tour_id: Int
 )

fun BookingDTO.mapToBookingResponse() : BookingResponseRemote = BookingResponseRemote(
    date = date,
    user_phone = user_phone,
    tour_id = tour_id
)