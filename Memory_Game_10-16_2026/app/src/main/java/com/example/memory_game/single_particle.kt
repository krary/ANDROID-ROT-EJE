
import android.text.Layout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp

@Composable
fun visible(){
    var expanded by remember{mutableStateOf(false)}

          Column(modifier = Modifier
              .fillMaxSize()
              .background(Color.DarkGray))
          {
       Row(modifier = Modifier

           .clip(RoundedCornerShape(12.dp))
           .background(Color.Gray)
           .padding(vertical = 43.dp)){

           IconButton(
               onClick = {expanded = !expanded},
               modifier = Modifier.size(64.dp)) {
               Icon(
                  imageVector = Icons.Filled.Menu,
                   contentDescription = "Description",
                   tint = Color.White,
                   modifier = Modifier.size(40.dp)
               )
           }
       AnimatedVisibility(
           visible = expanded,
           enter = expandHorizontally(expandFrom = Alignment.Start) + fadeIn(),
           exit = shrinkHorizontally (shrinkTowards = Alignment.Start)+ fadeOut()
       ) {
           Row(modifier = Modifier
               .padding(4.dp)
               ,verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.spacedBy(2.dp)){
               IconButton(onClick = {expanded = false}){
                   Icon(
                       imageVector = Icons.Filled.ArrowBackIosNew,
                       contentDescription = "takeBack",
                       tint = Color.White
                   )
               }
               IconButton(onClick = {expanded = false}){
                   Icon(
                       imageVector = Icons.Filled.PermIdentity,
                       contentDescription = "takeBack",
                       tint = Color.White
                   )
               }
           }
       }
       }

          }



}