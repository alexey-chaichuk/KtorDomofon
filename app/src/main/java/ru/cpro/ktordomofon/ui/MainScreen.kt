package ru.cpro.ktordomofon.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.cpro.ktordomofon.data.repository.IntercomRepositoryImpl
import ru.cpro.ktordomofon.ui.viewmodel.MainViewModel
import ru.cpro.ktordomofon.ui.viewmodel.MainViewModelFactory

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: MainViewModel = viewModel( factory = MainViewModelFactory(IntercomRepositoryImpl.create()))
) {
    val uiState = vm.uiState.collectAsState()

    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Камеры", "Двери")

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Мой дом",
            style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(20.dp))

        TabRow(selectedTabIndex = tabIndex,
            backgroundColor = MaterialTheme.colors.background,
            contentColor = contentColorFor(MaterialTheme.colors.background)) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title)},
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when(tabIndex) {
            0 -> CamerasScreen(vm, uiState)
            1 -> DoorsScreen(uiState)
        }
    }
}
