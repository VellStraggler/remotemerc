package com.example.remotemerc.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.remotemerc.ui.view.PERSON_HEIGHT
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import kotlin.math.min
import kotlin.random.Random

class GameDataViewModel : ViewModel() {
    var treePositions: MutableList<Position> = mutableStateListOf()

    var people: MutableList<GamePerson> = mutableStateListOf()

    var random = Random(101)

    init {
        repeat(20) {
            val position = Position(random.nextInt(-200,200).toFloat(),
                0f,
                random.nextInt(-200,200).toFloat())
            treePositions.add(position)
        }
        repeat(10) {
            val position = Position(random.nextInt(-200,200).toFloat(),
                PERSON_HEIGHT/2f,
                random.nextInt(-200,200).toFloat())
            people.add(GamePerson(
                "",
                true,
                position,
                Rotation()
            ))
        }
    }

    fun killAt(i: Int) {
        people[i].alive = false
    }

    fun initGamePeopleNames(peopleViewModel: PeopleViewModel) {
        var i = 0
        while (i < min(peopleViewModel.myPeople.size, people.size)) {
            people[i].name = peopleViewModel.myPeople[i].fullName
            i++
        }
    }
}

data class GamePerson(
    var name: String,
    var alive: Boolean,
    var position: Position,
    val rotation: Rotation
)