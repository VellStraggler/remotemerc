package com.example.remotemerc.data

import android.util.Log
import kotlin.math.pow
import kotlin.random.Random

class FakeDroneRepo {
    private val rng = Random(21398)
    private var idInc = 0

    private val droneBuzzWords = listOf(
        "Drone", "Mark", "Boom", "Strike", "Nova", "Kamikaze",
        "Crest", "Quad", "Stealth", "Falcon", "Viper", "Raven",
        "Ghost", "Nova", "Wasp", "Scout", "Vector", "Hawk",
        "Phantom", "Strike", "Blade", "Pulse", "Orbit", "Echo",
        "Shadow", "Circuit", "Helix", "Core", "Sky", "Wing",
        "Jet", "Storm", "Apex", "Hunter", "Specter", "Zero",
        "Bolt", "Cyber", "Mantis", "Wolf", "Fury", "Raptor",
        "Signal", "Zenith", "Comet", "Glide", "Rogue", "Fusion",
        "Sentinel", "Hornet", "Drift", "Pixel", "Forge", "Cobra"
    )

    var fakeDrones: MutableList<Drone> = mutableListOf()
    private set

    fun getAll(): List<Drone> {
        return fakeDrones.toList()
    }
    fun getById(id: Int): Drone? {
        fakeDrones.forEach {
            if(it.id == id) {
                return it
            }
        }
        return null
    }
    fun getLast(): Drone? {
        if (fakeDrones.isEmpty()) {
            return null
        }
        return fakeDrones.last()
    }
    fun generateDrones(amt: Int = 1): List<Drone> {
        repeat(amt) { generateDrone() }
        return getAll()
    }

    fun generateDrone(): Drone {

        val partCount = rng.nextInt(2, 5)

        val parts = mutableListOf<String>()

        repeat(partCount) {

            when (rng.nextInt(3)) {

                // random capital letter
                0 -> {
                    val letter = ('A'..'Z').random(rng)
                    parts.add(letter.toString())
                }

                // cool drone word
                1 -> {
                    parts.add(droneBuzzWords.random(rng))
                }

                // number 1..9999
                2 -> {
                    parts.add(rng.nextInt(1, 10000).toString())
                }
            }
        }

        parts.shuffle(rng)

        val model = parts.joinToString("-")

        val batteryLifeSeconds =    rng.nextInt(1, 120) * 10
        val topSpeedMph =           rng.nextInt(100,3000) / 10.0
        val maxAltitude =           rng.nextInt(1, 100) * 10
        val weightOz =              rng.nextInt(10, 10000) / 10.0 + (batteryLifeSeconds /100.0)
        val tntGrams =              rng.nextInt(5, (weightOz*5).toInt()-4) / 10.0
        val dim = (weightOz.pow(.33) * 10).toInt() / 10.0
        val dimensionsInches = listOf(dim, ((dim*2.5).toInt()/10.0), dim)

        val priceUSD = ((
                (batteryLifeSeconds*batteryLifeSeconds/50) +
                (topSpeedMph*topSpeedMph) +
                (maxAltitude/5) +
                (0.1/weightOz) +
                (tntGrams*20) +
                (0.1/dim)
                ) * 20 ).toInt() / 100.0

        val newDrone = Drone(
            idInc, model, priceUSD, weightOz, topSpeedMph,
            maxAltitude, batteryLifeSeconds, tntGrams, dimensionsInches
        )

        fakeDrones.add(newDrone)
        idInc++
        Log.d("DRONE", getLast().toString())
        return newDrone
    }
}