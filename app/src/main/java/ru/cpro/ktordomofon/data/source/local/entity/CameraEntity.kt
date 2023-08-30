package ru.cpro.ktordomofon.data.source.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CameraEntity() : RealmObject {
    var name: String = ""
    var snapshot: String = ""
    var room: String? = null
    @PrimaryKey
    var id: Int = 0
    var favorites: Boolean = false
    var rec: Boolean = false

    constructor(
        name: String,
        snapshot: String,
        room: String?,
        id: Int,
        favorites: Boolean,
        rec: Boolean
    ) : this() {
        this.name = name
        this.snapshot = snapshot
        this.room = room
        this.id = id
        this.favorites = favorites
        this.rec = rec
    }
}