package ru.cpro.ktordomofon.ktor_client_tests

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import ru.cpro.ktordomofon.data.source.remote.RubetekServiceImpl
import ru.cpro.ktordomofon.data.source.remote.dto.CameraDto
import ru.cpro.ktordomofon.data.source.remote.dto.CamerasDataDto
import ru.cpro.ktordomofon.data.source.remote.dto.CamerasDto

class CamerasTest {

    private val response =
        "{" +
        "\"success\": true," +
        "\"data\": {" +
        "   \"room\": ["+
        "       \"FIRST\","+
        "       \"SECOND\""+
        "   ],"+
        "   \"cameras\": ["+
        "       {" +
        "           \"name\": \"Camera 1\","+
        "           \"snapshot\": \"https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png\"," +
        "           \"room\": \"FIRST\"," +
        "           \"id\": 1," +
        "           \"favorites\": true," +
        "           \"rec\": false" +
        "       }" +
        "   ]" +
        "}" +
        "}"

    private val mockEngine = MockEngine { _ ->
        respond(
            content = ByteReadChannel(response),
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    @Test
    fun `valid response test`() = runTest {

        val mockClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }
        val service = RubetekServiceImpl(mockClient)
        val cameras = service.getCameras()
        val expected = Result.success(
            CamerasDto(
                success = true,
                data = CamerasDataDto(
                    room = listOf(
                        "FIRST",
                        "SECOND"
                    ),
                    cameras = listOf(
                        CameraDto(
                            name = "Camera 1",
                            snapshot = "https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png",
                            room = "FIRST",
                            id = 1,
                            favorites = true,
                            rec = false
                        )
                    )
                )
            )
        )

        Assert.assertEquals(expected, cameras)
    }
}