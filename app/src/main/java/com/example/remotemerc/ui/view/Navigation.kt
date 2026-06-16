package com.example.remotemerc.ui.view

import android.util.Log
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
import com.example.remotemerc.ui.viewmodel.DroneViewModel
import com.example.remotemerc.R
import com.example.remotemerc.ui.viewmodel.GameDataViewModel
import com.example.remotemerc.ui.viewmodel.PeopleViewModel
import com.example.remotemerc.ui.viewmodel.PlayerViewModel
import com.example.remotemerc.data.LaunchedDrone
import com.example.remotemerc.ui.viewmodel.ProfileViewModel

sealed class AppScreen(val route: String) {
    data object Landing : AppScreen("landing-page")
    data object DroneControl: AppScreen("drone-control")
    data object DroneShop: AppScreen("drone-shop")
    data object DroneView: AppScreen("drone-view")
    data object MissionView: AppScreen("mission-view")
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
    navController: NavHostController = rememberNavController(),
    droneViewModel: DroneViewModel,
    playerViewModel: PlayerViewModel,
    peopleViewModel: PeopleViewModel,
    gameDataViewModel: GameDataViewModel,
    profileViewModel: ProfileViewModel
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Landing.route
    ) {

        composable(AppScreen.Landing.route) {
            LandingPage(
                profileViewModel = profileViewModel,
                peopleViewModel = peopleViewModel,
                onClickMission = {
                    navController.navigate(AppScreen.MissionView.route)
                }
            )
        }

        composable(AppScreen.DroneControl.route) {
            DroneFleetControl(droneViewModel = droneViewModel, navController = navController)
        }

        composable(AppScreen.DroneShop.route) {
            DroneShopScreen(droneViewModel = droneViewModel, profileViewModel = profileViewModel)
        }

        composable(AppScreen.DroneView.route) {
            val explode = {
                // explode
                Log.d("DEAD", "target eliminated")
                playerViewModel.reset()
                droneViewModel.removeSelected()
                navController.navigate("drone-control")
            }

            DroneView(
                { droneViewModel.getSelected() as LaunchedDrone? },
                {
                    droneViewModel.selectedDroneId = -1
                    navController.navigate("drone-control")
                },
                playerViewModel,
                gameDataViewModel,
                peopleViewModel,
                explode,
            )
        }

        composable(AppScreen.MissionView.route) {
                MissionProfilePage(peopleViewModel)
        }
    }
}