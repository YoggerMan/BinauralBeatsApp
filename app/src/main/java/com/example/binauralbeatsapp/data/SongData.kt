package com.example.binauralbeatsapp.data

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.example.binauralbeatsapp.R

data class RawStringPair(
    @RawRes val raw:Int,
    @StringRes val text:Int
    )


val songDataList = listOf(
    R.raw.a_tribe_called_quest_bonita_applebum to R.string.song_a_tribe_called_quest_bonita_applebum,
    R.raw.aaron_neville_tell_it_like_it_is to R.string.song_aaron_neville_tell_it_like_it_is,
    R.raw.abba_sos to R.string.song_abba_sos,
    R.raw.abc_poison_arrow to R.string.song_abc_poison_arrow,
    R.raw.ac_dc_dirty_deeds_done_dirt_cheap to R.string.song_ac_dc_dirty_deeds_done_dirt_cheap,
    R.raw.adam_and_the_ants_prince_charming to R.string.song_adam_and_the_ants_prince_charming,
    R.raw.adam_ant_wonderful to R.string.song_adam_ant_wonderful,
    R.raw.adverts_gary_gilmores_eyes to R.string.song_adverts_gary_gilmores_eyes,
    R.raw.aerobic_jonquil_sweat_machine to R.string.song_aerobic_jonquil_sweat_machine,
    R.raw.aerosmith_dude_looks_like_a_lady to R.string.song_aerosmith_dude_looks_like_a_lady,
    R.raw.aimee_mann_wise_up to R.string.song_aimee_mann_wise_up,
    R.raw.air_sexy_boy to R.string.song_air_sexy_boy,
    R.raw.al_green_sha_la_la_make_me_happy to R.string.song_al_green_sha_la_la_la_make_me_happy,
    R.raw.alanis_morissette_thank_u to R.string.song_alanis_morissette_thank_u,
    R.raw.alice_cooper_elected to R.string.song_alice_cooper_elected,
    R.raw.alice_in_chains_no_excuses to R.string.song_alice_in_chains_no_excuses,
    R.raw.alicia_keys_fallin to R.string.song_alicia_keys_fallin,
    R.raw.allman_brothers_band_melissa to R.string.song_allman_brothers_band_melissa,
    R.raw.altered_images_dont_talk_to_me_about_love to R.string.song_altered_images_dont_talk_to_me_about_love,
    R.raw.american_music_club_jesus_hands to R.string.song_american_music_club_jesus_hands
).map { RawStringPair(it.first, it.second)}
