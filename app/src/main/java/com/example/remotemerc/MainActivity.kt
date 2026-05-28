package com.example.remotemerc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.compose.rememberNavController
import com.example.remotemerc.presentation.ui.DroneShopScreen
import com.example.remotemerc.ui.theme.RemoteMercTheme

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

            RemoteMercTheme {
                val navController = rememberNavController()


                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { MyNavBar(navController) }) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        AppNavHost(Modifier, navController, droneViewModel)
                    }
                }
            }
        }
    }
}