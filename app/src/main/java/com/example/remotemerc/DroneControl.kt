package com.example.remotemerc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DroneControl(modifier: Modifier = Modifier, droneViewModel: DroneViewModel) {
    if (droneViewModel.selectedDroneId != -1) {
        val drone = droneViewModel.getById(droneViewModel.selectedDroneId)!!
        DroneView(drone)
    } else {
        DroneFleet(droneViewModel)
    }
}

@Composable
fun DroneView(drone: Drone) {
    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter) {
        DroneScene(drone)
        DroneControls()
    }
}

@Composable
fun DroneScene(drone: Drone) {
    Column {
        IconButton({}, modifier= Modifier.size(24.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Go Back")
        }
        Box(Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center) {
                Text("Example Scene")
        }
    }
}

@Composable
fun DroneControls() {
    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom){
        JoyStick()
        SecondaryButton()
        PrimaryButton()
        SecondaryButton()
        JoyStick()
    }
}

@Composable
fun JoyStick() {
    Box(Modifier.size(100.dp)
        .clip(CircleShape)
        .background(Color(0.1f, 0.1f, 0.1f, 0.3f)),
        contentAlignment = Alignment.Center) {
        Box(Modifier.size(10.dp)
            .clip(CircleShape)
            .background(Color.Black))
    }
}

@Composable
fun SecondaryButton() {
    Box(Modifier.size(20.dp)
        .clip(CircleShape)
        .background(Color.Gray))
}
@Composable
fun PrimaryButton() {
    Box(Modifier.size(40.dp, 12.dp)
        .clip(CircleShape)
        .background(Color.DarkGray))
}

@Composable
fun DroneFleet(droneViewModel: DroneViewModel) {
    val drones: List<Drone> by remember { mutableStateOf(droneViewModel.getAll()) }

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
                    HighlightedDroneCard(drone)
                } else {
                    DroneCard(drone)
                }
            }
        }
    }
}

@Composable
fun DroneCard(drone: Drone) {
    Box(Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painterResource(R.drawable.drone_icon_simple),
                "Drone Icon")
            Text(drone.model,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }
    }
}

@Composable
fun HighlightedDroneCard(drone: Drone) {
    Box(Modifier.size(120.dp)
        .border(2.dp, Color.White)
    ) {
        DroneCard(drone)
    }
}