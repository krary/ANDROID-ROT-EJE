package com.example.contador

import android.R.attr.onClick
import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.util.Log
import android.view.SoundEffectConstants
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contador.ui.theme.ContadorTheme
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import java.nio.file.WatchEvent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContadorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   main_function()
                }
            }
        }
    }
}

@Composable
fun main_function() {

    var numero by remember { mutableStateOf(0) }

    Column(modifier= Modifier
        .padding(34.dp)
        .fillMaxWidth()
        , horizontalAlignment = Alignment.CenterHorizontally){
        Text(numero.toString(), fontSize = 75.sp, fontWeight  = FontWeight.ExtraBold,color=Color.Red,modifier= Modifier
            .padding(2.dp))
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement= Arrangement.SpaceBetween){
            Button(onClick={numero++},
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )){Text("+", fontWeight = FontWeight.ExtraBold)}
            Button(onClick={numero--},

                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )){Text("-", fontWeight = FontWeight.ExtraBold)}

        }
            }
}
