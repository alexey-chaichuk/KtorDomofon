package ru.cpro.ktordomofon.data.source.local

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.flow.Flow
import ru.cpro.ktordomofon.data.source.local.entity.CameraEntity
import ru.cpro.ktordomofon.data.source.local.entity.DoorEntity

interface DbCache {
    suspend fun getCameras(): List<CameraEntity>
    suspend fun saveCameras(cameras: List<CameraEntity>)

    suspend fun getDoors(): List<DoorEntity>
    suspend fun saveDoors(doors: List<DoorEntity>)

    suspend fun getCamerasAsFlow(): Flow<List<CameraEntity>>

    companion object {
        fun create(): DbCacheImpl {
            val config = RealmConfiguration.create(
                schema = setOf(
                    CameraEntity::class,
                    DoorEntity::class
                )
            )
            val realm: Realm = Realm.open(config)
            return DbCacheImpl(realm)
        }
    }
}