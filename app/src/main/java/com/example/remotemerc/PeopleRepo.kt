package com.example.remotemerc

import android.util.Log
import com.github.javafaker.Faker
import java.util.Locale
import java.util.Random

class FakePersonRepo {
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
    private val fakeUsers: MutableList<FakeUser> = mutableListOf()

    fun getAll():List<FakeUser> {
        return fakeUsers.toList()
    }
    fun getLast(): FakeUser? {
        if(fakeUsers.isEmpty()) {
            return null
        }
        return fakeUsers.last()
    }

    fun generatePeople(amt:Int = 1) {
        repeat(amt) {generatePerson()}
    }
    private fun generatePerson() {
        val name = faker.name().fullName()
        val town = easyCities[rng.nextInt(easyCities.size)]
        val incomeBase = rng.nextInt(100)
        val income = when(incomeBase) {
            // multiples of 10 only
            in 0..49 -> (1900..4000).random(rng) * 10
            in 50..79-> (4000..9000).random(rng) * 10
            else -> (9000..18000).random(rng) * 10
        }

        val newUser = FakeUser(idInc, name, town, income)
        fakeUsers.add(newUser)
        idInc++
        Log.d("SIZE",getLast().toString())
    }
}

data class FakeUser(
    val id: Int,
    val fullName: String,
    var location: String,
    var bounty: Int
) {
    override fun toString(): String {
        return "#$id: $fullName of $location worth $$bounty"
    }
}