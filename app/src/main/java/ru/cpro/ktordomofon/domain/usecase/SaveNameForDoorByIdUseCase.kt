package ru.cpro.ktordomofon.domain.usecase

import ru.cpro.ktordomofon.domain.repository.IntercomRepository

class SaveNameForDoorByIdUseCase(
    private val repository: IntercomRepository
) {
    suspend operator fun invoke(id: Int, name: String) {
        repository.saveNameForDoorById(id, name)
    }
}