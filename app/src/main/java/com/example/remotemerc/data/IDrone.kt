package com.example.remotemerc.data

interface IDrone {
    val id: Int
    val model: String
    val topSpeedMph: Double
    val maxAltitude: Int
    val batteryLifeSeconds: Number
}