package ru.cpro.ktordomofon.domain.usecase

import ru.cpro.ktordomofon.domain.repository.IntercomRepository

class UpdateDbFromNetUseCase(
    private val repository: IntercomRepository
) {
    suspend operator fun invoke() {
        repository.updateCamerasInDbFromRemoteDs()
        repository.updateDoorsInDbFromRemoteDs()
    }
}