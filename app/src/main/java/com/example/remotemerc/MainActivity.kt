package com.example.remotemerc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.remotemerc.data.DroneViewModel
import com.example.remotemerc.data.FakeDroneRepo
import com.example.remotemerc.data.FakePeopleRepo
import com.example.remotemerc.data.PlayerViewModel
import com.example.remotemerc.ui.theme.RemoteMercTheme
import com.example.remotemerc.ui.view.AppNavHost
import com.example.remotemerc.ui.view.AppScreen
import com.example.remotemerc.ui.view.MyNavBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val fakeDroneRepo = FakeDroneRepo()
        val fakePeopleRepo= FakePeopleRepo()

        fakeDroneRepo.generateDrones(100)
        fakePeopleRepo.generatePeople(100)

        setContent {
            val droneViewModel by viewModels<DroneViewModel>()
            val playerViewModel by viewModels<PlayerViewModel>()

            RemoteMercTheme {
                val navController = rememberNavController()

                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    if(currentRoute != AppScreen.DroneView.route) {
                        MyNavBar(navController)
                    }
                }) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        AppNavHost(Modifier, navController, droneViewModel, playerViewModel)
                    }
                }
            }
        }
    }
}