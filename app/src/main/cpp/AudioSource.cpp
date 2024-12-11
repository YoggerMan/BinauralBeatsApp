#define MINIMP3_FLOAT_OUTPUT
#define MINIMP3_IMPLEMENTATION
#include "AudioSource.h"
#include <minimp3.h>
#include <iostream>


namespace synth {
    void AudioSource::setPinkNoise(bool pinkNoise) {
        hasPinkNoise = pinkNoise;
    }
    void AudioSource::setMusicMasking(bool musicMasking) {
        hasMusic = musicMasking;
    }

    void AudioSource::decodeMusicFrame() {

        uint8_t * mp3DataToDecode = &mp3Data[mp3DataIndex];
        mp3dec_frame_info_t frameInfo;
        float mp3Output[MINIMP3_MAX_SAMPLES_PER_FRAME];
        if(mp3DataIndex >= mp3DataSize){
            mp3DataIndex = 0;
            return;
        }

        int numberOfSamples = mp3dec_decode_frame(&mp3, mp3DataToDecode, mp3DataSize, mp3Output, &frameInfo);

        if (numberOfSamples < 0){
            decodedSamples.clear();
            mp3DataIndex = 0;
            return;
        }

        for(int i = 0; i<numberOfSamples; i ++){
            decodedSamples.push_back(mp3Output[i]);
        }

        if( mp3DataIndex < mp3DataSize && frameInfo.frame_bytes >= 0 ) {
            mp3DataIndex += frameInfo.frame_bytes;

        }
    }
}