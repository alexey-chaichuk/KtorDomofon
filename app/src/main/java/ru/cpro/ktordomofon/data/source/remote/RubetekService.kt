package ru.cpro.ktordomofon.data.source.remote

import ru.cpro.ktordomofon.data.source.remote.dto.CamerasDto
import ru.cpro.ktordomofon.data.source.remote.dto.DoorsDto

interface RubetekService {

    suspend fun getCameras(): Result<CamerasDto>
    suspend fun getDoors(): Result<DoorsDto>

}