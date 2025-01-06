#include "MonauralBeatsGenerator.h"


namespace synth{

    MonauralBeatsGenerator::MonauralBeatsGenerator() = default;

    std::pair<float,float> MonauralBeatsGenerator::getSample() {

        std::pair<float,float> sample;
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

        float pinkNoiseSample = 0;
        if(hasPinkNoise){
            pinkNoiseSample = pinkNoiseGenerator.getSample();
        }

        float leftSample = amp*sin(leftPhase);
        float rightSample = amp*sin(rightPhase);
        leftPhase += (float)leftPhaseIncrement;
        rightPhase += (float)rightPhaseIncrement;
        if (leftPhase > 2*M_PI ){
            leftPhase -= 2*M_PI;
        }
        else if(rightPhase > 2*M_PI){
            rightPhase -= 2*M_PI;
        }
        float sum = 0.5*(leftSample + rightSample);
        sample.first = (1-maskingMix)*sum + maskingMix*musicSample.first + maskingMix*pinkNoiseSample;
        sample.second = (1-maskingMix)*sum + maskingMix*musicSample.first + maskingMix*pinkNoiseSample;

        return sample;
    }

    void MonauralBeatsGenerator::onPlaybackStopped() {
        leftPhase = 0;
        rightPhase = 0;
    }

    void MonauralBeatsGenerator::setCarrierFrequency(float freq) {
        frequency = freq;
        leftPhaseIncrement = frequency * 2*Pi / (double) sampleRate;
        rightPhaseIncrement = (frequency+frequencyDifference) * 2*Pi / (double)sampleRate;

    }

    void MonauralBeatsGenerator::setFrequencyDifference(float freq) {
        frequencyDifference = freq;
        leftPhaseIncrement = frequency * 2*Pi / (double) sampleRate;
        rightPhaseIncrement = (frequency+frequencyDifference) * 2*Pi / (double)sampleRate;
    }
}
