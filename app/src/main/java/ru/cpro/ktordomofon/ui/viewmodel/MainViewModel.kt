package ru.cpro.ktordomofon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.cpro.ktordomofon.domain.repository.IntercomRepository

class MainViewModel(
    repository: IntercomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow((MainViewUiState()))
    val uiState: StateFlow<MainViewUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val cameras = repository.getCamerasInRooms().getOrNull() ?: emptyMap()
            val doors = repository.getDoors().getOrNull() ?: emptyList()

            _uiState.update { currentState ->
                currentState.copy(
                    cameras = cameras,
                    doors = doors
                )
            }
        }
    }
}