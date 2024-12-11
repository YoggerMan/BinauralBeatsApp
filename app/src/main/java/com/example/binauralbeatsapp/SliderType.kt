package com.example.binauralbeatsapp

import androidx.annotation.StringRes

enum class SliderType {

    CARRIER{
        override fun toResourceString(): Int {
            return R.string.carrier
        }
    },
    DIFF{
        override fun toResourceString(): Int {
            return R.string.freq_diff
        }
    },
    PULSE{
        override fun toResourceString(): Int {
            return R.string.pulse
        }
    },
    MIX{
        override fun toResourceString(): Int {
            return R.string.mix
        }
    };

    @StringRes
    abstract fun toResourceString(): Int
}