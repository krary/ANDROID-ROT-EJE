
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

// 1. La estructura de datos de cada trocito (partícula)
data class Particle(
    var x: Float,
    var y: Float,
    var vx: Float,      // Velocidad horizontal
    var vy: Float,      // Velocidad vertical
    val color: Color,
    val size: Float
)

@Composable
fun ShatterDemoScreen() {
    val scope = rememberCoroutineScope()
    var isBroken by remember { mutableStateOf(false) }
    var particles by remember { mutableStateOf(listOf<Particle>())}
    LaunchedEffect(isBroken){
        delay(2000)
        isBroken = false
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (!isBroken) {
            // El cuadrado original antes de romperse
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        isBroken = true
                        // Al hacer clic, generamos 15 partículas en el centro
                        particles = List(15) {
                            Particle(
                                x = Random.nextFloat() * 100f - 50f, // Desplazamiento inicial aleatorio
                                y = Random.nextFloat() * 100f - 50f,
                                vx = Random.nextFloat() * 20f - 10f, // Hacia los lados
                                vy = Random.nextFloat() * -15f - 5f, // Impulso inicial hacia arriba
                                color = Color(0xFF55EFC4),
                                size = Random.nextFloat() * 20f + 10f
                            )
                        }

                        // Bucle de físicas fotograma a fotograma
                        scope.launch {
                            val gravity = 1.5f // Fuerza de la gravedad
                            while (true) {
                                withFrameNanos { _ ->
                                    particles = particles.map { p ->
                                        p.copy(
                                            x = p.x + p.vx,
                                            y = p.y + p.vy,
                                            vy = p.vy + gravity // La gravedad acelera la caída
                                        )
                                    }
                                }
                            }

                        }
                    }
                    .background(Color(0xFF353B50))
            )
        }

        // El Canvas que dibuja las partículas volando cuando se rompe
        if (isBroken) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2f
                val centerY = size.height / 2f

                particles.forEach { p ->
                    drawRect(
                        color = p.color,
                        topLeft = Offset(centerX + p.x, centerY + p.y),
                        size = Size(p.size, p.size)
                    )
                }
            }
        }
    }
}