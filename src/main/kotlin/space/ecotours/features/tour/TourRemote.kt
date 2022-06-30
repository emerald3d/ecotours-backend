package space.ecotours.features.tour

import kotlinx.serialization.Serializable

@Serializable
data class TourReceiveRemote(
    val tour_id: Int
)

@Serializable
data class TourResponseRemote(
    val tour_id: Int,
    val name: String,
    val description: String,
    val type_name: String,
    val region_name: String,
    val date_start: String,
    val date_end: String,
    val price: Int,
    val max_capacity: Int,
    val tourphoto_url: String
)

@Serializable
data class TourListResponseRemote(
    val tourList: List<TourResponseRemote>
)

@Serializable
data class TourMiniResponseRemote(
    val date_start: String,
    val date_end: String,
    val price: Int,
    val max_capacity: Int
)

@Serializable
data class TourMiniListResponseRemote(
    val tourList: List<TourMiniResponseRemote>
)

@Serializable
data class TourPhotoResponseRemote(
    val url: String
)

@Serializable
data class TourPhotoListResponseRemote(
    val tourPhotoList: List<TourPhotoResponseRemote>
)