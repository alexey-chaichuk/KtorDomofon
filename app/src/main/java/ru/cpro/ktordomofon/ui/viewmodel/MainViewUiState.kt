package ru.cpro.ktordomofon.ui.viewmodel

import ru.cpro.ktordomofon.domain.model.Camera
import ru.cpro.ktordomofon.domain.model.Door

data class MainViewUiState(
    val cameras: Map<String?, List<Camera>> = emptyMap(),
    val doors: List<Door> = emptyList(),
    val selectedTab: Int = 0,
    val isLoading: Boolean = false
)