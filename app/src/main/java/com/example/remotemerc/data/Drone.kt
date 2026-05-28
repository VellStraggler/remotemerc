package com.example.remotemerc.data

data class Drone(
    val id: Int = 1000000,
    val model: String = "DroneModel",
    val priceUSD: Double = 999.0,
    val weightOz: Double = 1.0,
    val topSpeedMph: Double = 60.0,
    val maxAltitude: Int = 6000,
    val batteryLifeSeconds: Int = 6000,
    val tntGrams: Double = 1.0,
    val dimensionsInches: List<Double> = listOf(6.0,2.0,6.0)
)