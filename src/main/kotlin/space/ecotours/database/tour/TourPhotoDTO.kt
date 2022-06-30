package space.ecotours.database.tour

import space.ecotours.features.tour.TourPhotoResponseRemote

class TourPhotoDTO(
     val rowId: Int,
     val url: String,
     val tour_id: Int
 )

class TourPhotoUrlDTO(
    val url: String
)

fun TourPhotoUrlDTO.mapToTourPhotoResponse() : TourPhotoResponseRemote = TourPhotoResponseRemote(
    url = url
)