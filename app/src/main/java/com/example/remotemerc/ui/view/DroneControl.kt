package com.example.remotemerc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.remotemerc.data.Drone
import com.example.remotemerc.data.DroneViewModel
import com.example.remotemerc.R
import com.example.remotemerc.data.GameDataViewModel
import com.example.remotemerc.data.PlayerViewModel
import io.github.sceneview.rememberEngine

@Composable
fun DroneControl(modifier: Modifier = Modifier, droneViewModel: DroneViewModel,
                 navController: NavHostController) {
    DroneFleet(droneViewModel, navController)
}

@Composable
fun DroneView(getDrone: () -> Drone?, onBack: () -> Unit, playerViewModel:PlayerViewModel,
              explode: () -> Unit, gameDataViewModel: GameDataViewModel) {
    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        DroneScene(getDrone, playerViewModel, explode, gameDataViewModel)
        DroneUI(onBack, playerViewModel)
    }
}

@Composable
fun DroneUI(onBack: () -> Unit, playerViewModel: PlayerViewModel) {
    Column(Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween) {
        IconButton({
            onBack()
        }, modifier = Modifier.size(24.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Go Back")
        }
        DroneControls(playerViewModel)
    }
}
@Composable
fun DroneScene(getDrone: () -> Drone?, playerViewModel: PlayerViewModel, explode: () -> Unit, gameDataViewModel: GameDataViewModel) {
    val drone = getDrone()
    if (drone != null) {
        Box(Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center) {
            GameScreen(
                rememberEngine(), playerViewModel, gameDataViewModel,
                explode,
            )
        }
    }
}

@Composable
fun DroneControls(playerViewModel: PlayerViewModel) {
    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom){
        JoyStick {x, y ->
               playerViewModel.forwardAcceleration = y
        }
        SecondaryButton({playerViewModel.upAmt = 1f}, {playerViewModel.upAmt = 0f})
        PrimaryButton() //does nothing (kaboom?)
        SecondaryButton({playerViewModel.upAmt = -1f},{playerViewModel.upAmt = 0f})
        JoyStick { x, y ->
            playerViewModel.turnAmt = x * x * x
        }
    }
}

@Composable
fun JoyStick(onMove: (offsetX: Float, offsetY: Float)-> Unit) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val density = LocalDensity.current
    val diameterPx = with(density) {100.dp.toPx()}
    Box(Modifier.size(100.dp)
        .clip(CircleShape)
        .background(Color(0.5f, 0.5f, 0.5f, 0.3f))
        .pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    offsetX = 0f
                    offsetY = 0f
                    onMove(0f, 0f)
                }
            ) { change, dragAmount ->
                change.consume()

                offsetX += dragAmount.x
                offsetY += dragAmount.y

                val maxRadius = diameterPx/2

                val distance = kotlin.math.sqrt(offsetX * offsetX + offsetY * offsetY)
                if (distance > maxRadius) {
                    val scale = maxRadius / distance
                    offsetX *= scale
                    offsetY *= scale
                }

                onMove(offsetX / maxRadius, offsetY / maxRadius)
            }}
        ,
        contentAlignment = Alignment.Center) {
        Box(Modifier
            .size(15.dp)
            .offset {
                IntOffset(offsetX.toInt(), offsetY.toInt())
            }
            .clip(CircleShape)
            .background(Color.Black)
        )
    }
}

@Composable
fun SecondaryButton(onHoldStart: () -> Unit, onHoldEnd: () -> Unit) {
    Box(Modifier.size(30.dp)
        .clip(CircleShape)
        .background(Color.Gray)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    onHoldStart()

                    tryAwaitRelease() // wait until finger lifts

                    onHoldEnd()
                }
            )
        }
    )
}
@Composable
fun PrimaryButton() {
    Box(Modifier.size(50.dp, 20.dp)
        .clip(CircleShape)
        .background(Color.DarkGray))
}

@Composable
fun DroneFleet(droneViewModel: DroneViewModel, navController: NavHostController) {
    val drones: List<Drone> by remember { mutableStateOf(droneViewModel.getAllOwned()) }

    Column(Modifier.fillMaxSize()
        .background(Color.DarkGray)
        .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Your Fleet", fontSize = 24.sp, color = Color.LightGray)
        HorizontalDivider()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp)
        ) {
            items(drones) { drone ->
                if (drone.id == droneViewModel.selectedDroneId) {
                    HighlightedDroneCard(drone) {
                        navController.navigate("drone-view")
                    }
                } else {
                    DroneCard(drone) { droneViewModel.selectedDroneId = drone.id }
                }
            }
        }
    }
}

@Composable
fun DroneCard(drone: Drone, onClick: () -> Unit) {
    Box(Modifier.size(120.dp)
        .clickable {
            onClick()
        }) {
        Column(Modifier.size(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painterResource(R.drawable.drone_icon_simple),
                "Drone Icon",
                modifier = Modifier.weight(1f))
            Text(drone.model,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }
    }
}

@Composable
fun HighlightedDroneCard(drone: Drone, onClick:() -> Unit) {
    Box(Modifier.size(120.dp)
        .border(2.dp, Color.White)
    ) {
        DroneCard(drone, onClick)
    }
}