package ru.cpro.ktordomofon.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.cpro.ktordomofon.R
import ru.cpro.ktordomofon.ui.viewmodel.MainViewModel
import ru.cpro.ktordomofon.ui.viewmodel.MainViewUiState
import kotlin.math.roundToInt

private enum class SwipeStateDoor{
    On,
    Off
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorsScreen(vm: MainViewModel,
                uiState: State<MainViewUiState>,
                modifier: Modifier = Modifier) {
    var isChangeNameDialogVisible by remember { mutableStateOf(false) }
    var textToChange by remember { mutableStateOf("") }
    var doorIdToChange by remember { mutableStateOf(-1) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.value.isLoading,
        onRefresh = vm::updateDbFromNet
    )

    if(isChangeNameDialogVisible) {
        EditNameDialog(
            content = {
                OutlinedTextField(
                    value = textToChange,
                    onValueChange = { textToChange = it },
                    label = { Text("Name") },
                )
            },
            onDismiss = { isChangeNameDialogVisible = false }
        ) {
            isChangeNameDialogVisible = false
            vm.saveNameForDoorById(
                id = doorIdToChange,
                name = textToChange
            )
            textToChange = ""
            doorIdToChange = -1
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = modifier
                .background(MaterialTheme.colors.background)
                .padding(top = 16.dp)
        ) {
            items(uiState.value.doors) { door ->
                val scope = rememberCoroutineScope()
                val swipeState = rememberSwipeableState(SwipeStateDoor.Off)
                val sizePx = with(LocalDensity.current) { 120.dp.toPx() }

                Box(
                    modifier = Modifier
                        .swipeable(
                            state = swipeState,
                            anchors = mapOf(
                                0f to SwipeStateDoor.Off,
                                -sizePx to SwipeStateDoor.On,
                            ),
                            thresholds = { _, _ -> FractionalThreshold(0.5f) },
                            orientation = Orientation.Horizontal
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(16.dp)
                    ) {
                        IconButton(
                            onClick = {
                                textToChange = door.name
                                doorIdToChange = door.id
                                isChangeNameDialogVisible = true
                                scope.launch {
                                    swipeState.animateTo(SwipeStateDoor.Off, tween(600, 0))
                                }
                            }
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
                                    swipeState.animateTo(SwipeStateDoor.Off, tween(600, 0))
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
                            .offset {
                                IntOffset(swipeState.offset.value.roundToInt(), 0)
                            },
                        elevation = 2.dp,
                        shape = RoundedCornerShape(12.dp)
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

        PullRefreshIndicator(
            refreshing = uiState.value.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = if (uiState.value.isLoading) Color.Red else Color.Green,
        )
    }
}

@Composable
fun EditNameDialog(
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
) {
    Dialog(onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column {
                Column(Modifier.padding(24.dp)) {
                    Text(text = "Change name")
                    Spacer(Modifier.size(16.dp))
                    content.invoke()
                }
                Spacer(Modifier.height(4.dp))
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    TextButton(
                        onClick = { onDismiss.invoke() },
                        content = { Text("CANCEL") },
                    )
                    TextButton(
                        onClick = { onSave.invoke() },
                        content = { Text("OK") },
                    )
                }
            }
        }
    }
}