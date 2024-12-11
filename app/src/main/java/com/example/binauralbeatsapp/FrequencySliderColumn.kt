package com.example.binauralbeatsapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData

@Composable
fun FrequencySliderColumn(synthViewModel: MainViewModel, modifier: Modifier){

    val synthView = synthViewModel.synthState.value?.type!!


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        FrequencySliderState(
            type = SliderType.CARRIER,
            300f..900f,
            synthViewModel,
            modifier = Modifier
        )
        if (synthView == BeatType.ISOCHRONIC) {
            FrequencySliderState(
                type = SliderType.PULSE,
                0f..40f,
                synthViewModel,
                modifier = Modifier
            )
        }
        else{
            FrequencySliderState(
                type = SliderType.DIFF,
                0f..40f,
                synthViewModel,
                modifier = Modifier
            )
        }
        FrequencySliderState(
            type = SliderType.MIX,
            0f..1f,
            synthViewModel,
            modifier = Modifier
        )
    }

}