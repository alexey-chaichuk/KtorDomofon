package ru.cpro.ktordomofon.ui

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
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.cpro.ktordomofon.R
import ru.cpro.ktordomofon.ui.viewmodel.MainViewModel
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState

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
                    var fav by remember { mutableStateOf(camera.favorites) }
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                 fav = !fav
                                //TODO save favorites for item by vm
                                false
                            } else false
                        }
                    )

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
                                        style = MaterialTheme.typography.body1
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