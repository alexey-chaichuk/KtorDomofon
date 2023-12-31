package ru.cpro.ktordomofon.data.source.local

import kotlinx.coroutines.flow.Flow
import ru.cpro.ktordomofon.data.source.local.entity.CameraEntity
import ru.cpro.ktordomofon.data.source.local.entity.DoorEntity

interface DbCache {
    suspend fun getCameras(): List<CameraEntity>
    suspend fun saveCameras(cameras: List<CameraEntity>)
    suspend fun clearCameras()

    suspend fun getDoors(): List<DoorEntity>
    suspend fun saveDoors(doors: List<DoorEntity>)
    suspend fun clearDoors()

    suspend fun getCamerasAsFlow(): Flow<List<CameraEntity>>

    suspend fun saveNameForDoorById(id: Int, name: String)
}