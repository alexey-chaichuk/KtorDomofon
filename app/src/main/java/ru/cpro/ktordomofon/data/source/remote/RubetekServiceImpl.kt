package ru.cpro.ktordomofon.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import ru.cpro.ktordomofon.data.source.remote.dto.CamerasDto
import ru.cpro.ktordomofon.data.source.remote.dto.DoorsDto

class RubetekServiceImpl(
    private val client: HttpClient
) : RubetekService {

    override suspend fun getCameras(): Result<CamerasDto> {
        return runCatching {
            client.get { url(CAMERAS_URL) }.body()
        }
    }

    override suspend fun getDoors(): Result<DoorsDto> {
        return runCatching {
            client.get { url(DOORS_URL) }.body()
        }
    }

    companion object {
        private const val BASE_URL = "http://cars.cprogroup.ru/api/rubetek"
        const val CAMERAS_URL = "$BASE_URL/cameras/"
        const val DOORS_URL = "$BASE_URL/doors/"
    }
}