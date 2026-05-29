package com.example.remotemerc.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remotemerc.data.Drone
import com.example.remotemerc.data.DroneViewModel
import com.example.remotemerc.data.FakePerson
import com.example.remotemerc.R

@Composable
fun DroneShopScreen(droneViewModel: DroneViewModel) {

    val cash = droneViewModel.cash
    val ownedDrones = droneViewModel.getAllOwned()
    val shopDrones = droneViewModel.getAll()

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                HeaderSection(
                    cash = cash,
                    ownedCount = ownedDrones.size
                )
            }

            //TODO: filters for price, speed, alphabetized
            // all of these in descending or ascending order (toggle button)

            items(shopDrones) { drone ->
                DroneCard(
                    drone = drone,
                    canAfford = drone.priceUSD <= cash,
                    onBuyClick = {
                        droneViewModel.purchaseById(drone.id)
                    }
                )
            }
        }
    }
}

@Composable
fun HeaderSection(
    cash: Double,
    ownedCount: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Drone\nShop",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${cash.toInt()}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Drones owned: $ownedCount",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun PersonBountyCard(
    person: FakePerson,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = person.fullName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = person.location,
                fontSize = 14.sp
            )

            Text(
                text = "Bounty: $${person.bounty}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DroneCard(
    drone: Drone,
    canAfford: Boolean,
    onBuyClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.drone_icon_simple),
                    contentDescription = "Drone Icon"
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = drone.model,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Speed: ${drone.topSpeedMph} mph",
                    fontSize = 13.sp
                )

                Text(
                    text = "Altitude: ${drone.maxAltitude} ft",
                    fontSize = 13.sp
                )

                Text(
                    text = "Battery: ${drone.batteryLifeSeconds}s",
                    fontSize = 13.sp
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$${drone.priceUSD.toInt()}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onBuyClick,
                    enabled = canAfford,
                    modifier = Modifier.height(36.dp)
                ) {
                    Text("Buy")
                }
            }
        }
    }
}