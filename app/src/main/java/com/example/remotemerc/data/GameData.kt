package com.example.remotemerc.data

import com.example.remotemerc.ui.view.PEOPLE_SCALE
import io.github.sceneview.math.Position
import io.github.sceneview.model.ModelInstance
import kotlin.random.Random

class GameData(playerViewModel: PlayerViewModel) {
    var treePositions: MutableList<Position> = mutableListOf()
    var treeInstances: MutableList<ModelInstance> = mutableListOf()

    var peoplePositions: MutableList<Position> = mutableListOf()

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
        repeat(100) {
            val ranX = random.nextInt(-40,40).toFloat()
            val ranZ = random.nextInt(-40,40).toFloat()

            val chunkX = random.nextInt(-5,5)
            val chunkZ = random.nextInt(-5,5)

            val position = Position(ranX + (chunkX * 55f), PEOPLE_SCALE/3.0f, ranZ + (chunkZ * 55f))
            peoplePositions.add(position)
        }
    }
}