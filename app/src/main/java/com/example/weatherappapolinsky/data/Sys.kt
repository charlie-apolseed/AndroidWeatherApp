package com.example.weatherappapolinsky.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    @SerialName("country")
    var country: String? = null,
    @SerialName("id")
    var id: Int? = null,
    @SerialName("sunrise")
    var sunrise: Long? = null,
    @SerialName("sunset")
    var sunset: Long? = null,
    @SerialName("type")
    var type: Int? = null
)