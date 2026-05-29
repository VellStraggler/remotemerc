package com.example.remotemerc.ui.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.remotemerc.data.GameDataViewModel
import com.example.remotemerc.data.PersonOrientation
import com.example.remotemerc.data.PlayerViewModel
import com.google.android.filament.Engine
import com.google.android.filament.Material
import com.google.android.filament.gltfio.FilamentInstance
import io.github.sceneview.SceneView
import io.github.sceneview.geometries.Plane
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.math.Size
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

const val PERSON_HEIGHT = 2f
const val TREE_SCALE = .5f
@Composable
fun GameScreen(
    engine: Engine,
    playerViewModel: PlayerViewModel,
    data: GameDataViewModel,
    explode: () -> Unit,
    context: Context = LocalContext.current
) {
    Log.d("RECOMP", "GameScreen recomposed")
    val materialLoader = rememberMaterialLoader(engine)
    val modelLoader = rememberModelLoader(engine)

    val cameraNode = rememberCameraNode(engine).apply {
        position = playerViewModel.camPos
        lookAt(playerViewModel.position)
    }
    val greenMaterial = remember {materialLoader.createColorInstance(
        Color.argb(1f,0.15f,0.6f,0.15f)
    ) }
    val skyMaterial = remember {materialLoader.createUnlitColorInstance(
        Color.argb(1f, 0.2f, 0.4f, 1f)
    )}
    skyMaterial.cullingMode = Material.CullingMode.NONE

    var treeInstances by remember {
        mutableStateOf<List<FilamentInstance>>(emptyList())
    }

    LaunchedEffect(Unit) {
        treeInstances = modelLoader.loadInstancedModel(
            "models/tree_low_poly.glb",
            20
        )
    }


    SceneView(modifier = Modifier.fillMaxSize(),
        engine = engine,
        cameraNode = cameraNode,
        cameraManipulator = null,
        // built-in frame-perfect function
        onFrame = { frameTimeNanos ->
            playerViewModel.update(frameTimeNanos/ 1_000_000_000f)

            val baseYaw = Math.toRadians(playerViewModel.rotation.y.toDouble())

            val forwardX = sin(baseYaw).toFloat()
            val forwardZ = cos(baseYaw).toFloat()

            val lookPos = Position(
                playerViewModel.position.x + forwardX,
                playerViewModel.position.y,
                playerViewModel.position.z + forwardZ
            )
            playerViewModel.camPos = lookPos

            cameraNode.position = playerViewModel.camPos
            cameraNode.lookAt(playerViewModel.position)


            // collision and billboard logic
            val cpos = cameraNode.position
            var i = 0
            while(i <data.people.size) {
                val person = data.people[i]
                val ppos = person.position

                val dx = playerViewModel.position.x - ppos.x
                val dz = playerViewModel.position.z - ppos.z

                val yaw = Math.toDegrees(
                    atan2(dx.toDouble(), dz.toDouble())
                ).toFloat()
                val newRot = Rotation(0f, yaw, 0f)
                // replace person so UI updates
                data.people[i] = PersonOrientation(person.position, newRot)

                val d = sqrt(
                    (ppos.x - cpos.x) * (ppos.x - cpos.x) +
                        (ppos.z - cpos.z) * (ppos.z - cpos.z)
                )
                if (d < 1f && cpos.y < PERSON_HEIGHT + .1f) {
                    Log.d("BOOM", "destroyed a guy")
                    data.people.removeAt(i)
                    explode()
                } else {
                    i++
                }
            }
            if (cpos.y < 0.1f){// && playerViewModel.speed > 1.0f) {
                Log.d("BOOM", "you crashed")
                explode()
            }


        }
    ) {
        //test people
        data.people.forEach {
            ImageNode(
                imageFileLocation = "models/lego_dude.png",
                position = it.position,
                size = Size(PERSON_HEIGHT, PERSON_HEIGHT, 0f),
                rotation = it.rotation,
                center = Plane.DEFAULT_CENTER,
            )
        }

        PlaneNode(
            size = Size(550f,550f),
            position = Position(0f,0f,0f),
            rotation = Rotation(-90f,0f, 0f),
            materialInstance = greenMaterial
        )
        CubeNode(
            size = Size(600f),
            position = Position(0f,0f,0f),
            rotation = Rotation(90f,0f, 0f),
            materialInstance = skyMaterial
        )

        //test trees
        treeInstances.forEachIndexed { index, instance ->
            ModelNode(
                modelInstance = instance,
                position = data.treePositions[index],
                scale = Scale(TREE_SCALE)
            )
        }
    }
}