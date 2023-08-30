package ru.cpro.ktordomofon.domain.model

data class Door (
    val name: String,
    val room: String? = null,
    val id: Int,
    val favorites: Boolean,
    val snapshot: String? = null
)