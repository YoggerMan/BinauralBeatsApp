package com.example.binauralbeatsapp

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NativeSynth : Synth, DefaultLifecycleObserver {
    private var synthHandle: Long = 0
    private val synthMutex = Object()

    private external fun create(): Long
    private external fun delete(synthHandle: Long)

    private external fun play(synthHandle: Long)
    private external fun stop(synthHandle: Long)
    private external fun isPlaying(synthHandle: Long): Boolean
    private external fun setCarrierFrequency(synthHandle: Long, frequency: Float)
    private external fun setSecondaryFrequency(synthHandle: Long, frequency: Float)
    private external fun setSynthType(synthHandle: Long, synthType: Int)
    private external fun setPinkNoiseMasking(synthHandle: Long, hasPinkNoise: Boolean)
    private external fun setMusicMasking(synthHandle: Long, hasMusic: Boolean )
    private external fun setOtherMasking(synthHandle: Long, hasOtherMasking: Boolean)
    private external fun setPlayingState(synthHandle: Long, playingState: Boolean)
    private external fun setAudioFileByteArray(synthHandle: Long, byteArray: ByteArray)
    private external fun setMixing(synthHandle: Long, maskingMix: Float)

    companion object{
        init {
            System.loadLibrary("binauralbeatsapp")
        }
    }

    override fun onResume(owner: LifecycleOwner){
        super.onResume(owner)

        synchronized(synthMutex){
            createNativeHandleIfNotExists()
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        synchronized(synthMutex){
            if(synthHandle == 0L){
                return
            }
            delete(synthHandle)
            synthHandle = 0
        }
    }

    private fun createNativeHandleIfNotExists(){
        if (synthHandle != 0L) {
            return
        }
        synthHandle = create()

    }

    override suspend fun play() = withContext(Dispatchers.Default) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            play(synthHandle)
        }
    }

    override suspend fun stop() = withContext(Dispatchers.Default) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            stop(synthHandle)
        }
    }

    override suspend fun isPlaying(): Boolean = withContext(Dispatchers.Default) {
        synchronized(synthMutex) {
            createNativeHandleIfNotExists()
            return@withContext isPlaying(synthHandle)
        }
    }

    override suspend fun setCarrierFrequency(frequency: Float) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setCarrierFrequency(synthHandle, frequency)
        }
    }

    override suspend fun setSecondaryFrequency(frequency: Float) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setSecondaryFrequency(synthHandle, frequency)
        }
    }

    override suspend fun setPinkNoiseMasking(hasPinkNoise: Boolean) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setPinkNoiseMasking(synthHandle, hasPinkNoise)
        }
    }

    override suspend fun setSynthType(synthType: BeatType) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setSynthType(synthHandle, synthType.ordinal)
        }
    }

    override suspend fun setMusicMasking(hasMusic: Boolean) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setMusicMasking(synthHandle, hasMusic)
        }
    }

    override suspend fun setOtherMasking(hasOtherMasking: Boolean) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setOtherMasking(synthHandle, hasOtherMasking)
        }
    }

    override suspend fun setPlayingState(playingState: Boolean) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setPlayingState(synthHandle, playingState)
        }
    }

    override suspend fun setAudioFileByteArray(byteArray: ByteArray) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setAudioFileByteArray(synthHandle, byteArray)
        }
    }

    override suspend fun setMix(maskingMix: Float) {
        synchronized(synthMutex){
            createNativeHandleIfNotExists()
            setMixing(synthHandle, maskingMix)
        }
    }
}