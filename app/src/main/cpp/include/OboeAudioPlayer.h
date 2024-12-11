#pragma once

#include <oboe/Oboe.h>
#include "AudioPlayer.h"
#include "AudioSource.h"

namespace synth {

    class OboeAudioPlayer : public oboe::AudioStreamDataCallback, public synth::AudioPlayer {
    public:
        OboeAudioPlayer(std::shared_ptr<synth::AudioSource> source, int sampleRate);

        ~OboeAudioPlayer();


        oboe::DataCallbackResult
        onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) override;

        int32_t play() override;

        void stop() override;

        void setSource(std::shared_ptr<synth::AudioSource> source);




    private:
        std::shared_ptr<synth::AudioSource> source;
        std::shared_ptr<oboe::AudioStream> stream;
        int sampleRate;
        int channelCount = 2;
        std::mutex _lock;

    };
}