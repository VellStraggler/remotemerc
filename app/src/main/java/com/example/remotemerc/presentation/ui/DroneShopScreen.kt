package com.example.remotemerc.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remotemerc.R


@Composable
fun DroneShopScreen() {
    Scaffold(
        bottomBar = { DroneBottomBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            HeaderSection()

            Spacer(modifier = Modifier.height(20.dp))

            DroneCard(
                name = "XLR",
                price = "$990",
                isMain = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            repeat(3) {
                DronePlaceholderCard()
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Column {
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
                    text = "$190000",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Drones owned: 16",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun DroneCard(
    name: String,
    price: String,
    isMain: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
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
                Image(painterResource(R.drawable.drone_icon_simple),
                    "Drone Icon")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = price,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = {},
                    modifier = Modifier.height(36.dp)
                ) {
                    Text("Buy")
                }
            }
        }
    }
}

@Composable
fun DronePlaceholderCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
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
                    .size(55.dp)
                    .border(2.dp, Color.Black)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier
                    .height(8.dp)
                    .weight(1f)
                    .background(Color.LightGray)
            )
        }
    }
}

@Composable
fun DroneBottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Shop")
            },
            label = { Text("Shop") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Home")
            },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            },
            label = { Text("Menu") }
        )
    }
}