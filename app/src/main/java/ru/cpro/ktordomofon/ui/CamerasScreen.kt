package ru.cpro.ktordomofon.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import ru.cpro.ktordomofon.R
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState

@Composable
fun CamerasScreen(
    uiState: State<MainViewUiState>,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(top = 16.dp)
    ) {
        items(
            uiState.value.cameras.toList(),
            key = {it.first ?: ""}
        ) { room ->

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
                room.first?.let { roomName ->
                    Text(text = roomName,
                        style = MaterialTheme.typography.titleLarge)
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp))

                for (camera in room.second) {
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

                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)) {

                                    val recText = if (camera.rec) "REC" else ""
                                    Text(text = recText,
                                        color = Color.Red,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if(camera.favorites) {
                                        Icon(Icons.Outlined.Star,
                                            tint = Color.Yellow,
                                            contentDescription = null)
                                    }
                                }

                                Box(modifier = Modifier.matchParentSize(),
                                    contentAlignment = Alignment.Center) {
                                    Icon(
                                        painterResource(R.drawable.ic_play),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(60.dp))
                                }
                            }

                            Text(modifier = Modifier.padding(16.dp),
                                text = camera.name,
                                style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp))

                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(16.dp))
        }
    }
}