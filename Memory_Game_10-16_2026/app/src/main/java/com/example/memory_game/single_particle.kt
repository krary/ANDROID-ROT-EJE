    import android.R.attr.radius
    import android.widget.Toast
    import androidx.compose.foundation.Canvas
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.offset
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.runtime.withFrameNanos
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.geometry.CornerRadius
    import androidx.compose.ui.geometry.Offset
    import androidx.compose.ui.geometry.Size
    import androidx.compose.ui.graphics.Canvas
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.drawscope.draw
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import kotlinx.coroutines.delay
    import kotlin.collections.emptySet
    import kotlin.random.Random
    
    data class Param(
        var x: Float,
        var y: Float,
        var vy: Float,
        var vx: Float,
        var speed: Float,
        var color: Color
    )
    
    data class ParamBox(
        var x: Float,
        var y: Float
    )
    
    
    @Composable
    fun ligic_mecanic(onClick: () -> Unit) {
    
    }
    
    @Composable
    fun single() {
    
        val context = LocalContext.current
        var p by remember { mutableStateOf(listOf<Param>()) }
        var estado by remember { mutableStateOf(true) }
        var p_box by remember { mutableStateOf(ParamBox(x = 50f, y = 250f)) }
    
        LaunchedEffect(estado == false) {
            while (true) {
                withFrameNanos {
                    _->
                    p = p.map { pp->
                        var newVX = pp.vx + pp.x
                        var newVY = pp.vy + pp.speed
                        var ny = newVY + pp.y
                        pp.copy(
                            y = ny,
                            vy = newVY,
                            x = newVX
                        )
                    }
    
                }//AQUI TERMINA EL WITHFRAMENANOS
    
            }//AQUI TERMINA EL WHILE
    
    
        }//AQUI TERMINA EL LAUNCHEDEFFECT
    
    
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            if (estado) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .offset(p_box.x.dp, p_box.y.dp)
                        .clip(RoundedCornerShape(23.dp))
                        .background(Color.Yellow)
                        .clickable {
                            p = List(20){
                                val randomX = Random.nextInt(-15, 15).toFloat()
                                val randomY = Random.nextInt(-14, -4).toFloat()
    
                                Param(
                                    x = p_box.x + 70 / 2,
                                    y = p_box.y + 70 / 2,
                                    vy = randomY,
                                    vx = randomX,
                                    speed = 0.6f,
                                    color = Color(
                                        red = Random.nextFloat(),
                                        green = Random.nextFloat(),
                                        blue = Random.nextFloat(),
                                        alpha = 1f
                                    )
                                    //color = Color(0xFF353B50)
                                )
                            }
                            estado = false
    
    
    
    
    
                        }//AQUI TERMINA EL CLICKABLE
                )//AQUI TERMINA EL BOX
            }//AQUI TERMINA EL IF
            if (estado == false) {
                p?.let { particula ->
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                       p.map { pp->
                           drawRoundRect(
                               color = pp.color,
                               topLeft = Offset(pp.x,pp.y),
                               size = Size(32f,32f),
                               cornerRadius = CornerRadius(12f,12f)
    
                           )
                       }
                    }//AQUI TERMINA EL CANVAS
                }
            }//AQUI TERMINA EL IF
        }
    }//AQUI TERMINA EL COMPOSABLE
