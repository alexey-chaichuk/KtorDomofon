package ru.cpro.ktordomofon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.cpro.ktordomofon.R
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorsScreen(uiState: State<MainViewUiState>,
                modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier
        .background(MaterialTheme.colors.background)
        .padding(top = 16.dp)
    ) {
        items(uiState.value.doors) { door ->
            val swipeableState = rememberSwipeableState(0)
            val sizePx = with(LocalDensity.current) { 100.dp.toPx() }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .swipeable(
                        state = swipeableState,
                        anchors = mapOf(
                            0f to 0,
                            -sizePx to 1,
                            0f to 0
                        ),
                        thresholds = { _, _ -> FractionalThreshold(0.5f) },
                        orientation = Orientation.Horizontal
                    )
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