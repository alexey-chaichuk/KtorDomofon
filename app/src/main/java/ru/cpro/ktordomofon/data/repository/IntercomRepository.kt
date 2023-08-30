package ru.cpro.ktordomofon.data.repository

import kotlinx.coroutines.flow.Flow
import ru.cpro.ktordomofon.data.source.local.DbCache
import ru.cpro.ktordomofon.data.source.remote.RubetekService
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

    companion object {
        fun create() = IntercomRepositoryImpl(
            RubetekService.create(),
            DbCache.create()
        )
    }
}