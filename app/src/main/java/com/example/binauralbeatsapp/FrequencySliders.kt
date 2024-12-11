package com.example.binauralbeatsapp

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrequencySliderState(
    type: SliderType,
    sliderRange: ClosedFloatingPointRange<Float>,
    synthViewModel: MainViewModel,
    modifier: Modifier = Modifier,
){

    val synthState = synthViewModel.synthState.observeAsState()
//    val frequency = if(type == SliderType.CARRIER)  else synthState.value?.secondFreq
    val value: Float
    if(type == SliderType.CARRIER){
        value = synthState.value?.freq!!
    }else if(type == SliderType.MIX){
        value = synthState.value?.maskingMix!!
    }
    else{
        value = synthState.value?.secondFreq!!
    }

    val sliderPosition = remember { mutableStateOf(value) }

    val valueString = if(type == SliderType.MIX)  value.toBigDecimal().toString() else  value.roundToInt().toString()
    val label = if(type == SliderType.MIX) "%" else "Hz"

    FrequencySlider(
        type = type,
        sliderPosition = sliderPosition.value!!,
        onValueChange = {
            sliderPosition.value = it
            if(type == SliderType.CARRIER) {
                synthViewModel.setFreq(it)
            } else if (type == SliderType.MIX){
                synthViewModel.setMix(it)
            }else{
                synthViewModel.setSecondFreq(it)
            }
                        },
        sliderRange = sliderRange,
        synthViewModel,
        valueString,
        label,
        modifier
        )

}

@Composable
@ExperimentalMaterial3Api
fun FrequencySlider(
    type: SliderType,
    sliderPosition: Float,
    onValueChange: (Float) -> Unit,
    sliderRange: ClosedFloatingPointRange<Float>,
    synthViewModel: MainViewModel,
    freqString: String,
    label:String,
    modifier: Modifier = Modifier,
    ){
    Column(
        modifier = modifier.padding(start = 10.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(type.toResourceString()),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            )
            TextField(
                value = freqString,
                onValueChange = {  },
                modifier = modifier.padding(end = 24.dp),
                label = {
                    Text(label)
                }
            )


        }
        Slider(
            modifier = modifier,
            value = sliderPosition,
            onValueChange = onValueChange,
            valueRange = sliderRange,
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = remember {MutableInteractionSource()},
                    thumbSize = DpSize(10.dp,30.dp),
                    modifier = Modifier.offset(5.dp)
                )
            },
        )
    }

}

fun floatMaker(string:String): Float {
    if(string == " ")
        return 0f
    else
        return string.toFloat()
}


//@Preview(showBackground = true)
//@Composable
//@ExperimentalMaterial3Api
//fun SliderPreview(){
//    FrequencySliderState(type = SliderType.CARRIER, modifier = Modifier)
//}
