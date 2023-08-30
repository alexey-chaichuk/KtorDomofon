package ru.cpro.ktordomofon.data.source.local

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.cpro.ktordomofon.data.source.local.entity.CameraEntity
import ru.cpro.ktordomofon.data.source.local.entity.DoorEntity

class DbCacheImpl(
    private val realm: Realm
) : DbCache {
    override suspend fun getCameras(): List<CameraEntity> {
        val results = realm.query<CameraEntity>().find()
        return realm.copyFromRealm(results)
    }

    override suspend fun saveCameras(cameras: List<CameraEntity>) {
        realm.write {
            cameras.map {
                copyToRealm(it)
            }
        }
    }

    override suspend fun getDoors(): List<DoorEntity> {
        val results = realm.query<DoorEntity>().find()
        return realm.copyFromRealm(results)
    }

    override suspend fun saveDoors(doors: List<DoorEntity>) {
        realm.write {
            doors.map {
                copyToRealm(it)
            }
        }
    }

    override suspend fun getCamerasAsFlow(): Flow<List<CameraEntity>> {
        return realm.query<CameraEntity>().asFlow().map { changes ->
            when (changes) {
                is UpdatedResults -> {
                    changes.list
                }

                else -> TODO()
            }
        }
    }
}
