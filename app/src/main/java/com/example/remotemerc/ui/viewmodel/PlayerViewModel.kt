package com.example.remotemerc.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.remotemerc.data.LaunchedDrone
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

const val SPEED_MULT = 0.02
const val TURN_MUlT = 1f
const val GRAVITY = .005f

const val VERTICAL_ACCEL = .01f
const val MAX_FALL_SPEED = .7f
const val CRASH_HEIGHT = .1f
const val CRASH_SPEED = .3f

class PlayerViewModel : ViewModel() {

    var speed: Double by mutableDoubleStateOf(0.0)
    var forwardInput: Float by mutableFloatStateOf(0f)
    var sideInput: Float by mutableFloatStateOf(0f)
    var sideSpeed: Double by mutableDoubleStateOf(0.0)
    var downInput: Float by mutableFloatStateOf(0f)
    var turnInput: Float by mutableFloatStateOf(0f)
    var pitchInput: Float by mutableFloatStateOf(0f)
    var cameraMode: Boolean by mutableStateOf(false)
    var hoverMode: Boolean by mutableStateOf(false)
    var position = (Position(0f,CRASH_HEIGHT-.01f,0f))
        private set
    var rotation = Rotation(0f,0f,0f)
        private set

    var controlledDrone: LaunchedDrone? = null

    var topSpeed = 1.0
        private set
    var maxHeight = 1f
    var batteryLeft by mutableFloatStateOf(10f)
    var maxBatterySecs by mutableIntStateOf(10)

    var shadowPosition by mutableStateOf(Position(
        position.x,
    0.05f,
    position.y))
        private set

    var verticalVelocity by mutableFloatStateOf(0f)
    private set

    fun setPosition(x:Float, y:Float, z:Float) {
        position = Position(x,y,z)
        shadowPosition = Position(x,0f, z)
    }
    fun reset() {
        position = Position(0f,CRASH_HEIGHT,0f)
        rotation = Rotation(0f,0f,0f)
        verticalVelocity = 0f
    }
    fun initDroneStats(drone: LaunchedDrone) {
        controlledDrone = drone
        topSpeed = drone.topSpeedMph
        maxHeight = drone.maxAltitude.toFloat()
        batteryLeft = drone.batteryLeft
        maxBatterySecs = drone.maxBatterySecs
        position = drone.position
        rotation = drone.rotation
    }

    /** Takes (d)elta in milliseconds. Sets player position, including shadow and camera */
    fun update(d: Float) {
        if (cameraMode) {
            pitchInput = downInput / 5f
            downInput = 0f
        }

        /** in seconds */
        val delta = d / 100000f
        // update rotation from turn input
        if(position.y <= .2) {
            forwardInput =0f
            sideInput =0f
        }

        speed = forwardInput * SPEED_MULT * topSpeed
        sideSpeed= sideInput * SPEED_MULT * topSpeed

        rotation = Rotation(
        rotation.x + (-pitchInput * delta * TURN_MUlT),
        rotation.y + (-turnInput * delta * TURN_MUlT),
        rotation.z
        )

        // recompute direction AFTER rotation update
        val newYaw = rotation.y

        val forwardX = sin(Math.toRadians(newYaw.toDouble())).toFloat()
        val forwardZ = cos(Math.toRadians(newYaw.toDouble())).toFloat()

        val rightZ = -forwardX

        val liftInput = if(hoverMode) {
            0.0f
        } else {
            -downInput
        }
        verticalVelocity = max( -MAX_FALL_SPEED,
            verticalVelocity -
            (GRAVITY * delta) +
            (liftInput * VERTICAL_ACCEL * delta))

        var y1 = min(
            max(position.y + verticalVelocity * delta,CRASH_HEIGHT - .05f),
            maxHeight
        )
        // on ground
        // vertical velocity is still required if we are at crashing speed
        if(y1 < CRASH_HEIGHT && verticalVelocity >= -CRASH_SPEED) {
            verticalVelocity = 0f
        }
        if(hoverMode) {
            verticalVelocity = 0f
            y1 = position.y
        }

        setPosition(
            (position.x +
                    (forwardX * speed * delta) +
                    (forwardZ * sideSpeed * delta)
                    ).toFloat(),
            (y1),
            (position.z +
                    (forwardZ * speed * delta) +
                    (rightZ * sideSpeed * delta)
                    ).toFloat()
        )

        // BATTERY
        if((speed != 0.0 || sideSpeed != 0.0 || liftInput != 0.0f)
            && controlledDrone != null) {
            batteryLeft = controlledDrone!!.useBattery(delta)
        }
    }
}