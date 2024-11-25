package com.example.weatherappapolinsky.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    @SerialName("deg")
    var deg: Double? = null,
    @SerialName("speed")
    var speed: Double? = null
)