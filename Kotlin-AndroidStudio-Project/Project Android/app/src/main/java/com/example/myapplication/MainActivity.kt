package com.example.myapplication

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.view.GravityCompat

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_AUDIO = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pedirPermisoAudio()

        val botonExit = findViewById<Button>(R.id.b_Message)
        val drawerlayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        botonExit.setOnClickListener {
            val mp = MediaPlayer.create(this, R.raw.sonido)
            // mp.start()
            drawerlayout.openDrawer(GravityCompat.START)
        }
    }

    private fun pedirPermisoAudio() {
        val permiso = Manifest.permission.READ_MEDIA_AUDIO

        if (ContextCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permiso), REQUEST_CODE_AUDIO)
        } else {
            listarAudios()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_AUDIO &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            listarAudios()
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listarAudios() {
        Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
        // Aquí podrías cargar los audios usando MediaStore, por ejemplo
    }
}
