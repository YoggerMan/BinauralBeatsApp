package com.example.binauralbeatsapp

interface Synth {
    suspend fun play()
    suspend fun stop()
    suspend fun isPlaying(): Boolean
    suspend fun setCarrierFrequency(frequency: Float)
    suspend fun setSecondaryFrequency(frequency: Float)
    suspend fun setPinkNoiseMasking(hasPinkNoise: Boolean)
    suspend fun setSynthType(synthType: BeatType)
    suspend fun setMusicMasking(hasMusic: Boolean)
    suspend fun setOtherMasking(hasOtherMasking: Boolean)
    suspend fun setPlayingState(playingState: Boolean)
    suspend fun setAudioFileByteArray(byteArray: ByteArray)
    suspend fun setMix(maskingMix: Float)

}