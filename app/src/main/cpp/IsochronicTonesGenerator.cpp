#pragma once

#include "IsochronicTonesGenerator.h"
#include "math.h"

namespace synth{

    IsochronicTonesGenerator::IsochronicTonesGenerator() = default;

    std::pair<float,float> IsochronicTonesGenerator::getSample() {

        std::pair<float, float> sample;
        std::pair<float,float> musicSample;
        if(decodedSamples.size() < 10){
            decodeMusicFrame();
        }
        if(decodedSamples.empty()){
            musicSample.first = 0;
            musicSample.second = 0;
            mp3DataIndex = 0;
        }

        if(!decodedSamples.empty()) {
            if(hasMusic) {
                musicSample.first = decodedSamples.front();
                decodedSamples.erase(decodedSamples.begin());
            }else{
                musicSample.first = 0;
                musicSample.second = 0;
            }
        }

        float pinkNoiseSample = pinkNoiseGenerator.getSample();

        float carrierSample = amp * sin(carrierPhase);
        float pulseSample;
        if (pulsePhase < Pi) {
            pulseSample = 1;
        }else if (pulsePhase == round(Pi)) {
            pulseSample = 0.5;
        }else{
                pulseSample = 0;
                carrierPhase = 0;
        }
        carrierPhase += (float)carrierPhaseIncrement;
        pulsePhase += (float)pulsePhaseIncrement;
        if (carrierPhase > 2*M_PI ){
            carrierPhase -= 2*M_PI;
        }
        if(pulsePhase > 2*M_PI){
            pulsePhase -= 2*M_PI;
        }
        float pulsedSample = pulseSample*carrierSample;
        sample.first = (1-maskingMix)*pulsedSample + maskingMix*musicSample.first + maskingMix*pinkNoiseSample;
        sample.second = (1-maskingMix)*pulsedSample + maskingMix*musicSample.first + maskingMix*pinkNoiseSample;

        return sample;
    }

    void IsochronicTonesGenerator::onPlaybackStopped() {
        carrierPhase = 0;
        pulsePhase = 0;
    }

    void IsochronicTonesGenerator::setCarrierFrequency(float freq) {
        frequency = freq;
        carrierPhaseIncrement = frequency * 2*Pi / (double) sampleRate;
        pulsePhaseIncrement = (frequencyDifference) * 2*Pi / (double)sampleRate;

    }

    void IsochronicTonesGenerator::setFrequencyDifference(float freq) {
        frequencyDifference = freq;
        carrierPhaseIncrement = frequency * 2*Pi / (double) sampleRate;
        pulsePhaseIncrement = (frequencyDifference) * 2*Pi / (double)sampleRate;
    }
}

