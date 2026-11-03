
import android.content.Context
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


val Context.mi_data by preferencesDataStore( name = "user_preferences")
val USER_NAME_KEY = stringPreferencesKey("user_name")

suspend fun superFloat(context: Context, name:String){
      context.mi_data.edit{
            preferences ->
                  preferences[USER_NAME_KEY] = name
      }
}


@Composable
fun name_2(){
val context = LocalContext.current




      Box(modifier= Modifier
            .fillMaxSize()
            .background(Color.Blue))
      {
            Column(modifier = Modifier
                 .fillMaxSize()
                 ,verticalArrangement = Arrangement.Center,
                  horizontalAlignment = Alignment.CenterHorizontally){

            }
      }
}


@Composable
fun name(){
      val context = LocalContext.current
      val user_flow = context.mi_data.data.map {
            preferences ->
                preferences[USER_NAME_KEY] ?: "Write your name..." }
            .collectAsState(initial = "Loading....")

val mensaje by remember{mutableStateOf("TESTING RECIVER INTO APK")}
      Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)){
            Column(modifier = Modifier
                  .fillMaxSize()
                  .padding(vertical = 14.dp),
                  verticalArrangement = Arrangement.Center,
                  horizontalAlignment = Alignment.CenterHorizontally){
                  Text(user_flow.value,color = Color.Green,
                        fontSize = 38.sp,fontWeight = FontWeight.Bold,modifier = Modifier.clickable{})
                  Text(mensaje,color = Color.Green,
                        fontSize = 38.sp,fontWeight = FontWeight.Bold,modifier = Modifier.clickable{})
                  Text(mensaje       ,color = Color.Green,
                        fontSize = 38.sp,fontWeight = FontWeight.Bold,modifier = Modifier.clickable{})
            }
      }
}