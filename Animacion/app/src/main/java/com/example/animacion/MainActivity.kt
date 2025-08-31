package com.example.animacion
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animacion.ui.theme.AnimacionTheme
import androidx.compose.runtime.State

import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimacionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Field()

                }
            }
        }
    }
}

@Composable
fun Field(){

    var texto by remember{mutableStateOf("")}
    Column(modifier = Modifier.padding(16.dp)){
           Spacer(modifier=Modifier.height(59.dp))
        OutlinedTextField(
            value = texto,
            onValueChange={texto=it},
            label = {Text("Write something Exm.. .pdf",color=Color.Green)},
            modifier = Modifier.fillMaxWidth()
        )
        if(texto == ".pdf"){
            var t = OpenDirectoryOnStart().value
            Text(t,color=Color.Yellow, modifier = Modifier.padding(16.dp))
        }
    }

}
@Composable
fun OpenDirectoryOnStart():State<String> {
val context = LocalContext.current
    val elemento = remember { mutableStateOf("") }
   val launcher = rememberLauncherForActivityResult(
       contract = ActivityResultContracts.GetContent()
   ){
       uri:Uri?->
      elemento.value = uri?.let{getFileNameFromUri(context,it)}?:""

   }
    LaunchedEffect(Unit) {
        launcher.launch("application/pdf")

    }
    return elemento
}



fun getFileNameFromUri(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(
        uri,
        null,
        null,
        null,
        null
    )
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst() && nameIndex >= 0) {
            return it.getString(nameIndex) // <- Aquí obtienes solo el nombre del archivo
        }
    }
    return null
}


















