package com.example.remotemerc.ui.view

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
import com.example.remotemerc.data.GameData
import com.example.remotemerc.data.PlayerViewModel
import com.google.android.filament.Engine
import com.google.android.filament.gltfio.FilamentInstance
import io.github.sceneview.SceneView
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.math.Size
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

const val PEOPLE_SCALE = .005f
const val TREE_SCALE = .5f
@Composable
fun GameScreen(
    engine: Engine,
    playerViewModel: PlayerViewModel,
    data: GameData
) {
    Log.d("RECOMP", "GameScreen recomposed")
    val materialLoader = rememberMaterialLoader(engine)
    val modelLoader = rememberModelLoader(engine)

    val cameraNode = rememberCameraNode(engine).apply {
        position = data.camPos
        lookAt(data.lookPos)
    }
    val greenMaterial = remember {
        materialLoader.createColorInstance(
            Color.GREEN
        )
    }

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

            data.camPos = playerViewModel.position
            data.lookPos = lookPos

            cameraNode.position = data.camPos
            cameraNode.lookAt(data.lookPos)
        }
    ) {
        // test planes
        for (x in -4..6) {
            for (z in -4..6) {
                PlaneNode(
                    size = Size(100.0f, 100.0f),
                    position = Position(x = -105.0f + (x*105f), y = -0.1f, z = -105.0f + (z*105f)),
                    rotation = Rotation(-90f,0f, 0f),
                    materialInstance = greenMaterial
                )
            }
        }

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