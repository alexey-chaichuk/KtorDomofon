package ru.cpro.ktordomofon.data.source.remote.dto

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CamerasDto(
    val success: Boolean,
    val data: CamerasDataDto
)

@Keep
@Serializable
data class CamerasDataDto(
    val room: List<String>,
    val cameras: List<CameraDto>
)

@Keep
@Serializable
data class CameraDto(
    val name: String,
    val snapshot: String,
    val room: String? = null,
    val id: Int,
    val favorites: Boolean,
    val rec: Boolean
)