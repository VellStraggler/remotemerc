package com.example.remotemerc.data

import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation

class LaunchedDrone(
    override val id: Int = 1000000,
    override val model: String = "DroneModel",
    override val topSpeedMph: Double = 60.0,
    override val maxAltitude: Int = 6000,
    override val batteryLifeSeconds: Int = 6000,
) : IDrone {
    var batteryLeft = batteryLifeSeconds.toFloat()
        private set

    var position = Position(0f,0.1f,0f)
    var rotation = Rotation()

    fun useBattery(delta:Float): Float {
        batteryLeft -= delta
        return batteryLeft
    }
}