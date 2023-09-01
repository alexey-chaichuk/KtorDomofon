package ru.cpro.ktordomofon.di

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.serialization.json.Json
import ru.cpro.ktordomofon.data.repository.IntercomRepositoryImpl
import ru.cpro.ktordomofon.data.source.local.DbCache
import ru.cpro.ktordomofon.data.source.local.DbCacheImpl
import ru.cpro.ktordomofon.data.source.local.entity.CameraEntity
import ru.cpro.ktordomofon.data.source.local.entity.DoorEntity
import ru.cpro.ktordomofon.data.source.remote.RubetekService
import ru.cpro.ktordomofon.data.source.remote.RubetekServiceImpl
import ru.cpro.ktordomofon.domain.repository.IntercomRepository

interface AppModule {
    val httpClient: HttpClient
    val rubetekService: RubetekService
    val realmDb: Realm
    val dbCache: DbCache
    val intercomRepository: IntercomRepository

    class Base(
        private val appContext: Context
    ) : AppModule {
        override val httpClient: HttpClient by lazy {
            HttpClient() {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 5_000
                    connectTimeoutMillis = 5_000
                    socketTimeoutMillis = 5_000
                }
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
        }

        override val rubetekService: RubetekService by lazy {
            RubetekServiceImpl(httpClient)
        }

        override val realmDb: Realm by lazy {
            val config = RealmConfiguration.create(
                schema = setOf(
                    CameraEntity::class,
                    DoorEntity::class
                )
            )
            Realm.open(config)
        }

        override val dbCache: DbCache by lazy {
            DbCacheImpl(realmDb)
        }

        override val intercomRepository: IntercomRepository by lazy {
            IntercomRepositoryImpl(
                service = rubetekService,
                db = dbCache
            )
        }
    }
}