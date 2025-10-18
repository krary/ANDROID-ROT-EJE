package com.example.modelviews

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

// Necesitarás una clase de datos para Firebase. AÑADE ESTA CLASE:


class LocationPublisher(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val databaseRef = FirebaseDatabase.getInstance().getReference("ubicaciones").child("dispositivo_emisor")

    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 60000L)
        .build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                publishLocation(location)
            }
        }
    }

    // 1. Inicia la escucha de ubicación
    fun startPublishing() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Aunque el Foreground Service garantiza la ubicación "while-in-use",
            // para una publicación continua en segundo plano, se idealmente requiere ACCESS_BACKGROUND_LOCATION
            // que se concede en la pantalla de ajustes

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnFailureListener { e ->
                Log.e("Publisher", "Error al iniciar ubicación: $e")
            }
            Log.d("Publisher", "Iniciando envío de ubicación a Firebase.")
        } else {
            // Este Log debería ser raramente visto si la pantalla Compose funciona correctamente
            Log.e("Publisher", "Permisos de ubicación no concedidos. El servicio no puede iniciar la escucha.")
        }
    }

    // 2. Detiene la escucha
    fun stopPublishing() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // 3. Escribe los datos en Firebase
    private fun publishLocation(location: Location) {
        val currentTimestamp = System.currentTimeMillis()
        val formatter = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
        val readableTime = formatter.format(Date(currentTimestamp))

        val data = LocationData(
            latitude = location.latitude,
            longitude = location.longitude,
            timestamp = readableTime
        )

        databaseRef.setValue(data)
            .addOnSuccessListener {
                Log.i("Publisher", "Ubicación enviada. Lat: ${data.latitude}, Hora: $readableTime")
            }
            .addOnFailureListener { e ->
                Log.e("Publisher", "ERROR al enviar ubicación: $e")
            }
    }
}