package com.example.remotemerc.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DroneViewModel : ViewModel() {

    public var selectedDroneId by mutableIntStateOf(-1)

    var cash by mutableDoubleStateOf(190000.0)
        private set

    private val fakeDroneRepo = FakeDroneRepo()

    var shopDrones = mutableStateListOf<Drone>()
        private set

    var myDrones = mutableStateListOf<Drone>()
        private set

    init {
        fakeDroneRepo.generateDrones(100)
        shopDrones.addAll(fakeDroneRepo.getAll())

        val starterDrone = fakeDroneRepo.generateDrone()
        myDrones.add(starterDrone)
    }

    fun getAll(): List<Drone> {
        return shopDrones.toList()
    }

    fun getAllOwned(): List<Drone> {
        return myDrones.toList()
    }

    fun getDroneById(id: Int): Drone? {
        return shopDrones.firstOrNull { drone ->
            drone.id == id
        } ?: myDrones.firstOrNull { drone ->
            drone.id == id
        }
    }

    fun selectDrone(id: Int) {
        selectedDroneId = id
    }

    fun getSelected(): Drone? {
        return getDroneById(selectedDroneId)
    }

    fun removeSelected() {
        myDrones.remove(getSelected())
    }

    fun purchaseById(id: Int) {
        attemptPurchase(id)
    }

    private fun attemptPurchase(id: Int): Boolean {
        val droneToBuy = shopDrones.firstOrNull { drone ->
            drone.id == id
        } ?: return false

        if (droneToBuy.priceUSD > cash) {
            return false
        }

        cash -= droneToBuy.priceUSD
        myDrones.add(droneToBuy)
        shopDrones.remove(droneToBuy)

        return true
    }
}