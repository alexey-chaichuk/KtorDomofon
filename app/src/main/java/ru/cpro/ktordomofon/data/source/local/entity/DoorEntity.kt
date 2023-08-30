package ru.cpro.ktordomofon.data.source.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class DoorEntity() : RealmObject {
    var name: String = ""
    var room: String? = null
    @PrimaryKey
    var id: Int = 0
    var favorites: Boolean = false
    var snapshot: String? = null

    constructor(
        name: String,
        room: String?,
        id: Int,
        favorites: Boolean,
        snapshot: String?
    ) : this() {
        this.name = name
        this.room = room
        this.id = id
        this.favorites = favorites
        this.snapshot = snapshot
    }
}