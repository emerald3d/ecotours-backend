package space.ecotours.features.tour.models

import kotlinx.serialization.Serializable

@Serializable
class FetchTourRequest(
     val searchQuery:String
 )