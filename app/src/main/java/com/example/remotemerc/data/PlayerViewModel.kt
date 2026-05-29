package com.example.remotemerc.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation

const val SPEED_MULT = 0.0002
const val TURN_MUlT = 0.02f
const val HEIGHT_MULT = 0.0008f

class PlayerViewModel : ViewModel() {

    var speed: Double by mutableDoubleStateOf(0.0)
    var forwardInput: Float by mutableFloatStateOf(0f)
    var sideInput: Float by mutableFloatStateOf(0f)
    var sideSpeed: Double by mutableDoubleStateOf(0.0)
    var upAmt: Float by mutableFloatStateOf(0f)
    var turnInput: Float by mutableFloatStateOf(0f)
    var pitchInput: Float by mutableFloatStateOf(0f)
    var position = (Position(0f,0.1f,0f))
        private set
    var rotation = Rotation(0f,0f,0f)
        private set

    // should be placed slightly behind the player position
    var camPos = Position(position.x,
        position.y + 1.0f,
        position.z + 1.0f)

    fun reset() {
        position = Position(0f,0.1f,0f)
        rotation = Rotation(0f,0f,0f)
    }

    fun update(d: Float) {
        val delta = d / 1000f
        // update rotation from turn input
        speed = (forwardInput * delta) * SPEED_MULT
        sideSpeed= (sideInput * delta) * SPEED_MULT

        rotation = Rotation(
        rotation.x + (pitchInput * delta * TURN_MUlT),
        rotation.y + (-turnInput * delta * TURN_MUlT),
        rotation.z
        )

        // recompute direction AFTER rotation update
        val newYaw = rotation.y

        val dirX = kotlin.math.sin(Math.toRadians(newYaw.toDouble())).toFloat()
        val dirZ = kotlin.math.cos(Math.toRadians(newYaw.toDouble())).toFloat()

        position = Position(
            (position.x +
                    (dirX * speed * delta) +
                    (dirZ * sideSpeed * delta)
                    ).toFloat(),
            (position.y + upAmt * delta * HEIGHT_MULT),
            (position.z +
                    (dirZ * speed * delta) +
                    (dirX * sideSpeed * delta)
                    ).toFloat()
        )
    }
}