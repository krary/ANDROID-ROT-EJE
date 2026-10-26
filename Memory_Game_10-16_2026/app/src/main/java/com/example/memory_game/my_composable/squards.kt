package com.example.memory_game.my_composable

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import com.example.memory_game.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random
data class Param(
    var x : Float,
    var y : Float,
    var vx : Float,
    var vy : Float,
    var color : Color,
    var speed: Float
)
@Composable
fun GameScreen(Click:()->Unit){
    var win by remember{mutableStateOf(false)}
    var param by remember{mutableStateOf(listOf<Param>())}
    var show_squad = remember{mutableStateListOf(true,true,true,true,true,true,true,true,true)}
    var box_position = remember{ mutableStateMapOf<Int, Offset>()}


    val context = LocalContext.current
    val soundPool = remember {
        SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()
    }
    val sonido_squad = remember { soundPool.load(context,R.raw.button_game,1) }
    val win_game = remember { soundPool.load(context,R.raw.win_game,1) }
    val failure_game = remember { soundPool.load(context,R.raw.failure_game,1) }
    DisposableEffect(soundPool) {
        onDispose { soundPool.release() }
    }
    var num_squad by remember{ mutableStateOf(9)}
    var easy by remember{ mutableIntStateOf(3)}
    var cuadrado_activo by remember { mutableIntStateOf(-1) }
    var random_list by remember{ mutableStateOf<List<Int>>(emptyList())}
    var state_scope by remember {mutableStateOf<Boolean>(false)}
    var index_player by remember { mutableStateOf(0) }
    var scope = rememberCoroutineScope()
    var state_player by remember{ mutableStateOf(false)}
    var mensaje by remember{mutableStateOf("Pulse para Reproducir la Sequencia")}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171923))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF171923))
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(cuadrado_activo) {
                if (cuadrado_activo != -1) {
                    soundPool.play(
                        sonido_squad,
                        1f,
                        1f,
                        1,
                        0,
                        1f
                    )
                }
            }
            Text(
                text = "Memoriza la secuencia!!",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
            {
                items(num_squad) { index ->
                    if (show_squad[index]) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                                .onGloballyPositioned { coordenadas ->
                                    val position = coordenadas.positionInRoot()
                                    box_position[index] = position
                                }
                                .clickable {
                                    if (state_player && random_list.isNotEmpty()) {
                                        if (index == random_list[index_player]) {

                                            index_player++
                                            scope.launch {
                                                cuadrado_activo = index
                                                delay(180)
                                                cuadrado_activo = -1


                                                if (index_player == random_list.size) {

                                                    state_player = false
                                                    mensaje = "Has ganado!!"
                                                    soundPool.play(
                                                        win_game,
                                                        1f,
                                                        1f,
                                                        1,
                                                        0,
                                                        1f
                                                    )
                                                     state_scope = true
                                                    //=========================================================
                                                    for (ii in random_list) {
                                                        var posBox =
                                                            box_position[ii] ?: Offset(0f, 0f)
                                                        param = List(50) {
                                                            val XRandom_ =
                                                                Random.nextInt(-12, 12).toFloat()
                                                            val YRandom_ =
                                                                Random.nextInt(-10, -2).toFloat()
                                                            Param(
                                                                x = posBox.x,
                                                                y = posBox.y,
                                                                vx = XRandom_,
                                                                vy = YRandom_,
                                                                speed = 0.6f,
                                                                color = Color(
                                                                    red = Random.nextFloat(),
                                                                    green = Random.nextFloat(),
                                                                    blue = Random.nextFloat(),
                                                                    alpha = 1f
                                                                )
                                                            )
                                                        }


                                                        win = true

                                                        show_squad[ii] = false
                                                        val start_time = System.currentTimeMillis()
                                                        while (System.currentTimeMillis() - start_time < 1000) {
                                                            withFrameNanos { _ ->
                                                                param = param.map { pp ->
                                                                    val nv_y = pp.vy + pp.speed
                                                                    val Nv_y = nv_y + pp.y
                                                                    val nv_x = pp.x + pp.vx
                                                                    pp.copy(
                                                                        x = nv_x,
                                                                        y = Nv_y,
                                                                        vy = nv_y
                                                                    )
                                                                }
                                                            }//AQUI TERMINA EL WITHFRAMENANOS
                                                        }// AQUI TERMINA EL WHILE
                                                        show_squad[ii] = true
                                                        win = false
                                                        delay(500)

                                                    }//AQUI TERMINA EL BUCLE FOR
                                                    state_scope = false


                                                    //========================================================
                                                    easy++
                                                    //num_squad += 3

                                                } else {
                                                    mensaje = "Bien sigue la secuencia"
                                                }

                                            }

                                        } else {
                                            state_player = false
                                            soundPool.play(
                                                failure_game,
                                                1f,
                                                1f,
                                                1,
                                                0,
                                                1f
                                            )
                                            scope.launch {


                                            }//AQUI TERMINA EL SCOPE.LAUNCH
                                        }//AQUI TERMINA EL ELF
                                    }
                                }
                                .background(
                                    color = if (cuadrado_activo == index) {
                                        Color(0xFF55EFC4)
                                    } else {
                                        Color(0xFF353B50)
                                    }, shape = RoundedCornerShape(12.dp)
                                ))//AQUI TERMINA EL BOX
                    }//AQUI TERMINA EL IF
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    scope.launch {


                        show_squad.replaceAll { true }
                        state_player = false
                        state_scope = true
                        index_player = 0
                        random_list = List(easy) { Random.nextInt(9) }
                        for (c in random_list) {
                            cuadrado_activo = c
                            delay(500)
                            cuadrado_activo = -1
                            delay(250)
                        }
                        state_scope = false
                        state_player = true
                    }
                },//AQUI TERMINA EL ONCLICK DE BUTTON
                enabled = !state_scope
            )
            {
                Text(
                    text = if (state_scope) {
                        "memoriza.."
                    } else {
                        "Mostrar Secuencia"
                    }
                )
            }


        }//AQUI TERMINA COLUMN
        if (win) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                param.map { particula ->
                    drawRoundRect(
                        color = particula.color,
                        topLeft = Offset(particula.x, particula.y),
                        size = Size(32f, 32f),
                        cornerRadius = CornerRadius(12f, 12f)
                    )
                }   //AQUI TERMINA EL MAP
            }//AQUI TERMINA EL CANVAS

        }//AQUI TERMINA EL IF
    }//AQUI TERMINA EL BOX
}
