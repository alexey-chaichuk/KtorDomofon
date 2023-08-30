package ru.cpro.ktordomofon.data.source.remote.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class DoorsDto (
    val success: Boolean,
    @SerialName("data")
    val doors: List<DoorDto>
)

@Keep
@Serializable
data class DoorDto (
    val name: String,
    val room: String? = null,
    val id: Int,
    val favorites: Boolean,
    val snapshot: String? = null
)