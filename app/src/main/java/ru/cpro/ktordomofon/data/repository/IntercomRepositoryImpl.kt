package ru.cpro.ktordomofon.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.cpro.ktordomofon.data.Mapper
import ru.cpro.ktordomofon.data.source.local.DbCache
import ru.cpro.ktordomofon.data.source.remote.RubetekService
import ru.cpro.ktordomofon.domain.model.Camera
import ru.cpro.ktordomofon.domain.model.Door
import ru.cpro.ktordomofon.domain.repository.IntercomRepository

class IntercomRepositoryImpl(
    private val service: RubetekService,
    private val db: DbCache,
    private val mapper: Mapper = Mapper(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IntercomRepository {

    override suspend fun getCamerasInRooms(): Result<Map<String?, List<Camera>>> {
        val camerasInRooms = withContext(ioDispatcher) {
            service.getCameras().mapCatching { camerasDto ->
                camerasDto.data.cameras.map { cameraDto ->
                    mapper.mapCameraDtoToDomain(cameraDto)
                }
            }.map { cameras ->
                cameras.groupBy {
                    it.room
                }
            }
        }
        return camerasInRooms
    }

    override suspend fun getCameras(): Result<List<Camera>> {
        return withContext(ioDispatcher) {
            service.getCameras().mapCatching { camerasDto ->
                camerasDto.data.cameras.map { cameraDto ->
                    mapper.mapCameraDtoToDomain(cameraDto)
                }
            }
        }
    }

    override suspend fun getDoors(): Result<List<Door>> {
        return withContext(ioDispatcher) {
            service.getDoors().mapCatching { doorsDto ->
                doorsDto.doors.map { doorDto ->
                    mapper.mapDoorDtoToDomain(doorDto)
                }
            }
        }
    }

    override suspend fun getCamerasFromDb(): List<Camera> {
        return db.getCameras().map {
            mapper.mapCameraEntityToDomain(it)
        }
    }

    override suspend fun getDoorsFromDb(): List<Door> {
        return db.getDoors().map {
            mapper.mapDoorEntityToDomain(it)
        }
    }

    override suspend fun saveCamerasToDb(cameras: List<Camera>) {
        return db.saveCameras(
            cameras.map {
                mapper.mapCameraDomainToEntity(it)
            }
        )
    }

    override suspend fun saveDoorsToDb(doors: List<Door>) {
        return db.saveDoors(
            doors.map {
                mapper.mapDoorDomainToEntity(it)
            }
        )
    }

    override suspend fun updateCamerasInDbFromRemoteDs() {
        service.getCameras().mapCatching { camerasDto ->
            db.saveCameras(
                camerasDto.data.cameras.map { cameraDto ->
                    mapper.mapCameraDtoToEntity(cameraDto)
                }
            )
        }
    }

    override suspend fun updateDoorsInDbFromRemoteDs() {
        service.getDoors().mapCatching { doorsDto ->
            db.saveDoors(
                doorsDto.doors.map { doorDto ->
                    mapper.mapDoorDtoToEntity(doorDto)
                }
            )
        }
    }

    override suspend fun getCamerasFromDbAsFlow(): Flow<List<Camera>> {
        TODO()
    }

    companion object {
        fun create() = IntercomRepositoryImpl(
            RubetekService.create(),
            DbCache.create()
        )
    }
}
