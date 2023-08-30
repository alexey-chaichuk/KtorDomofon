package ru.cpro.ktordomofon.domain.model

data class Camera(
    val name: String,
    val snapshot: String,
    val room: String? = null,
    val id: Int,
    val favorites: Boolean,
    val rec: Boolean
)