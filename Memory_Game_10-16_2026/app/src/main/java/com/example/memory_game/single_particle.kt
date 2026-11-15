
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.collection.intFloatMapOf
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontVariation.Setting
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.memory_game.my_database
import java.io.ByteArrayOutputStream
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue


val Context.mi_data by preferencesDataStore( name = "user_preferences")
val USER_NAME_KEY = stringPreferencesKey("user_name")
val MY_NUMBER_KEY = intPreferencesKey("age")

val Context.my_firebase by preferencesDataStore("user_preferences")
var FOTO = stringPreferencesKey("foto")
suspend fun edit_my_firebase(context: Context,bitmap: Bitmap){

    var data_jpg = ByteArrayOutputStream()
    bitmap.compress(
        Bitmap.CompressFormat.JPEG,
        85,
        data_jpg)
    val byteArray = data_jpg.toByteArray()
    val photoString = Base64.encodeToString(
        byteArray,
        Base64.NO_WRAP
    )




    context.my_database.edit {

    }
}




@Composable
fun  my_function(){
    var avilable_foto by remember{mutableStateOf(false)}
    var info_foto by remember{mutableStateOf(false)}
    var context = LocalContext.current
    var activity = context as Activity
    var my_bitmap by remember{mutableStateOf<Bitmap?>(null)}
    val permiso_camara = android.Manifest.permission.CAMERA
    var launcher_camera_out = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()){
        bitmap: Bitmap ? ->
        if(bitmap != null){
            my_bitmap = bitmap
            avilable_foto = true

        }
    }
    val permiso_concedido = ContextCompat.checkSelfPermission(
        context,
        permiso_camara)==PackageManager.PERMISSION_GRANTED
    if(permiso_concedido){ info_foto = true}
    var launcher_camera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()){
        granted ->
        if(!granted){
            val check = ActivityCompat.shouldShowRequestPermissionRationale(
             activity,
                permiso_camara)
            if(!check){
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package",context.packageName,null)
                )
                context.startActivity(intent)
            }
        }
        else{
             info_foto = true
            //Toast.makeText(context,"SE CAMBIO EL VALOR..",Toast.LENGTH_SHORT).show()
        }
    }




    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

      Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))

          .padding(vertical = 200.dp)
          ,contentAlignment = Alignment.Center){
          Icon(
              imageVector= Icons.Filled.CameraAlt,
              contentDescription = "Foto de Perfil",
              tint = Color.Blue,
              modifier = Modifier.size(45.dp)
                  .clickable(onClick = {
                      launcher_camera.launch(permiso_camara)
                  }))


      }
        Box(modifier = Modifier
            .size(190.dp)
            .clip(CircleShape)
            .background(Color(0xFF353B50))
            .border(3.dp, Color.Green/*(0xFF55EFC4)*/,CircleShape)
            .clickable(onClick = {
                if(info_foto){
                    launcher_camera_out.launch(null)
                }}))
        {
            if(my_bitmap != null){
                Image(
                    bitmap = my_bitmap!!.asImageBitmap(),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}