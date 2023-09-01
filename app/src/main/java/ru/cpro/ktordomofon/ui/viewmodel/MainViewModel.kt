package ru.cpro.ktordomofon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.cpro.ktordomofon.domain.repository.IntercomRepository
import ru.cpro.ktordomofon.domain.usecase.GetCamerasInRoomsUseCase
import ru.cpro.ktordomofon.domain.usecase.GetDoorsFromDbUseCase
import ru.cpro.ktordomofon.domain.usecase.SaveNameForDoorByIdUseCase
import ru.cpro.ktordomofon.domain.usecase.UpdateDbFromNetUseCase

class MainViewModel(
    private val getCamerasInRoomsUseCase: GetCamerasInRoomsUseCase,
    private val getDoorsFromDbUseCase: GetDoorsFromDbUseCase,
    private val updateDbFromNetUseCase: UpdateDbFromNetUseCase,
    private val saveNameForDoorByIdUseCase: SaveNameForDoorByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow((MainViewUiState()))
    val uiState: StateFlow<MainViewUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            updateDbFromNetUseCase()
            val cameras = getCamerasInRoomsUseCase().getOrNull() ?: emptyMap()
            val doors = getDoorsFromDbUseCase()

            _uiState.update { currentState ->
                currentState.copy(
                    cameras = cameras,
                    doors = doors,
                    isLoading = false
                )
            }
        }
    }


    fun saveNameForDoorById(id: Int, name: String) {
        if(id < 0) return
        viewModelScope.launch {
            saveNameForDoorByIdUseCase(id, name)

            val doors = getDoorsFromDbUseCase()
            _uiState.update { currentState ->
                currentState.copy(
                    doors = doors,
                    isLoading = false
                )
            }
        }
    }

    fun updateDbFromNet() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            updateDbFromNetUseCase()
            val doors = getDoorsFromDbUseCase()
            _uiState.update { currentState ->
                currentState.copy(
                    doors = doors,
                    isLoading = false
                )
            }
        }
    }

}