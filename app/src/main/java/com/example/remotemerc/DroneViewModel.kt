package com.example.remotemerc

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class DroneViewModel: ViewModel() {

    var drones = mutableStateListOf<Drone>()
    private set

    fun getAll() : List<Drone> {
        return drones.toList()
    }
}