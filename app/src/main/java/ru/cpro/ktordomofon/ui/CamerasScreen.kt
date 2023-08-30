package ru.cpro.ktordomofon.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.cpro.ktordomofon.R
import ru.cpro.ktordomofon.ui.viewmodel.MainViewModel
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamerasScreen(
    vm: MainViewModel,
    uiState: State<MainViewUiState>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
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
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                for (camera in room.second) {
                    var fav by remember { mutableStateOf(camera.favorites) }
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                 fav = !fav
                                false
                            } else false
                        }, positionalThreshold = { 60.dp.toPx() }
                    )
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        //TODO switch favorites for item
                    }

                    SwipeToDismiss(state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.star),
                                    tint = colorResource(R.color.gold_star),
                                    contentDescription = null
                                )
                            }
                        },
                        dismissContent = {
                            Card {
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
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        })

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}