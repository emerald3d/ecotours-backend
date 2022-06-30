package space.ecotours.features.tour

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureTourRouting() {

    routing {

        get("/tour/list") {
            val tourController = TourController(call)
            tourController.getTourList()
        }

        get("/tour/view/{id}") {
            val id = call.parameters["id"]?.toInt() ?: -1
            val tourController = TourController(call)
            tourController.getTourView(id)
        }

        get("/tour/photo/{tour_id}") {
            val id = call.parameters["tour_id"]?.toInt() ?: -1
            val tourController = TourController(call)
            tourController.getTourPhotoList(id)
        }

        post("/tour/add") {
            val tourController = TourController(call)
            tourController.addTour()
        }
    }
}