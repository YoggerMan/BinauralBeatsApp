package com.example.binauralbeatsapp

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import java.io.File


enum class BeatType{
    MONAURAL{
        override fun toResourceString(): Int {
            return R.string.monaural
        }
    },
    BINAURAL{
        override fun toResourceString(): Int {
            return R.string.binaural
        }
    },
    ISOCHRONIC{
        override fun toResourceString(): Int {
            return R.string.isochronic
        }
    },
    BINAURALMUSIC{
        override fun toResourceString(): Int {
            return R.string.music
        }
    };

    @StringRes
    abstract fun toResourceString(): Int
}



data class SynthState (
    val type: BeatType = BeatType.BINAURAL,
    val hasPinkNoise: Boolean = false,
    val hasMusic:Boolean = false,
    val hasOtherMasking: Boolean = false,
    val freq: Float = 300f,
    val secondFreq: Float = 0f,
    val freqDiff: Float = 0f,
    val isPlaying: Boolean = false,
    val maskingMix: Float = 0.5f,
    val chosenAudioFile: Int = R.raw.a_tribe_called_quest_bonita_applebum
)

