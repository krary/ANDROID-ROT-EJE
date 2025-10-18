package com.example.modelviews

import android.Manifest
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import android.net.Uri
import android.provider.Settings
import android.util.Log

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PublisherScreen() {
    val context = LocalContext.current

    var isPublishing by remember { mutableStateOf(false) }

    // Definir los 3 permisos necesarios
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    )

    // Determinar si todos los permisos están concedidos.
    // Usamos 'any' para ver si al menos uno de los permisos de ubicación en uso está concedido.
    val hasForegroundLocation = locationPermissionsState.permissions.any {
        (it.permission == Manifest.permission.ACCESS_FINE_LOCATION ||
                it.permission == Manifest.permission.ACCESS_COARSE_LOCATION) && it.status.isGranted
    }

    // Buscar el permiso de segundo plano
    val backgroundPermissionState = locationPermissionsState.permissions.find {
        it.permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION
    }

    // Determinar si la app tiene permiso para segundo plano
    val hasBackgroundLocation = backgroundPermissionState?.status?.isGranted == true

    // Mostrar sección de permisos si no tenemos AMBOS (Uso + Segundo Plano)
    val showPermissionSection = !hasForegroundLocation || !hasBackgroundLocation

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dispositivo EMISOR (Publicador)", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        // --- Manejo de Permisos ---
        if (showPermissionSection) {
            PermissionRequestSection(locationPermissionsState, hasForegroundLocation, hasBackgroundLocation)
        } else {
            // --- Control del Servicio ---
            Button(
                onClick = {
                    val serviceIntent = Intent(context, com.example.modelviews.LocationForegroundServices::class.java)
                    if (isPublishing) {
                        context.stopService(serviceIntent)
                        isPublishing = false
                    } else {
                        // Antes de Android O, solo startService; a partir de O, startForegroundService
                        context.startForegroundService(serviceIntent)
                        isPublishing = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPublishing) Color.Red else Color.Green
                ),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(
                    text = if (isPublishing) "DETENER ENVÍO" else "INICIAR ENVÍO",
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionRequestSection(
    state: MultiplePermissionsState,
    hasForegroundLocation: Boolean,
    hasBackgroundLocation: Boolean
) {
    val context = LocalContext.current

    // Texto de la acción requerida
    val requiredActionText = remember(hasForegroundLocation, hasBackgroundLocation) {
        when {
            !hasForegroundLocation -> "Conceder Ubicación (Mientras se usa)"
            !hasBackgroundLocation -> "Habilitar 'Permitir todo el tiempo'"
            else -> "Permisos Concedidos"
        }
    }

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("Se necesitan permisos de ubicación para enviar datos.", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            // --- Mensaje de estado
            if (!hasForegroundLocation) {
                Text(
                    "Paso 1: Concede los permisos de ubicación iniciales (Precisa).",
                    color = MaterialTheme.colorScheme.error
                )
            } else if (!hasBackgroundLocation) {
                Text(
                    "Paso 2: Concede el permiso de **'Permitir todo el tiempo'** en los ajustes de la aplicación para que funcione en segundo plano.",
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(16.dp))

            // --- Lógica del Botón
            Button(
                onClick = {
                    when {
                        // Caso 1: Faltan los permisos de ubicación mientras se usa la app (FINE/COARSE).
                        // Se lanza la solicitud del sistema (Android no muestra BACKGROUND aquí).
                        !hasForegroundLocation -> {
                            state.launchMultiplePermissionRequest()
                        }

                        // Caso 2: Se tienen los permisos de uso, pero falta el de segundo plano.
                        // Redirigimos al usuario a los ajustes.
                        !hasBackgroundLocation -> {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                            context.startActivity(intent)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(requiredActionText)
            }
        }
    }
}