
import android.content.Context
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.mi_data by preferencesDataStore( name = "user_preferences")
val USER_NAME_KEY = stringPreferencesKey("user_name")
val MY_NUMBER_KEY = intPreferencesKey("age")

@Composable
fun my_function(){
      var message by remember{mutableStateOf("HELLO WORLD")}
      var animacion  = rememberInfiniteTransition("waveTransition")
      var start_animacion by remember{mutableStateOf(false)}





      Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            ,contentAlignment = Alignment.Center) {
            Button(onClick = {start_animacion = !start_animacion}){
                  Text("Start",color = Color.DarkGray,fontWeight = FontWeight.Bold,fontSize = 12.sp)
            }
            Spacer(Modifier.height(34.dp))
            if(start_animacion){
                  Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                  ) {
                        message.forEachIndexed { index, ch ->
                              if(ch == ' '){
                                    Text(text = "", fontSize = 32.sp)
                                    return@forEachIndexed
                              }
                              val scale by animacion.animateFloat(
                                    initialValue = 1f,
                                    targetValue = 1.4f,
                                    animationSpec = infiniteRepeatable(
                                          animation = tween(durationMillis = 700,easing = FastOutSlowInEasing),
                                          repeatMode = RepeatMode.Reverse,
                                          initialStartOffset = StartOffset(offsetMillis = index * 100)
                                    ),
                                    label = "LetterScale_$index"
                              )
                              Text(
                                    text = ch.toString(),
                                    color = Color.Blue,
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.scale(scale)
                              )

                        }






                  }
            }
      }
}