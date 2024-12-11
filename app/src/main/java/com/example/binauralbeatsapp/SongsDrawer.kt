package com.example.binauralbeatsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.binauralbeatsapp.data.songDataList
import com.example.binauralbeatsapp.data.RawStringPair



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsDrawer(synthViewModel: MainViewModel) {


    val synthState = synthViewModel.synthState.observeAsState()
    val fileIndex = synthState.value?.chosenAudioFile
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    )
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // State to track the currently selected song
    var selectedSongIndex by remember { mutableStateOf(0) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            // Song List in Bottom Sheet
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(songDataList) { index, song ->
                    val songTitle = stringResource(id = song.text)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedSongIndex = index
                                synthViewModel.setFile(songDataList[selectedSongIndex].raw, context)
                                coroutineScope.launch { scaffoldState.bottomSheetState.partialExpand() }
                            }
                            .padding(16.dp)
                    ) {
                        Text(text = songTitle)
                    }
                }
            }
        },
        sheetPeekHeight = 64.dp // Keep a portion of the collapsed state visible
    ) {
        // Main Content with Selected Song
        val selectedSongTitle = stringResource(id = songDataList[selectedSongIndex].text)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        if (scaffoldState.bottomSheetState.hasPartiallyExpandedState) {
                            scaffoldState.bottomSheetState.expand()
                        } else {
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                    }
                }
            ) {
                Text(
                    text = selectedSongTitle ,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (scaffoldState.bottomSheetState.hasExpandedState) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = "Toggle song list"
                )
            }
        }
    }
}


