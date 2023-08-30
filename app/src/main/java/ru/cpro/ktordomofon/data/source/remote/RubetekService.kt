package ru.cpro.ktordomofon.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.cpro.ktordomofon.data.source.remote.dto.CamerasDto
import ru.cpro.ktordomofon.data.source.remote.dto.DoorsDto

interface RubetekService {

    suspend fun getCameras(): Result<CamerasDto>
    suspend fun getDoors(): Result<DoorsDto>

    companion object {
        fun create() = RubetekServiceImpl(
            client = HttpClient() {
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
        )
    }
}