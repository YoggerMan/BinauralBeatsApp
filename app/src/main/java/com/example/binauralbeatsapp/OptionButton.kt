package com.example.binauralbeatsapp

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@Composable
fun ButtonState(
    buttonType: ButtonType,
    synthViewModel: MainViewModel,
    modifier: Modifier = Modifier,

) {
    val synthState = synthViewModel.synthState.observeAsState()
    val context = LocalContext.current

    var buttonState:Boolean
    if(buttonType == ButtonType.PINK_NOISE){
        buttonState = synthState.value?.hasPinkNoise!!
    }else if(buttonType == ButtonType.MUSIC_MASK){
        buttonState = synthState.value?.hasMusic!!
    }else{
        buttonState = false
    }

//    var buttonState by remember { mutableStateOf(false) }
    val color = if(buttonState) ButtonDefaults.buttonColors().disabledContainerColor else ButtonDefaults.buttonColors().containerColor
    Button (
        onClick = {
            buttonState = !buttonState;
            if(buttonType == ButtonType.PINK_NOISE) {
                synthViewModel.changePinkNoiseMasking(!(synthState.value?.hasPinkNoise!!))
                if(synthState.value?.hasMusic!!){
                    synthViewModel.changeMusicMasking(false);
                }
            }
            else if(buttonType == ButtonType.MUSIC_MASK){
                synthViewModel.changeMusicMasking(!(synthState.value?.hasMusic!!))
                if(synthState.value?.hasPinkNoise!!){
                    synthViewModel.changePinkNoiseMasking(false);
                }
            }
            else{
                synthViewModel.changeOtherMasking(!(synthState.value?.hasOtherMasking!!))
            }
                  },
        colors = ButtonDefaults.buttonColors(color)
    ){
        Text(text = stringResource(buttonType.toResourceString()))
    }
}

