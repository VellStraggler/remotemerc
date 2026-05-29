package com.example.remotemerc.ui.view

enum class DroneSortAttribute(
    val label: String
) {
    NAME("Name"),
    PRICE("Price"),
    WEIGHT("Weight"),
    SPEED("Top Speed"),
    ALTITUDE("Max Altitude"),
    BATTERY("Battery Life"),
    TNT("TNT Grams")
}

enum class SortDirection(
    val label: String
) {
    ASCENDING("Ascending"),
    DESCENDING("Descending")
}