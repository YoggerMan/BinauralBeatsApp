#pragma once

#include <oboe/Oboe.h>
#include <vector>
#include <utility>
#include <minimp3.h>
#include "PinkNoiseGenerator.h"

namespace synth{
    class AudioSource{
    public:
        AudioSource() = default;
        ~AudioSource() = default;
        virtual std::pair<float,float> getSample() = 0;
        virtual void onPlaybackStopped() = 0;
        virtual void setCarrierFrequency(float freq) = 0;
        virtual void setFrequencyDifference(float freq) = 0;

        virtual void setPinkNoise(bool hasPinkNoise) ;
        virtual void setMusicMasking(bool hasMusic);
        void decodeMusicFrame();
        bool hasMusic = false;
        bool hasPinkNoise = false;
        std::vector<float> decodedSamples;
        uint8_t* mp3Data;
        int mp3DataSize = 0;
        int mp3DataIndex = 0;
        mp3dec_t mp3;
        float maskingMix = 0.5;
        PinkNoiseGenerator pinkNoiseGenerator = PinkNoiseGenerator();


    };
}