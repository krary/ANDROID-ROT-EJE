
import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
      var clave by remember{mutableStateOf("MESSAGE")}
     val launcher =  rememberLauncherForActivityResult(
      contract = ActivityResultContracts.RequestPermission()
     ){
           granted ->
           if(granted ){
             clave = "SE PUEDE ABRIR LA CAMARA"
           }
           else{
                 clave = "NO SE PUEDE ABRIR LA CAMARA"
           }


     }
    Column(modifier = Modifier
        .fillMaxSize()){

      Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))

          .padding(vertical = 200.dp)
          ,contentAlignment = Alignment.Center){
          //Text(clave,fontSize = 34.sp,fontWeight = FontWeight.Bold,color = Color.White)
          Icon(
              imageVector= Icons.Filled.CameraAlt,
              contentDescription = "Foto de Perfil",
              tint = Color.Blue,
              modifier = Modifier.size(45.dp)
                  .clickable(onClick = {}))


      }
    Box(modifier = Modifier
            .size(90.dp)
            .padding(vertical = 200.dp)){
        Text("<<Revoque>>",fontSize = 32.sp,fontWeight = FontWeight.Bold,color = Color.White)
    }

    }
}