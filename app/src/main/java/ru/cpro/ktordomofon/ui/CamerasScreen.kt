package ru.cpro.ktordomofon.ui

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import ru.cpro.ktordomofon.ui.viewmodel.MainViewModel
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState
import kotlin.math.roundToInt

private enum class SwipeStateCamera {
    On,
    Off
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CamerasScreen(
    vm: MainViewModel,
    uiState: State<MainViewUiState>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(top = 16.dp)
    ) {
        items(
            uiState.value.cameras.toList(),
            key = { it.first ?: "" }
        ) { room ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                room.first?.let { roomName ->
                    Text(
                        text = roomName,
                        style = MaterialTheme.typography.h4
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                for (camera in room.second) {
                    val scope = rememberCoroutineScope()
                    var fav by remember { mutableStateOf(camera.favorites) }
                    val swipeState = rememberSwipeableState(SwipeStateCamera.Off)
                    val sizePx = with(LocalDensity.current) { 80.dp.toPx() }

                    Box(
                        modifier = Modifier
                            .swipeable(
                                state = swipeState,
                                anchors = mapOf(
                                    0f to SwipeStateCamera.Off,
                                    -sizePx to SwipeStateCamera.On,
                                ),
                                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                                orientation = Orientation.Horizontal
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            Modifier
                                .padding(horizontal = 16.dp)
                                .align(Alignment.CenterEnd)
                        ) {
                            IconButton(
                                onClick = {
                                    Log.d("CameraScreen", "star btn clicked")
                                    fav = !fav
                                    scope.launch {
                                        swipeState.animateTo(SwipeStateCamera.Off, tween(600, 0))
                                    }
                                }
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.star),
                                    tint = colorResource(R.color.gold_star),
                                    contentDescription = null
                                )
                            }
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .offset {
                                    IntOffset(swipeState.offset.value.roundToInt(), 0)
                                },
                            elevation = 2.dp,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    AsyncImage(
                                        model = camera.snapshot,
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {

                                        val recText = if (camera.rec) "REC" else ""
                                        Text(
                                            text = recText,
                                            color = Color.Red,
                                            modifier = Modifier.weight(1f)
                                        )

                                        if (fav) {
                                            Icon(
                                                painterResource(id = R.drawable.star_filled),
                                                tint = colorResource(R.color.gold_star),
                                                contentDescription = null
                                            )
                                        }
                                    }

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

                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = camera.name,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}