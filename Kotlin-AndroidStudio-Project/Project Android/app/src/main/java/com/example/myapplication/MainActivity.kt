package com.example.myapplication
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.MediaPlayer
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonExit = findViewById<Button>(R.id.b_Message)
        val drawerlayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        botonExit.setOnClickListener{
           // finishAffinity()
            val mp = MediaPlayer.create(this,R.raw.sonido);
            //mp.start()
            drawerlayout.openDrawer(GravityCompat.START)
        }

    }
}