package space.ecotours.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val phone: String,
    val password: String,
    val name: String,
    val email: String
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)