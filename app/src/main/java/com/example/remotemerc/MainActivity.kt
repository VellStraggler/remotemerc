package com.example.remotemerc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DroneControl(Modifier.padding(innerPadding),
                        droneViewModel)
                }
            }
        }
    }
}