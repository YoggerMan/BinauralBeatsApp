#pragma once

#include "AudioSource.h"
#include <cmath>

namespace synth{
    class MonauralBeatsGenerator : public AudioSource {

    public:
        MonauralBeatsGenerator();

        std::pair<float,float> getSample() override;

        void onPlaybackStopped() override;

        void setCarrierFrequency(float freq)override;

        void setFrequencyDifference(float freq)override;


    private:
        static int constexpr sampleRate = 48000;
        static float constexpr amp = 0.5f;
        float  frequency = 440;
        float frequencyDifference = 30;
        static float constexpr Pi = M_PI;
        double leftPhaseIncrement = frequency * 2*Pi / (double) sampleRate;
        double rightPhaseIncrement = (frequency+frequencyDifference) * 2*Pi / (double)sampleRate;
        float leftPhase = 0;
        float rightPhase = 0;
    };
}
