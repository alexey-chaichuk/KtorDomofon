package ru.cpro.ktordomofon.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import ru.cpro.ktordomofon.data.repository.IntercomRepository
import ru.cpro.ktordomofon.ui.viewmodel.MainViewModel
import ru.cpro.ktordomofon.ui.viewmodel.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainViewModel = viewModel( factory = MainViewModelFactory(IntercomRepository.create()))
) {
    val uiState = vm.uiState.collectAsState()

    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Камеры", "Двери")

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Мой дом",
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(20.dp))

        TabRow(selectedTabIndex = tabIndex) {
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
