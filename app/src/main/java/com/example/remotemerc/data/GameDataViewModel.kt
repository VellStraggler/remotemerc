package com.example.remotemerc.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.remotemerc.ui.view.PERSON_HEIGHT
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import kotlin.random.Random

class GameDataViewModel : ViewModel() {
    var treePositions: MutableList<Position> = mutableStateListOf()

    var people: MutableList<PersonOrientation> = mutableStateListOf()

    var random = Random(101)

    init {
        repeat(20) {
            val position = Position(random.nextInt(-200,200).toFloat(),
                0.1f,
                random.nextInt(-200,200).toFloat())
            treePositions.add(position)
        }
        repeat(10) {
            val position = Position(random.nextInt(-200,200).toFloat(),
                PERSON_HEIGHT/2f,
                random.nextInt(-200,200).toFloat())
            people.add(PersonOrientation(position, Rotation()))
        }
    }
}

data class PersonOrientation(
    val position: Position,
    val rotation: Rotation
)