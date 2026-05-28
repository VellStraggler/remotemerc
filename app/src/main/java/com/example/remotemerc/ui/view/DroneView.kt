package com.example.remotemerc.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.remotemerc.data.GameData
import com.example.remotemerc.data.PlayerViewModel
import com.google.android.filament.Engine
import com.google.android.filament.MaterialInstance
import com.google.android.filament.Texture
import com.google.android.filament.gltfio.FilamentInstance
import io.github.sceneview.SceneView
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.math.Size
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import java.nio.ByteBuffer
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

const val PEOPLE_SCALE = 2f
const val TREE_SCALE = .5f
@Composable
fun GameScreen(
    engine: Engine,
    playerViewModel: PlayerViewModel,
    data: GameData,
    context: Context = LocalContext.current
) {
    Log.d("RECOMP", "GameScreen recomposed")
    val materialLoader = rememberMaterialLoader(engine)
    val modelLoader = rememberModelLoader(engine)

    val cameraNode = rememberCameraNode(engine).apply {
        position = data.camPos
        lookAt(data.lookPos)
    }
    val personBitMap = BitmapFactory.decodeStream(
        context.assets.open("models/lego_dude.png"))
    val buffer1 = ByteBuffer.allocateDirect(personBitMap.byteCount)
    personBitMap.copyPixelsToBuffer(buffer1)
    buffer1.rewind()
    val descriptor1 = Texture.PixelBufferDescriptor(
        buffer1,
        Texture.Format.RGBA,
        Texture.Type.UBYTE,
        1,
        0,
        0,
        personBitMap.width,
        null,
        null
    )
    val personMaterial = remember {
        materialLoader.createImageInstance(Texture.Builder()
            .width(personBitMap.width)
            .height(personBitMap.height)
            .sampler(Texture.Sampler.SAMPLER_2D)
            .format(Texture.InternalFormat.SRGB8_A8)
            .build(engine)
            .apply {
                setImage(engine, 0, descriptor1)
            })
    }

    val grassBitmap = BitmapFactory.decodeStream(
        context.assets.open("models/Grass_02.png"))
    val buffer = ByteBuffer.allocateDirect(grassBitmap.byteCount)
    grassBitmap.copyPixelsToBuffer(buffer)
    buffer.rewind()
    val descriptor = Texture.PixelBufferDescriptor(
        buffer,
        Texture.Format.RGBA,
        Texture.Type.UBYTE,
        1,
        0,
        0,
        grassBitmap.width,
        null,
        null
    )
    val grassMaterial = remember {
        materialLoader.createImageInstance(Texture.Builder()
            .width(grassBitmap.width)
            .height(grassBitmap.height)
            .sampler(Texture.Sampler.SAMPLER_2D)
            .format(Texture.InternalFormat.SRGB8_A8)
            .build(engine)
            .apply {
                setImage(engine, 0, descriptor)
            })
    }
    val greenMaterial = remember {materialLoader.createColorInstance(
        Color.argb(1f,0.2f,0.65f,0.2f)
    ) }

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
        //test people
        data.peoplePositions.forEach {
            val dx = playerViewModel.position.x - it.x
            val dz = playerViewModel.position.z - it.z

            val yaw = Math.toDegrees(
                atan2(dx.toDouble(), dz.toDouble())
            ).toFloat()

            PlaneNode(
//                imageFileLocation = "models/lego_dude.png",
                materialInstance = personMaterial,
                position = Position(it.x,it.y,it.z),
                scale = Scale(PEOPLE_SCALE),
                rotation = Rotation(
                    0f,
                    yaw,
                    0f),

            )
        }
        PlaneNode(
            size = Size(550f,550f),
            position = Position(0f,0f,0f),
            rotation = Rotation(-90f,0f, 0f),
            materialInstance = greenMaterial
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