package com.example.remotemerc.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PeopleViewModel : ViewModel() {

    var selectedPersonId by mutableIntStateOf(1)
        private set

    private val fakePeopleRepo = FakePeopleRepo()
    var fakePeople = mutableStateListOf<FakePerson>()
        private set

    var myPeople = mutableStateListOf<FakePerson>()
        private set

    var availablePeople = mutableStateListOf<FakePerson>()
        private set

    init {
        fakePeopleRepo.generatePeople(25)
        fakePeople.addAll(fakePeopleRepo.getAll())
        val shuffledPeople = fakePeople.shuffled()
        myPeople.addAll(shuffledPeople.take(5))
        availablePeople.addAll(shuffledPeople.drop(5).take(5))

    }

    fun selectPerson(id: Int) {
        selectedPersonId = id
    }

    fun getSelectedPerson(): FakePerson {
        return getPersonById(selectedPersonId)
    }

    fun getPersonById(id: Int): FakePerson {
        return fakePeople.first { person ->
            person.id == id
        }
    }

    fun getAllPeople(): List<FakePerson> {
        return fakePeople.toList()
    }

    fun getMyPeople(): List<FakePerson> {
        return myPeople.toList()
    }

    fun getAvailablePeople(): List<FakePerson> {
        return availablePeople.toList()
    }

    fun acceptPerson(person: FakePerson) {
        availablePeople.remove(person)
        myPeople.add(person)
    }
}