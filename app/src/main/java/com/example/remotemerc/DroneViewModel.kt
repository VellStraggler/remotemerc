package com.example.remotemerc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DroneViewModel: ViewModel() {

    var selectedDroneId by mutableIntStateOf(-1)

    var fakeDroneRepo = FakeDroneRepo()
    var drones: MutableList<Drone> = mutableStateListOf()
    private set

    init {
        fakeDroneRepo.generateDrones(100)
        drones.addAll(fakeDroneRepo.getAll())
    }


    fun getAll() : List<Drone> {
        return drones.toList()
    }
    fun getById(id: Int) : Drone? {
        return fakeDroneRepo.getById(id)
    }
}