package com.example.remotemerc.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.remotemerc.ui.view.DroneSortAttribute
import com.example.remotemerc.ui.view.SortDirection

class DroneViewModel : ViewModel() {

    var selectedDroneId by mutableIntStateOf(-1)

    var cash by mutableDoubleStateOf(900.0)
        private set

    var selectedSortAttribute by mutableStateOf(DroneSortAttribute.PRICE)
        private set

    var selectedSortDirection by mutableStateOf(SortDirection.ASCENDING)
        private set

    private val fakeDroneRepo = FakeDroneRepo()

    var shopDrones = mutableStateListOf<Drone>()
        private set

    var myDrones = mutableStateListOf<LaunchedDrone>()
        private set

    init {
        fakeDroneRepo.generateDrones(100)
        shopDrones.addAll(fakeDroneRepo.getAll())

        val starterDrone = fakeDroneRepo.generateDrone()
        myDrones.add(starterDrone.launched())
    }

    fun getAll(): List<Drone> {
        return shopDrones.toList()
    }

    fun getSortedShopDrones(): List<Drone> {
        val sortedDrones = when (selectedSortAttribute) {
            DroneSortAttribute.NAME -> shopDrones.sortedBy { drone ->
                drone.model
            }

            DroneSortAttribute.PRICE -> shopDrones.sortedBy { drone ->
                drone.priceUSD
            }

            DroneSortAttribute.WEIGHT -> shopDrones.sortedBy { drone ->
                drone.weightOz
            }

            DroneSortAttribute.SPEED -> shopDrones.sortedBy { drone ->
                drone.topSpeedMph
            }

            DroneSortAttribute.ALTITUDE -> shopDrones.sortedBy { drone ->
                drone.maxAltitude
            }

            DroneSortAttribute.BATTERY -> shopDrones.sortedBy { drone ->
                drone.batteryLifeSeconds
            }

            DroneSortAttribute.TNT -> shopDrones.sortedBy { drone ->
                drone.tntGrams
            }
        }

        return when (selectedSortDirection) {
            SortDirection.ASCENDING -> sortedDrones
            SortDirection.DESCENDING -> sortedDrones.reversed()
        }
    }

    fun updateSortAttribute(attribute: DroneSortAttribute) {
        selectedSortAttribute = attribute
    }

    fun updateSortDirection(direction: SortDirection) {
        selectedSortDirection = direction
    }

    fun getAllOwned(): List<LaunchedDrone> {
        return myDrones.toList()
    }

    fun getDroneById(id: Int): IDrone? {
        return shopDrones.firstOrNull { drone ->
            drone.id == id
        } ?: myDrones.firstOrNull { drone ->
            drone.id == id
        }
    }

    fun selectDrone(id: Int) {
        selectedDroneId = id
    }

    fun getSelected(): IDrone? {
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
        myDrones.add(droneToBuy.launched())
        shopDrones.remove(droneToBuy)

        return true
    }
}