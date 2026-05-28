package com.example.remotemerc

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.remotemerc.data.FakePeopleRepo
import com.example.remotemerc.data.FakePerson

class PersonViewModel : ViewModel() {

    private val fakePeopleRepo = FakePeopleRepo()

    var people = mutableStateListOf<FakePerson>()
        private set

    init {
        fakePeopleRepo.generatePeople(25)
        people.addAll(fakePeopleRepo.getAll())
    }

    fun getAllPeople(): List<FakePerson> {
        return people.toList()
    }

    fun getPersonById(id: Int): FakePerson? {
        return people.firstOrNull { person ->
            person.id == id
        }
    }

    fun getLastPerson(): FakePerson? {
        return people.lastOrNull()
    }

    fun generateMorePeople(amount: Int = 1) {
        val currentSize = people.size

        fakePeopleRepo.generatePeople(amount)

        val newPeople = fakePeopleRepo
            .getAll()
            .drop(currentSize)

        people.addAll(newPeople)
    }
}