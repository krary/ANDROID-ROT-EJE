package com.example.memory_game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.example.memory_game.my_composable.GameScreen

enum class Screen{
    Start,
    Game
}

@Composable
fun MainApp(){
    var current_screen by remember{mutableStateOf(Screen.Start)}
    when(current_screen){
        Screen.Start -> StartScreen(Click= {current_screen = Screen.Game})
        Screen.Game -> GameScreen(Click={current_screen=Screen.Start})
    }
}

@Composable
fun StartScreen(Click:()->Unit){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF171923))
        .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Memory Game...",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White)
        Spacer(modifier=Modifier.height(48.dp))
        Button(onClick = Click){
            Text(text = "PLAY!",
                style = MaterialTheme.typography.titleMedium)
        }
    }






}