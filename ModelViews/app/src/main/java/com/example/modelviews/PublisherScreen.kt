import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.modelviews.LocationPublisher
import com.google.accompanist.permissions.*

// Define la UI que controla el envío de ubicación
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PublisherScreen() {
    val context = LocalContext.current

    // Instancia del Publicador (solo se crea una vez)
    val publisher: LocationPublisher = remember { LocationPublisher(context) }

    // Estado para controlar si el envío está activo o no
    var isPublishing by remember { mutableStateOf(false) }

    // Define los permisos necesarios
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // Detiene el servicio al salir de la pantalla (limpieza)
    DisposableEffect(Unit) {
        onDispose {
            if (isPublishing) {
                publisher.stopPublishing()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dispositivo EMISOR (Publicador)", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        // --- Manejo de Permisos ---
        if (!locationPermissionsState.allPermissionsGranted) {
            PermissionRequestSection(locationPermissionsState)
        } else {
            // --- Control del Servicio ---
            Button(
                onClick = {
                    if (isPublishing) {
                        publisher.stopPublishing()
                        isPublishing = false
                    } else {
                        publisher.startPublishing()
                        isPublishing = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPublishing) Color.Red else Color.Green
                ),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(
                    text = if (isPublishing) "VERSAND STOPPEN" else "SENDUNG JEDE MINUTE STARTEN",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(Modifier.height(16.dp))
            Text(
                text = if (isPublishing) "Enviando ubicación a Firebase..." else "Envío detenido. Pulsa INICIAR.",
                color = if (isPublishing) Color.Green.copy(alpha = 0.8f) else Color.Gray,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Revisa la consola de Firebase para ver los datos.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// Composable para solicitar los permisos
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionRequestSection(state: MultiplePermissionsState) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("Se necesitan permisos de ubicación para enviar datos.", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { state.launchMultiplePermissionRequest() }) {
                Text("Conceder Permisos")
            }
        }
    }
}