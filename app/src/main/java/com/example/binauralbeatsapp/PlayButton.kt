package com.example.binauralbeatsapp

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp



@Composable
fun PlayButton(synthViewModel: MainViewModel){
    val synthState = synthViewModel.synthState.observeAsState()
    val isPlaying = synthState.value?.isPlaying

    Surface(
        onClick = {synthViewModel.changePlayingState(!isPlaying!!)},
        shape = RectangleShape,
        modifier = Modifier.size(40.dp)
        ) {
        Icon(
            imageVector = if(isPlaying!!) Icons.Filled.Pause else Icons.Filled.PlayArrow,
            contentDescription = null,
            Modifier.size(40.dp)
        )

    }
}