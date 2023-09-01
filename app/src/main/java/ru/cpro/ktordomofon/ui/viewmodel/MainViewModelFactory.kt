package ru.cpro.ktordomofon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.cpro.ktordomofon.domain.repository.IntercomRepository
import ru.cpro.ktordomofon.domain.usecase.GetCamerasInRoomsUseCase
import ru.cpro.ktordomofon.domain.usecase.GetDoorsFromDbUseCase
import ru.cpro.ktordomofon.domain.usecase.SaveNameForDoorByIdUseCase
import ru.cpro.ktordomofon.domain.usecase.UpdateDbFromNetUseCase
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val getCamerasInRoomsUseCase: GetCamerasInRoomsUseCase,
    private val getDoorsFromDbUseCase: GetDoorsFromDbUseCase,
    private val updateDbFromNetUseCase: UpdateDbFromNetUseCase,
    private val saveNameForDoorByIdUseCase: SaveNameForDoorByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                getCamerasInRoomsUseCase = getCamerasInRoomsUseCase,
                getDoorsFromDbUseCase = getDoorsFromDbUseCase,
                updateDbFromNetUseCase = updateDbFromNetUseCase,
                saveNameForDoorByIdUseCase = saveNameForDoorByIdUseCase
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
