#include "OboeAudioPlayer.h"
#include "AudioSource.h"

using namespace oboe;

namespace synth{
    OboeAudioPlayer::OboeAudioPlayer(std::shared_ptr<synth::AudioSource> source, int sampleRate) {
        this->source = (std::move(source));
        this->sampleRate = sampleRate;
    }

    OboeAudioPlayer::~OboeAudioPlayer() {
        OboeAudioPlayer::stop();
    }

    int32_t OboeAudioPlayer::play() {
        AudioStreamBuilder builder;

         auto result = builder.setPerformanceMode(PerformanceMode::LowLatency)
                ->setChannelCount(channelCount)
                ->setDirection(Direction::Output)
                ->setSampleRate(sampleRate)
                ->setDataCallback(this)
                ->setSharingMode(SharingMode::Exclusive)
                ->setFormat(oboe::AudioFormat::Float)
                ->setSampleRateConversionQuality(oboe::SampleRateConversionQuality::Medium)
                ->openStream(stream);
        if(result != Result::OK) return (int32_t )result;

        result = stream->requestStart();

        return (int32_t)result;
    }

    void OboeAudioPlayer::stop() {
        std::lock_guard<std::mutex> lock(_lock);
        if(stream){
            stream->stop();
            stream->close();
            stream.reset();
        }
        source->onPlaybackStopped();
    }

    oboe::DataCallbackResult
    OboeAudioPlayer::onAudioReady(oboe::AudioStream *audioStream, void *audioData,
                                  int32_t numFrames) {

        auto * floatData = reinterpret_cast<float*>(audioData);
        for(int frame = 0; frame < numFrames; ++frame){
            const auto sample = source->getSample();

            floatData[frame*channelCount] = sample.first;
            floatData[frame*channelCount + 1] = sample.second;
        }
        return oboe::DataCallbackResult::Continue;
    }

    void OboeAudioPlayer::setSource(std::shared_ptr<synth::AudioSource> source) {
        this->source = source;
    }








}