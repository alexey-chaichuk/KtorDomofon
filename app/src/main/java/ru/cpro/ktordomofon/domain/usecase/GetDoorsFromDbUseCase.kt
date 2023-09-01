package ru.cpro.ktordomofon.domain.usecase

import ru.cpro.ktordomofon.domain.model.Door
import ru.cpro.ktordomofon.domain.repository.IntercomRepository

class GetDoorsFromDbUseCase(
    private val repository: IntercomRepository
) {
    suspend operator fun invoke(): List<Door> {
        return repository.getDoorsFromDb()
    }
}