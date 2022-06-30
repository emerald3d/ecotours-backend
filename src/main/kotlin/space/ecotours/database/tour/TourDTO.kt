package space.ecotours.database.tour

import space.ecotours.database.booking.BookingDTO
import space.ecotours.features.booking.BookingResponseRemote
import space.ecotours.features.tour.TourMiniResponseRemote
import space.ecotours.features.tour.TourResponseRemote

class TourDTO(
     val rowId: Int,
     val date_start: String,
     val date_end: String,
     val price: Int,
     val max_capacity: Int,
     val tourtemplate_id: Int
 )


fun TourDTO.mapToTourMiniResponse() : TourMiniResponseRemote = TourMiniResponseRemote(
    date_start = date_start,
    date_end = date_end,
    price = price,
    max_capacity = max_capacity
)