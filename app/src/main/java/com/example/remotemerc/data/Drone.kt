package com.example.remotemerc.data

open class Drone(
    override val id: Int = 1000000,
    override val model: String = "DroneModel",
    val priceUSD: Double = 999.0,
    val weightOz: Double = 1.0,
    override val topSpeedMph: Double = 60.0,
    override val maxAltitude: Int = 6000,
    override val maxBatterySecs: Int = 6000,
    val tntGrams: Double = 1.0,
    val dimensionsInches: List<Double> = listOf(6.0,2.0,6.0)
) : IDrone{
    fun launched(): LaunchedDrone {
        return LaunchedDrone(
            id,
            model,
            topSpeedMph,
            maxAltitude,
            maxBatterySecs
        )
    }
}