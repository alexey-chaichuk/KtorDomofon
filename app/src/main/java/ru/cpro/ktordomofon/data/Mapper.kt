package ru.cpro.ktordomofon.data

import ru.cpro.ktordomofon.data.source.local.entity.CameraEntity
import ru.cpro.ktordomofon.data.source.local.entity.DoorEntity
import ru.cpro.ktordomofon.data.source.remote.dto.CameraDto
import ru.cpro.ktordomofon.data.source.remote.dto.DoorDto
import ru.cpro.ktordomofon.domain.model.Camera
import ru.cpro.ktordomofon.domain.model.Door

class Mapper {
    fun mapCameraEntityToDto(s: CameraEntity) = CameraDto(
        name = s.name,
        snapshot = s.snapshot,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        rec = s.rec
    )

    fun mapCameraDtoToEntity(s: CameraDto) = CameraEntity(
        name = s.name,
        snapshot = s.snapshot,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        rec = s.rec
    )

    fun mapCameraDtoToDomain(s: CameraDto) = Camera(
        name = s.name,
        snapshot = s.snapshot,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        rec = s.rec
    )

    fun mapCameraEntityToDomain(s: CameraEntity) = Camera(
        name = s.name,
        snapshot = s.snapshot,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        rec = s.rec
    )

    fun mapCameraDomainToDto(s: Camera) = CameraDto(
        name = s.name,
        snapshot = s.snapshot,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        rec = s.rec
    )

    fun mapCameraDomainToEntity(s: Camera) = CameraEntity(
        name = s.name,
        snapshot = s.snapshot,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        rec = s.rec
    )


    fun mapDoorDtoToEntity(s: DoorDto) = DoorEntity(
        name = s.name,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        snapshot = s.snapshot
    )

    fun mapDoorEntityToDto(s: DoorEntity) = DoorDto(
        name = s.name,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        snapshot = s.snapshot
    )

    fun mapDoorDtoToDomain(s: DoorDto) = Door(
        name = s.name,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        snapshot = s.snapshot
    )

    fun mapDoorDomainToDto(s: Door) = DoorDto(
        name = s.name,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        snapshot = s.snapshot
    )

    fun mapDoorEntityToDomain(s: DoorEntity) = Door(
        name = s.name,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        snapshot = s.snapshot
    )

    fun mapDoorDomainToEntity(s: Door) = DoorEntity(
        name = s.name,
        room = s.room,
        id = s.id,
        favorites = s.favorites,
        snapshot = s.snapshot
    )
}