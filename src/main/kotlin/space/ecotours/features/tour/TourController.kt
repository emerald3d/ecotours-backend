package space.ecotours.features.tour

import io.ktor.server.application.*
import io.ktor.server.response.*
import space.ecotours.database.tour.*

class TourController(private val call: ApplicationCall) {

    suspend fun addTour() {
    }

    suspend fun getTourView(id: Int) {
        try {
            val tourDTO = Tour.fetchTour(id)
            val tourTemplateDTO = TourTemplate.fetchTourTemplate(tourDTO!!.tourtemplate_id)
            val typeDTO = Type.fetchType(tourTemplateDTO!!.type_id)
            val regionDTO = Region.fetchRegion(tourTemplateDTO!!.region_id)
            val tourPhotoUrlDTO = TourPhoto.fetchTourPhoto(tourDTO!!.tourtemplate_id)!!

            if (tourDTO != null) {
                            val price = tourDTO.price.toString()
                            call.respond(
                                TourResponseRemote(
                                    tour_id = tourDTO.rowId,
                                    name = tourTemplateDTO.name,
                                    description = tourTemplateDTO.description,
                                    type_name = typeDTO!!.name,
                                    region_name = regionDTO!!.name,
                                    date_start = tourDTO.date_start,
                                    date_end = tourDTO.date_end,
                                    price = tourDTO.price,
                                    max_capacity = tourDTO.max_capacity,
                                    tourphoto_url = tourPhotoUrlDTO.url
                                )
                            )
            } else {
                call.respond("Тур не найден")
            }
        } catch (e: Exception) {
            call.respond("Тур не найден")
        }
    }

    suspend fun getTourList() {
        try {
            val listTour = Tour.fetchTourList()
            call.respond(TourListResponseRemote(
                tourList = listTour.map { TourResponseRemote(
                    tour_id = it.rowId,
                    name = TourTemplate.fetchTourTemplate(it.tourtemplate_id)!!.name,
                    description = TourTemplate.fetchTourTemplate(it.tourtemplate_id)!!.description,
                    type_name = Type.fetchType(TourTemplate.fetchTourTemplate(it.tourtemplate_id)!!.type_id)!!.name,
                    region_name = Region.fetchRegion(TourTemplate.fetchTourTemplate(it.tourtemplate_id)!!.region_id)!!.name,
                    date_start = it.date_start,
                    date_end = it.date_end,
                    price = it.price,
                    max_capacity = it.max_capacity,
                    tourphoto_url = TourPhoto.fetchTourPhoto(it.tourtemplate_id)!!.url
                ) }
            ))
        } catch (e: Exception) {
            call.respond("Ошибка")
        }
    }

    suspend fun getTourPhotoList(tourTemplate_id: Int) {
        try {
            val listTourPhoto = TourPhoto.fetchTourPhotoList(tourTemplate_id)
            call.respond(TourPhotoListResponseRemote(
                tourPhotoList = listTourPhoto.map { it.mapToTourPhotoResponse() }
            ))
        } catch (e: Exception) {
            call.respond("Ошибка")
        }
    }
}