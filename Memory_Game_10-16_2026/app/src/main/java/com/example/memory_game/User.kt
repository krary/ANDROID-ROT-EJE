import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun user() {
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
    }}

}