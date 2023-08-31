package ru.cpro.ktordomofon.ui

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.cpro.ktordomofon.R
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState
import kotlin.math.roundToInt

private enum class SwipeState{
    On,
    Off
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorsScreen(uiState: State<MainViewUiState>,
                modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier
        .background(MaterialTheme.colors.background)
        .padding(top = 16.dp)
    ) {
        items(uiState.value.doors) { door ->
            val scope = rememberCoroutineScope()
            val swipeState = rememberSwipeableState(SwipeState.Off)
            val sizePx = with(LocalDensity.current) { 120.dp.toPx() }

            Box {
                Row(modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
                ) {
                    IconButton(
                        onClick = {
                            Log.d("DoorScreen", "edit btn clicked")
                            scope.launch {
                                swipeState.animateTo(SwipeState.Off, tween(600, 0))
                            }
                        },
                        enabled = true,

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            tint = colorResource(id = R.color.blue_lock),
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            scope.launch {
                                swipeState.animateTo(SwipeState.Off, tween(600, 0))
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            tint = colorResource(id = R.color.gold_star),
                            contentDescription = null
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .swipeable(
                            state = swipeState,
                            anchors = mapOf(
                                0f to SwipeState.Off,
                                -sizePx to SwipeState.On,
                            ),
                            thresholds = { _, _ -> FractionalThreshold(0.5f) },
                            orientation = Orientation.Horizontal
                        )
                        .offset {
                            IntOffset(swipeState.offset.value.roundToInt(), 0)
                        }
                ) {
                    Column {
                        door.snapshot?.let {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
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
                            Icon(
                                painter = painterResource(id = R.drawable.lockon),
                                tint = colorResource(id = R.color.blue_lock),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}