package com.example.design
import androidx.compose.foundation.layout.fillMaxWidth

import android.os.Bundle
import android.provider.MediaStore

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import com.example.design.ui.theme.DesignTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {call()

                   SelectorRow()

                }
            }
        }
    }
}
@Composable
fun FileList(extension: String) {
    val context = LocalContext.current
    var files by remember { mutableStateOf(listOf<String>()) }

    // Buscar archivos cuando cambia la extensión
    LaunchedEffect(extension) {
        val pdfFiles = mutableListOf<String>()
        val projection = arrayOf(MediaStore.Files.FileColumns.DISPLAY_NAME)
        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE}=?"
        val selectionArgs = arrayOf("application/pdf")

        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            while (it.moveToNext()) {
                val name = it.getString(nameColumn)
                pdfFiles.add(name)
            }
        }
        files = pdfFiles
     }

    // Mostrar lista
    LazyColumn {
        items(files) { file ->
            Text(text = file, color = Color.Green)
        }
    }
}
@Composable
fun call(){
    Box(modifier = Modifier.fillMaxSize()){
        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ){ Icon(Icons.Default.Menu, contentDescription = "Enviar")}
    }
}
@Composable

fun SelectorRow() {
   val lista = listOf(".Mp4",".Mp3",".Pdf",".Epub",".txt")
    val setInfo =  remember{mutableStateOf(-1)}
    val showDialog = remember{mutableStateOf(false)}
    Column(modifier = Modifier.padding(5.dp)){
        lista.forEachIndexed { index,nombre ->

            Row(modifier = Modifier.padding(12.dp)){
                Text(text="Extension: $nombre", modifier = Modifier.weight(1f))
                Switch(checked = setInfo.value==index, onCheckedChange = {isCHecked ->
                    setInfo.value = if(isCHecked) index else -1
                    if(isCHecked){
                        showDialog.value = true
                    }
                }
                )
            }
        }//Aqui termina el for indexado
        if (setInfo.value != -1 && lista[setInfo.value] == ".Pdf") {
            FileList(".pdf")
        }

    }

    if (showDialog.value) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { showDialog.value = false }
        ) {
            Surface(
                modifier = Modifier
                    .width(300.dp)    // 👈 tamaño libre
                    .height(900.dp),
                     color = Color.Black,
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Mensaje personalizado",color=Color.Green)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "OK",
                        color=Color.Cyan,
                        modifier = Modifier
                            .clickable { showDialog.value = false }
                            .padding(8.dp)
                    )
                }
            }
        }
    }}
