import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.io.File
import java.io.FileOutputStream

val Context.perfil by preferencesDataStore("profile")

suspend fun Context.editBitmapPerfil(context : Context
                                     ,value:String,
                                     bitmap: Bitmap):String{

        var foto = File(context.filesDir,value)
    FileOutputStream(foto).use {
        outputStream ->
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                85,
                outputStream
            )
    }
    return foto.absolutePath
}
suspend fun Context.editPerfil(value:String,path_foto: String){
     perfil.edit {
         preferences ->
         preferences[stringPreferencesKey(value)] = path_foto
     }
}



@Composable
fun user() {
    var context = LocalContext.current
    var activity = context as Activity
    var scope = rememberCoroutineScope()
    var permiso = android.Manifest.permission.CAMERA
    var bitmap_copy by remember { mutableStateOf<Bitmap?>(null) }
    var permiso_launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()){
        granted ->
        if(!granted){
            val lock_permission = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permiso
            )
            if(!lock_permission){
            var intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package",context.packageName,null))
                context.startActivity(intent) } } }



    val take_picture_launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()) {
        bitmap: Bitmap?->
        if(bitmap != null){
            bitmap_copy = bitmap
        }
    }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),

            verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){


    Box(modifier = Modifier
        .size(90.dp)
        .clip(CircleShape)
        .background(Color(0xFF353B50))
        .border(4.dp,Color(0xFF55EFC4),CircleShape)
        .clickable(onClick = {}),
        contentAlignment = Alignment.Center){
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "symbol",
            tint = Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
        Spacer(Modifier.height(23.dp))
        Text("Perfil",fontSize = 23.sp, fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium)
    }

}