#pragma once
#include <memory>
#include "AudioSource.h"
#include "AudioPlayer.h"
#include "BinauralBeatsGenerator.h"
#include "MonauralBeatsGenerator.h"
#include "OboeAudioPlayer.h"
#include "IsochronicTonesGenerator.h"

namespace synth{
    enum class SynthType{
        MONAURAL, BINAURAL, ISCHRONIC, BINAURAL_MUSIC
    };

    class Synth{
    public:
        Synth();
        ~Synth();

        void play();
        void stop();
        bool isPlaying();
        void setCarrierFrequency(float frequency);
        void setSecondaryFrequency(float frequency);
        void setPinkNoiseMasking(bool hasPinkNoise);
        void setMusicMasking(bool hasMusic);
        void setOtherMasking(bool hasOtherMasking);
        void setSynthType(SynthType synthType);
        void setPlayingState(bool playingState);
        void setAudioFileByteArray(uint8_t * mp3Data, int mp3DataSize);
        void setMixing(float maskingMix);

    private:
        std::shared_ptr<MonauralBeatsGenerator> monauralGenerator;
        std::shared_ptr<BinauralBeatsGenerator> binauralGenerator;
        std::shared_ptr<IsochronicTonesGenerator> isochronicTonesGenerator;
        std::shared_ptr<AudioSource> audioSource;
        std::unique_ptr<OboeAudioPlayer> audioPlayer;
        std::vector<uint8_t> audioFileData;
        bool playingState = false;
        float carrierFrequency = 0;
        float secondaryFrequency = 0;
    };
}