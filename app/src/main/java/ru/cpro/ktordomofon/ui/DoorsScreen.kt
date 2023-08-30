package ru.cpro.ktordomofon.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState

@Composable
fun DoorsScreen(uiState: State<MainViewUiState>,
                modifier: Modifier = Modifier) {
    LazyColumn {
        items(uiState.value.doors) {
            Text(
                text = it.toString()
            )
        }
    }
}