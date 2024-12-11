package com.example.binauralbeatsapp

import android.content.Context
import android.content.res.Resources
import android.media.MediaCodec
import android.media.MediaPlayer
import android.provider.MediaStore.Audio
import androidx.annotation.RawRes
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ControlledComposition
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import kotlin.math.*


class MainViewModel : ViewModel(){

    private val _synthState = MutableLiveData(
        SynthState()
    )
    var synth: Synth?= null
        set(value){
            field = value
            applyParameters()
        }

    val synthState: LiveData<SynthState>
        get() {
          return _synthState
        }

    fun setFreq(freq: Float){
        _synthState.value = _synthState.value?.copy(freq = freq)
        viewModelScope.launch {
            synth?.setCarrierFrequency(freq)
        }
    }

    fun setSecondFreq(freq: Float){
        _synthState.value = _synthState.value?.copy(secondFreq = freq)
        viewModelScope.launch {
            synth?.setSecondaryFrequency(freq)
        }

    }
    fun setFreqDifference(freqDifference: Float){
        _synthState.value = _synthState.value?.copy(freqDiff = freqDifference)
    }
    fun changeSynthType(beatType: BeatType){
        _synthState.value = _synthState.value?.copy(type = beatType)
        viewModelScope.launch {
            synth?.setSynthType(beatType)
            synth?.setCarrierFrequency(synthState.value?.freq!!);
            synth?.setSecondaryFrequency(synthState.value?.secondFreq!!);
        }

    }
    fun changePinkNoiseMasking(hasPinkNoise: Boolean){
        _synthState.value = _synthState.value?.copy(hasPinkNoise = hasPinkNoise)
        viewModelScope.launch {
            synth?.setPinkNoiseMasking(hasPinkNoise)
        }

    }
    fun changeMusicMasking(hasMusic: Boolean){
        _synthState.value = _synthState.value?.copy(hasMusic = hasMusic)
        viewModelScope.launch {
            synth?.setMusicMasking(hasMusic)
        }

    }
    fun changeOtherMasking(hasOtherMasking:Boolean){
        _synthState.value = _synthState.value?.copy(hasOtherMasking = hasOtherMasking)
        viewModelScope.launch {
            synth?.setOtherMasking(hasOtherMasking)
        }

    }
    fun setFile(fileIndex: Int, context: Context){

        _synthState.value = _synthState.value?.copy(chosenAudioFile = fileIndex)
        val fileInputStream = context.resources.openRawResource(fileIndex)
        val byteArray = fileInputStream.readBytes()
        viewModelScope.launch {
            synth?.setAudioFileByteArray(byteArray)
        }
    }

    fun setMix(maskingMix: Float){
        _synthState.value = _synthState.value?.copy(maskingMix = maskingMix)
        viewModelScope.launch {
            synth?.setMix(maskingMix)
        }
    }



    fun changePlayingState(playingState: Boolean){
        _synthState.value = _synthState.value?.copy(isPlaying = playingState)
        viewModelScope.launch {
            if(playingState){
                synth?.setPlayingState(playingState)
                synth?.play()
            }
            else{
                synth?.setPlayingState(playingState)
                synth?.stop()
            }
        }

    }



    fun setFrequencySliderPosition(sliderPosition: Float){
        val freqInLogScale = transformSliderPositionToFreq(sliderPosition)
        setFreq(freqInLogScale)
    }

    fun applyParameters(){
        viewModelScope.launch {
            synth?.setSynthType(synthState.value?.type!!)
            synth?.setCarrierFrequency(synthState.value?.freq!!)
            synth?.setSecondaryFrequency(synthState.value?.secondFreq!!)
            synth?.setMusicMasking(synthState.value?.hasMusic!!)
            synth?.setPinkNoiseMasking(synthState.value?.hasPinkNoise!!)
            synth?.setOtherMasking(synthState.value?.hasOtherMasking!!)
            synth?.setPlayingState(synthState.value?.isPlaying!!)
        }
    }

    private val carrierFrequencyRange = 300f..900f
    private val freqDifferenceRange = 1f..40f
    private val pulseFreqDifference = 1f..40f

    private fun transformSliderPositionToFreq(sliderPosition: Float) : Float {
        val freq = linearToLogTransformation(base = 10f, value = sliderPosition, range = carrierFrequencyRange)
        return freq
    }

    fun transformFreqToSliderPosition(freq: Float): Float{
        val sliderPosition = logToLinearTransformation(range = carrierFrequencyRange, freq)
        return sliderPosition
    }

    companion object LinearToLogScaleConverter{
        private const val MINIMUM_VALUE = 0.001f

        fun linearToLogTransformation(
            base: Float,
            value: Float,
            range: ClosedFloatingPointRange<Float>): Float{
            assert(value in 0f..1f)
            var relativeDistance = 0f

            if(value < MINIMUM_VALUE){
                relativeDistance = 0f
            }
            else{
                relativeDistance = base.pow(log(MINIMUM_VALUE,base)-log(MINIMUM_VALUE,base)*value)
            }
            assert(relativeDistance in 0f..1f)
            return range.start + (range.endInclusive - range.start) * relativeDistance
        }

        fun logToLinearTransformation(range: ClosedFloatingPointRange<Float>, freq: Float): Float {
            assert(freq in range)
            val relativeDistance = (freq - range.start)/(range.endInclusive - range.start)
            assert(relativeDistance in 0f..1f)
            if(relativeDistance < MINIMUM_VALUE){
                return relativeDistance
            }
            return log(relativeDistance, base = 10f) - log(MINIMUM_VALUE, base = 10f) / (-log(MINIMUM_VALUE, base = 10f))

        }
    }
}



