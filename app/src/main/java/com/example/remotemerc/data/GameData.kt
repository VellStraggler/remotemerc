package com.example.remotemerc.data

import com.example.remotemerc.ui.view.PERSON_HEIGHT
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import kotlin.random.Random

class GameData(playerViewModel: PlayerViewModel) {
    var treePositions: MutableList<Position> = mutableListOf()
    var grassPositions: MutableList<Position> = mutableListOf()

    var people: MutableList<PersonOrientation> = mutableListOf()

    var random = Random(101)

    var lookPos = Position(playerViewModel.position.x,
        playerViewModel.position.y + 1.5f,
        playerViewModel.position.z)

    var camPos = Position(playerViewModel.position.x,
        playerViewModel.position.y + 1f,
        playerViewModel.position.z - 15f)

    init {
        repeat(20) {
            val ranX = random.nextInt(-40,40).toFloat()
            val ranZ = random.nextInt(-40,40).toFloat()

            val chunkX = random.nextInt(-5,5)
            val chunkZ = random.nextInt(-5,5)

            val position = Position(ranX + (chunkX * 55f), 0f, ranZ + (chunkZ * 55f))
            treePositions.add(position)
        }
        repeat(10){
            val position = Position(random.nextInt(-200,200).toFloat(),
                0.1f,
                random.nextInt(-200,200).toFloat())
            grassPositions.add(position)
        }
        repeat(10) {
            val ranX = random.nextInt(-40,40).toFloat()
            val ranZ = random.nextInt(-40,40).toFloat()

            val chunkX = random.nextInt(-5,5)
            val chunkZ = random.nextInt(-5,5)

            val position = Position(
                ranX + (chunkX * 55f),
                PERSON_HEIGHT/2f,
                ranZ + (chunkZ * 55f))
            people.add(PersonOrientation(position, Rotation()))
        }
    }
}

data class PersonOrientation(
    val position: Position,
    var rotation: Rotation
)