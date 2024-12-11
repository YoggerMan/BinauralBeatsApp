package com.example.binauralbeatsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ButtonRow(
    synthViewModel: MainViewModel,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        ButtonState(buttonType = ButtonType.PINK_NOISE, synthViewModel,modifier)
        ButtonState(buttonType = ButtonType.MUSIC_MASK, synthViewModel, modifier)
        ButtonState(buttonType = ButtonType.OTHER,synthViewModel, modifier)
    }
}