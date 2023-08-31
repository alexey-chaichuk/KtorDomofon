package ru.cpro.ktordomofon.domain.usecase

import ru.cpro.ktordomofon.domain.model.Camera
import ru.cpro.ktordomofon.domain.repository.IntercomRepository

class GetCamerasInRoomsUseCase(
    private val repository: IntercomRepository
) {
    suspend operator fun invoke(): Result<Map<String?, List<Camera>>> {
        return repository.getCameras().map { cameras ->
            cameras.groupBy {
                it.room
            }
        }
    }
}