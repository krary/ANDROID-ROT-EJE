import android.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun visible(){
    var visible by remember{mutableStateOf(false)}
    Box(modifier = Modifier
        .fillMaxSize()){
          Column(modifier = Modifier
              .fillMaxSize()
              .background(Color.Gray)){
              Box(modifier = Modifier

                  .fillMaxSize()
                  .background(Color.DarkGray)
                  ,
                  contentAlignment = Alignment.Center
              ){
                  Text("hola", color = Color.White, fontSize = 45.sp,
                      style = MaterialTheme.typography.bodyLarge,
                      modifier = Modifier
                          .clickable(onClick = {visible = !visible}))
                  Spacer(Modifier.height(4.dp))


              }
          }


    }
}