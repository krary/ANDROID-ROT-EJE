package com.example.memory_game

import android.graphics.Paint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Score
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memory_game.my_composable.GameScreen

enum class Screen{
    Start,

    Game,
    Level_On

}

@Composable
fun MainApp(){
    var current_screen by remember{mutableStateOf(Screen.Start)}
    when(current_screen){
        Screen.Start -> StartScreen(Click= {current_screen = Screen.Game})
        Screen.Game -> Levels(onLevelSelected ={current_screen=Screen.Level_On})
        Screen.Level_On-> GameScreen (Click= {current_screen = Screen.Game} )
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
        } } }
@Composable
fun Levels(
    totalLevels:Int = 10,
    unlockedLevel:Int = 1,
    onLevelSelected: (Int) -> Unit){
    var expanded by remember{mutableStateOf(false)}
    Box(modifier =Modifier.fillMaxSize()){



    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF171923))
        .padding(vertical = 367.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){




        Text("SELECCIONA UN NIVEL.....",
            style= MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,modifier = Modifier.padding(24.dp),
            fontSize = 22.sp
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(totalLevels){index ->
                    val levelNum = index +1
                     val isUnlocked = levelNum <=unlockedLevel
                      LevelCard3D(
                          levelNumber = levelNum,
                          isUnlocked = isUnlocked,
                          onClick = {
                              if(isUnlocked){
                                  onLevelSelected(levelNum)
                              }
                          }
                      )
            }


        }
    }//AQUI TERMINA EL COLUMN
        //==================================================================
        Row(modifier = Modifier
            .padding(vertical = 60.dp)
            , horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically){
            IconButton(
            onClick = {expanded = !expanded},
            Modifier.size(45.dp))
            {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    tint = Color.White,
                    contentDescription = "Menu",
                    modifier = Modifier.size(35.dp))

            }
            AnimatedVisibility(
                visible = expanded,
                enter = expandHorizontally(expandFrom = Alignment.Start) + fadeIn(),
                exit = shrinkHorizontally (shrinkTowards = Alignment.Start) + fadeOut()
                ){
                Row(modifier = Modifier
                    .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    IconButton(onClick = {expanded = !expanded},modifier = Modifier.size(48.dp)){
                     Icon(
                         imageVector = Icons.Filled.Score,
                         contentDescription = "score",
                         tint = Color.White,
                         modifier = Modifier.size(28.dp)
                     )
                    }
                    IconButton(onClick = {expanded = !expanded},modifier = Modifier.size(48.dp)){
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "perfil",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    IconButton(onClick = {expanded = !expanded},modifier = Modifier.size(48.dp)){
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "score",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    IconButton(onClick = {expanded = !expanded},modifier = Modifier.size(48.dp)){
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "score",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

            }
        }
        //==================================================================

    }//AQUI TERMINA EL BOX

}//AQUI TERMINA EL COMPOSABLE





@Composable
fun LevelCard3D(levelNumber:Int,isUnlocked:Boolean,onClick:()->Unit){
    val baseColor = if(isUnlocked)Color(0xFF4CAF50) else Color(0xFF555555)
    val shadowColor = if (isUnlocked) Color(0xFF2E7D32) else Color(0xFF333333)
    val textColor = if(isUnlocked) Color.White else Color.Gray
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(bottom=6.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = isUnlocked){onClick()},
        contentAlignment = Alignment.Center)
        {
        Box( modifier = Modifier
            .fillMaxSize()
            .padding(bottom=4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(baseColor),
            contentAlignment = Alignment.Center
        ){

        }

        }


}