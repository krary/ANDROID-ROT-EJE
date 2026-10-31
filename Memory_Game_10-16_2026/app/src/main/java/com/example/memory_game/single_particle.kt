import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun visible(){
    Box(modifier = Modifier
        .fillMaxSize()){
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {})){
            Text("hola", color = Color.Blue, fontSize = 45.sp)
        }
    }
}