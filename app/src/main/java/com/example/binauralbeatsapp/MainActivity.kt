package com.example.binauralbeatsapp

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.binauralbeatsapp.ui.theme.BinauralBeatsAppTheme

class MainActivity : ComponentActivity() {
    private val synthViewModel: MainViewModel by viewModels()
    private val synth = NativeSynth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycle.addObserver(synth)
        synthViewModel.synth = synth

        setContent {
            BinauralBeatsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        name = "Android",
                        synthViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun App(name:String, synthViewModel: MainViewModel, modifier: Modifier){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        SynthTypeMenu(synthViewModel, modifier.padding(vertical = 10.dp))
        PlayButton(synthViewModel)
        ButtonRow(synthViewModel, modifier.padding(vertical = 14.dp))
        FrequencySliderColumn(synthViewModel, modifier)
//        val text = synthViewModel.synthState.observeAsState()
//        Text(text.value.toString())
        SongsDrawer(synthViewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview(){
    val synthViewModel: MainViewModel = MainViewModel()
    val name: String = "Binaural Beats"
    App(name, synthViewModel = synthViewModel, modifier = Modifier)
}