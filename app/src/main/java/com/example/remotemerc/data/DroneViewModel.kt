package com.example.remotemerc.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DroneViewModel : ViewModel() {

    public var selectedDroneId by mutableIntStateOf(-1)

    var selectedPersonId by mutableIntStateOf(-1)
        private set

    var cash by mutableDoubleStateOf(190000.0)
        private set

    private val fakeDroneRepo = FakeDroneRepo()
    private val fakePeopleRepo = FakePeopleRepo()

    var shopDrones = mutableStateListOf<Drone>()
        private set

    var myDrones = mutableStateListOf<Drone>()
        private set

    var fakePeople = mutableStateListOf<FakePerson>()
        private set

    init {
        fakeDroneRepo.generateDrones(100)
        shopDrones.addAll(fakeDroneRepo.getAll())

        val starterDrone = fakeDroneRepo.generateDrone()
        myDrones.add(starterDrone)

        fakePeopleRepo.generatePeople(25)
        fakePeople.addAll(fakePeopleRepo.getAll())
    }

    fun getAll(): List<Drone> {
        return shopDrones.toList()
    }

    fun getAllOwned(): List<Drone> {
        return myDrones.toList()
    }

    fun getAllPeople(): List<FakePerson> {
        return fakePeople.toList()
    }

    fun getDroneById(id: Int): Drone? {
        return shopDrones.firstOrNull { drone ->
            drone.id == id
        } ?: myDrones.firstOrNull { drone ->
            drone.id == id
        }
    }

    fun getPersonById(id: Int): FakePerson? {
        return fakePeople.firstOrNull { person ->
            person.id == id
        }
    }

    fun selectDrone(id: Int) {
        selectedDroneId = id
    }

    fun selectPerson(id: Int) {
        selectedPersonId = id
    }

    fun getSelected(): Drone? {
        return getDroneById(selectedDroneId)
    }

    fun getSelectedPerson(): FakePerson? {
        return getPersonById(selectedPersonId)
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