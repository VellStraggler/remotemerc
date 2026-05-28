package com.example.remotemerc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.remotemerc.presentation.ui.DroneShopScreen

sealed class AppScreen(val route: String) {
    data object Landing : AppScreen("landing-page")
    data object DroneControl: AppScreen("drone-control")
    data object DroneShop: AppScreen("drone-shop")
    data object DroneView: AppScreen("drone-view")

    companion object {
        val mainScreens = listOf(
            Landing,
            DroneControl,
            DroneShop
        )
    }
}

@Composable
fun MyNavBar(navController: NavController) {
    Row(Modifier.fillMaxWidth().height(64.dp).background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(
            onClick = {
                navController.navigate("landing-page")
            }
        ) {
            Icon(Icons.Filled.Home, "landing page")
        }
        IconButton(
            onClick = {
                navController.navigate("drone-control")
            }
        ) {
            Image(painterResource(R.drawable.drone_icon_simple), "drone control")
        }
        IconButton(
            onClick = {
                navController.navigate("drone-shop")
            }
        ) {
            Icon(Icons.Filled.ShoppingBag, "drone shop")
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    droneViewModel: DroneViewModel
) {

    NavHost(
        navController = navController,
        startDestination = AppScreen.Landing.route
    ) {

        composable(AppScreen.Landing.route) {
            LandingScreen(droneViewModel = droneViewModel)
        }

        composable(AppScreen.DroneControl.route) {
            DroneControl(droneViewModel = droneViewModel)
        }

        composable(AppScreen.DroneShop.route) {
            DroneShopScreen(droneViewModel = droneViewModel)
        }
    }
}