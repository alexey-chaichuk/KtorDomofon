package ru.cpro.ktordomofon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.cpro.ktordomofon.R
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState

@Composable
fun DoorsScreen(uiState: State<MainViewUiState>,
                modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier
        .background(MaterialTheme.colors.background)
        .padding(top = 16.dp)
    ) {
        items(uiState.value.doors) { door ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    door.snapshot?.let {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            AsyncImage(
                                model = door.snapshot,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Box(
                                modifier = Modifier.matchParentSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painterResource(R.drawable.play_button),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = door.name, modifier = Modifier.weight(1f))
                        Icon(painter = painterResource(id = R.drawable.lockon),
                            tint = colorResource(id = R.color.blue_lock),
                            contentDescription = null)
                    }
                }
            }
        }
    }
}