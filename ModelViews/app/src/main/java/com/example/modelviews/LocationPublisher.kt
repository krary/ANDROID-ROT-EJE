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

// IMPORTACIONES AÑADIDAS 🎯
import java.text.SimpleDateFormat
import java.util.* class LocationPublisher(private val context: Context) {

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
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnFailureListener { e ->
                Log.e("Publisher", "Error al iniciar ubicación: $e")
            }
            Log.d("Publisher", "Iniciando envío de ubicación a Firebase.")
        } else {
            Log.e("Publisher", "Permisos de ubicación no concedidos.")
        }
    }

    // 2. Detiene la escucha
    fun stopPublishing() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // 3. Escribe los datos en Firebase
    private fun publishLocation(location: Location) {

        // 1. Capturar el timestamp
        val currentTimestamp = System.currentTimeMillis()

        val data = LocationData(
            latitude = location.latitude,
            longitude = location.longitude,
            timestamp = currentTimestamp // Usamos la variable capturada
        )

        // 2. Formatear para Logcat (la aplicación aún envía el Long)
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val readableTime = formatter.format(Date(currentTimestamp))

        // Usa setValue() para sobrescribir la ubicación anterior con la nueva.
        databaseRef.setValue(data)
            .addOnSuccessListener {
                // 3. Usar el tiempo legible en el Log
                Log.i("Publisher", "Ubicación enviada. Lat: ${data.latitude}, Hora: $readableTime")
            }
            .addOnFailureListener { e ->
                Log.e("Publisher", "ERROR al enviar ubicación: $e")
            }
    }
}