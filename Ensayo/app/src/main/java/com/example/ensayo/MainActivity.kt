package com.example.ensayo

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ensayo.ui.theme.EnsayoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EnsayoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NetworkInfoScreen(this)
                }
            }
        }
    }
}

// ----------- FUNCIONES DE RED -----------

fun getNetworkInfo(context: Context): Map<String, String> {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return mapOf("Estado" to "Sin conexión activa")
    val linkProperties =
        connectivityManager.getLinkProperties(network) ?: return mapOf("Estado" to "Sin propiedades de red")

    val info = mutableMapOf<String, String>()

    // Direcciones IP + máscara
    val addresses = linkProperties.linkAddresses.map { addr ->
        "${addr.address.hostAddress}/${addr.prefixLength} (máscara ${prefixToNetmask(addr.prefixLength)})"
    }
    if (addresses.isNotEmpty()) {
        info["Direcciones IP"] = addresses.joinToString("\n")
    }

    // Puertas de enlace
    val gateways = linkProperties.routes.mapNotNull { it.gateway?.hostAddress }
    if (gateways.isNotEmpty()) {
        info["Puertas de enlace"] = gateways.joinToString(", ")
    }

    // DNS
    val dnsServers = linkProperties.dnsServers.map { it.hostAddress }
    if (dnsServers.isNotEmpty()) {
        info["Servidores DNS"] = dnsServers.joinToString(", ")
    }

    // Nombre de interfaz (ej. wlan0)
    info["Interfaz"] = linkProperties.interfaceName ?: "Desconocida"

    return info
}

// Convierte prefixLength a máscara de red
fun prefixToNetmask(prefixLength: Int): String {
    val mask = (0xffffffffL shl (32 - prefixLength)).toInt()
    return "${mask shr 24 and 0xff}.${mask shr 16 and 0xff}.${mask shr 8 and 0xff}.${mask and 0xff}"
}

// ----------- COMPOSABLE UI -----------

@Composable

fun NetworkInfoScreen(context: Context) {
    var info by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    // Cargar info al entrar
    LaunchedEffect(Unit) {
        info = getNetworkInfo(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(36.dp)
            .background(color = Color(0xFF523733)),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Información de red",
            color = Color.Green,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (info.isEmpty()) {
            Text(
                "No se pudo obtener información de la red",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            )
        } else {
            info.forEach { (label, value) ->
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        label,
                        color = Color.Cyan,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                    Text(
                        value,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}
