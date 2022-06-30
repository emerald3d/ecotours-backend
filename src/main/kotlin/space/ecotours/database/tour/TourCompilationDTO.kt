package space.ecotours.database.tour

 class TourCompilationDTO(
     val date_start: String,
     val date_end: String,
     val price: Int,
     val max_capacity: Int,
     val tour_slug: String,
     val tourname: String,
     val description: String,
     val type: String,
     val region: String )