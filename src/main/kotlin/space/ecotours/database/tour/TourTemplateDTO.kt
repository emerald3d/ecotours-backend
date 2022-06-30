package space.ecotours.database.tour

 class TourTemplateDTO(
     val rowId: Int,
     val name: String,
     val slug: String,
     val description: String,
     val type_id: Int,
     val region_id: Int
 )