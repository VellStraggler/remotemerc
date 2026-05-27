package com.example.remotemerc

import android.util.Log
import com.github.javafaker.Faker
import java.util.Locale
import java.util.Random

class FakePeopleRepo {
    private val rng = kotlin.random.Random(21398)
    private val faker = Faker(Locale.ENGLISH, Random(21398))
    private var idInc = 0
    private val easyCities = mutableListOf<String>()
        .apply {
            repeat(10) { this.add("Atlanta") }
            repeat(3) {this.addAll(listOf(
                "Savannah", "Augusta", "Columbus", "Macon", "Athens"))}
            this.addAll(listOf(
                "Marietta", "Austell"))
        }
    private val fakePeople: MutableList<FakePerson> = mutableListOf()

    fun getAll():List<FakePerson> {
        return fakePeople.toList()
    }
    fun getLast(): FakePerson? {
        if(fakePeople.isEmpty()) {
            return null
        }
        return fakePeople.last()
    }

    fun generatePeople(amt:Int = 1) {
        repeat(amt) {generatePerson()}
    }
    private fun generatePerson() {
        val name = faker.name().fullName()
        val town = easyCities[rng.nextInt(easyCities.size)]
        val bountyBase = rng.nextInt(100)
        val bounty = when(bountyBase) {
            // multiples of 100 only
            in 0..49 -> (190..400).random(rng) * 100
            in 50..79-> (400..900).random(rng) * 100
            in 80..95-> (900..1800).random(rng) * 100
            else -> (1800..50000).random(rng) * 100
        }

        val newPerson = FakePerson(idInc, name, town, bounty)
        fakePeople.add(newPerson)
        idInc++
        Log.d("PERSON",getLast().toString())
    }
}

data class FakePerson(
    val id: Int,
    val fullName: String,
    var location: String,
    var bounty: Int
) {
    override fun toString(): String {
        return "#$id: $fullName of $location worth $$bounty"
    }
}