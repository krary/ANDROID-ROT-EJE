package com.example.memory_game


import LevelUpRewardScreen
import ShatterDemoScreen
import  android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.memory_game.my_composable.GameScreen
import com.example.memory_game.ui.theme.Memory_gameTheme
import my_function



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        //ShatterDemoScreen()
        MainApp()
        //my_function()
        //LevelUpRewardScreen(Clickkk = {})

        }
    }}