package space.ecotours.features.booking

import kotlinx.serialization.Serializable

@Serializable
data class BookingReceiveRemote(
    val token: String,
    val tour_id: Int
)

@Serializable
data class BookingResponseRemote(
    val date: String,
    val user_phone: String,
    val tour_id: Int
)

@Serializable
data class FetchBookingResponseRemote(
    val date: String,
    val user_phone: String,
    val tour_id: Int
)

@Serializable
data class BookingListResponseRemote(
    val bookingList: List<BookingResponseRemote>
)