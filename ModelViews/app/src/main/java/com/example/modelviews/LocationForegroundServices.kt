package com.example.modelviews

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat

class LocationForegroundServices:Service() {
    private lateinit var publisher: LocationPublisher

    // Temporizador para apagar el servicio
    private val handler = Handler(Looper.getMainLooper())
    private val STOP_DELAY_MS = 30000L // 30 segundos

    override fun onCreate(){
        super.onCreate()
        publisher = LocationPublisher(this)
        createNotificationChannel()

        // 1. Iniciar el servicio en primer plano (mostrar notificación)
        val notification = createNotification()
        val notificationId = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // API 34+
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            } else { // API 29-33
                0
            }
            ServiceCompat.startForeground(
                this,
                notificationId,
                notification,
                type
            )
        } else {
            startForeground(notificationId, notification)
        }

        // 2. Empezar a publicar la ubicación
        publisher.startPublishing()

        // 3. Programar el apagado del servicio después de 30 segundos
        handler.postDelayed({
            // Esto detiene el servicio y elimina la notificación
            stopSelf()
        }, STOP_DELAY_MS)
    }

    override fun onDestroy(){
        super.onDestroy()
        publisher.stopPublishing()
        // Asegurarse de que cualquier tarea pendiente del Handler sea eliminada
        handler.removeCallbacksAndMessages(null)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Usar START_NOT_STICKY si quieres que el sistema NO lo reinicie automáticamente
        // tras ser eliminado. Si se usa WorkManager para despertarlo, esta es una buena opción.
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder?=null

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "location_channel",
                "Ubicacion en segundo plano", // Corregido 'pLano'
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "location_channel")
            .setContentTitle("Enviando ubicación (Temporal)")
            .setContentText("La app está adquiriendo y enviando tu ubicación a Firebase...")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)
            .build()
    }
}