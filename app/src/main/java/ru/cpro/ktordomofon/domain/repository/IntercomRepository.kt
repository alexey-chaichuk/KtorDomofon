package ru.cpro.ktordomofon.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.cpro.ktordomofon.domain.model.Camera
import ru.cpro.ktordomofon.domain.model.Door

interface IntercomRepository {
    suspend fun getCamerasInRooms() : Result<Map<String?, List<Camera>>>

    suspend fun getCameras() : Result<List<Camera>>
    suspend fun getDoors() : Result<List<Door>>

    suspend fun getCamerasFromDb() : List<Camera>
    suspend fun getDoorsFromDb() : List<Door>

    suspend fun saveCamerasToDb(cameras: List<Camera>)
    suspend fun saveDoorsToDb(doors: List<Door>)

    suspend fun updateCamerasInDbFromRemoteDs()
    suspend fun updateDoorsInDbFromRemoteDs()

    suspend fun getCamerasFromDbAsFlow() : Flow<List<Camera>>

    suspend fun saveNameForDoorById(id: Int, name: String)
}