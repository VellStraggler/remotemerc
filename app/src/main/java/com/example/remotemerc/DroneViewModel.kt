package com.example.remotemerc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DroneViewModel: ViewModel() {

    var selectedDroneId by mutableIntStateOf(-1)

    var cash by mutableDoubleStateOf(190000.0)

    var fakeDroneRepo = FakeDroneRepo()
    var shopDrones: MutableList<Drone> = mutableStateListOf()
    private set

    var myDrones: MutableList<Drone> = mutableStateListOf(fakeDroneRepo.generateDrone())
    private set

    init {
        fakeDroneRepo.generateDrones(100)
        shopDrones.addAll(fakeDroneRepo.getAll())
    }


    fun getAll() : List<Drone> {
        return shopDrones.toList()
    }
    fun getAllOwned(): List<Drone> {
        return myDrones.toList()
    }
    fun getById(id: Int) : Drone? {
        return fakeDroneRepo.getById(id)
    }

    fun purchaseById(id: Int) {
        val response = attemptPurchase(id)
    }

    private fun attemptPurchase(id: Int): Boolean {
        shopDrones.forEach {
            if(it.id == id) {
                if(it.priceUSD <= cash) {
                    cash -= it.priceUSD
                    myDrones.add(it)
                    shopDrones.remove(it)
                    return true
                }
            }
        }
        return false
    }

}