package com.example.binauralbeatsapp

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random


@Composable
fun SynthTypeMenu( synthViewModel: MainViewModel, modifier: Modifier = Modifier){

    val synthState = synthViewModel.synthState.observeAsState()
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier){
        val context = LocalContext.current
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable {
                isExpanded = true
            }
            ){
            Text(
                text = stringResource(synthState.value?.type?.toResourceString()!!)
            )
            Image(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ){
            DropdownMenuItem(
                text = {
                    Text(stringResource(BeatType.MONAURAL.toResourceString()))
                },
                onClick = {
                    isExpanded = false
                    synthViewModel.changeSynthType(BeatType.MONAURAL)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(stringResource(BeatType.BINAURAL.toResourceString()))
                },
                onClick = {
                    isExpanded = false
                    synthViewModel.changeSynthType(BeatType.BINAURAL)
                }
            )
            DropdownMenuItem(
                text = {
                    Text(stringResource(BeatType.ISOCHRONIC.toResourceString()))
                },
                onClick = {
                    isExpanded = false
                    synthViewModel.changeSynthType(BeatType.ISOCHRONIC)
                }
            )

            DropdownMenuItem(
                text = {
                    Text(stringResource(BeatType.BINAURALMUSIC.toResourceString()))
                },
                onClick = {
                    isExpanded = false
                    synthViewModel.changeSynthType(BeatType.BINAURALMUSIC)
                }
            )
        }

    }
}

//@Preview(showBackground = true, widthDp = 300, heightDp = 400)
//@Composable
//fun SynthTypeMenuPreview(){
//    SynthTypeMenu()
//}
//
