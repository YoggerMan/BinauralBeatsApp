#pragma once

#include "AudioSource.h"
#include <cmath>

namespace synth{
    class IsochronicTonesGenerator : public AudioSource {

    public:
        IsochronicTonesGenerator();

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
        double carrierPhaseIncrement = frequency * 2*Pi / (double) sampleRate;
        double pulsePhaseIncrement = frequencyDifference* 2*Pi / (double) sampleRate;
        float carrierPhase = 0;
        float pulsePhase = 0;
    };
}