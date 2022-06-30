package space.ecotours.features.booking

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureBookingRouting() {

    routing {
        post("/tour/booking") {
            val bookingController = BookingController(call)
            bookingController.bookingTour()
        }
        get("/tour/booking/{token}") {
            val bookingController = BookingController(call)
            val token = call.parameters["token"]?.toString() ?: ""
            bookingController.bookingList(token)
        }
    }
}