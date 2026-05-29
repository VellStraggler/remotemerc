package com.example.remotemerc.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    var name by mutableStateOf("Mark")
        private set

    var score by mutableIntStateOf(900)
        private set

    var cash by mutableIntStateOf(190000)
        private set

    fun addScore(amount: Int) {
        score += amount
    }

    fun addCash(amount: Int) {
        cash += amount
    }

    fun spendCash(amount: Int) {
        cash -= amount
    }

    fun updateName(newName: String) {
        name = newName
    }
}