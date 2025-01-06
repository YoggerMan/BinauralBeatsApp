#include "Log.h"
#include "Synth.h"
#include "BinauralBeatsGenerator.h"
#include "OboeAudioPlayer.h"



namespace synth {

    Synth::Synth()  {

        binauralGenerator = std::make_shared<BinauralBeatsGenerator>();
        monauralGenerator = std::make_shared<MonauralBeatsGenerator>();
        isochronicTonesGenerator = std::make_shared<IsochronicTonesGenerator>();
        audioSource = binauralGenerator;
        audioPlayer = std::make_unique<OboeAudioPlayer>(audioSource, 22050);
    }

    Synth::~Synth() = default;

    void Synth::play(){

        auto result = audioPlayer->play();
        if(result == 0){
            LOGD("Synth is playing");
            setPlayingState(true);
        }
        else{
            LOGD("Failed to start playing");
        }
    }

    void Synth::stop() {
        audioPlayer->stop();
        setPlayingState(false);
    }

    bool Synth::isPlaying() {
        return playingState;
    }

    void Synth::setPlayingState(bool playingState) {
        this->playingState = playingState;
    }

    void Synth::setCarrierFrequency(float frequency) {
        LOGD("Set Carrier Frequency = ", frequency);
        audioSource->setCarrierFrequency(frequency);
    }

    void Synth::setSecondaryFrequency(float frequency) {
        LOGD("Set Secondary Frequency", frequency);
        audioSource->setFrequencyDifference(frequency);
    }
    void Synth::setPinkNoiseMasking(bool hasPinkNoise) {
        LOGD("Pink Noise button Pressed");
        audioSource->setPinkNoise(hasPinkNoise);
    }
    void Synth::setMusicMasking(bool hasMusic) {
        LOGD("Music Masking button Pressed");
        audioSource->setMusicMasking(hasMusic);
    }
    void Synth::setSynthType(synth::SynthType synthType) {
        if(synthType == SynthType::BINAURAL){
            audioSource = binauralGenerator;
            audioPlayer->setSource(audioSource);
        } else if(synthType == SynthType::MONAURAL){
            audioSource = monauralGenerator;
            audioPlayer->setSource(audioSource);
        } else{
            audioSource = isochronicTonesGenerator;
            audioPlayer->setSource(audioSource);
        }
    }
    void Synth::setOtherMasking(bool hasOtherMasking) {
        LOGD("Other masking button Pressed");
    }

    void Synth::setAudioFileByteArray(uint8_t * mp3Data, int mp3DataSize){

        audioFileData.clear();

        audioFileData.assign(mp3Data, mp3Data+mp3DataSize);

        audioSource->mp3Data = audioFileData.data();
        audioSource->mp3DataSize = mp3DataSize;
        audioSource->mp3DataIndex = 0;
    }

    void Synth::setMixing(float maskingMix) {
        LOGD("Changed Masking Mix");
        audioSource->maskingMix = maskingMix;
    }
}