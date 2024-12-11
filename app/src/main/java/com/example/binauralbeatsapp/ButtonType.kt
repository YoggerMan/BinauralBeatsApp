package com.example.binauralbeatsapp

import androidx.annotation.StringRes

enum class ButtonType {
    PINK_NOISE{
        override fun toResourceString(): Int {
            return R.string.pink
        }
    },
    MUSIC_MASK{
        override fun toResourceString(): Int {
            return R.string.music_mask
        }
    },
    OTHER{
        override fun toResourceString(): Int {
            return R.string.other
        }
    };

    @StringRes
    abstract fun toResourceString(): Int
}