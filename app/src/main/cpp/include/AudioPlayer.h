#pragma once

#include <oboe/Oboe.h>

namespace synth{
    class AudioPlayer{
    public:
        virtual ~AudioPlayer() = default;
        virtual int32_t play() = 0;
        virtual void stop() = 0;


    };
}