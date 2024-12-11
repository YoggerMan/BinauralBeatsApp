#include "jni.h"
#include "memory"

#include "Synth.h"
#include "Log.h"
#include <minimp3.h>
extern "C" {

}
extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_create(JNIEnv *env, jobject thiz) {

    auto synthesizer = std::make_unique<synth::Synth>();

    if(not synthesizer){
        LOGD("Failed to create the Synthesizer, ");
        synthesizer.reset(nullptr);
    }

    return reinterpret_cast<jlong>(synthesizer.release());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_delete(JNIEnv *env, jobject thiz,
                                                     jlong synth_handle) {

    auto* synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);

    if(not synthesizer){
        LOGD("Attempt to an uninitialized synthesizer");
        return;
    }

    delete synthesizer;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_play(JNIEnv *env, jobject thiz, jlong synth_handle) {

    auto* synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if(synthesizer){
        synthesizer->play();
    } else{
        LOGD("Synthesizer not created");
    }

}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_stop(JNIEnv *env, jobject thiz, jlong synth_handle) {

    auto* synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if(synthesizer){
        synthesizer->stop();
    } else{
        LOGD("Synthesizer not created");
    }
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_isPlaying(JNIEnv *env, jobject thiz,
                                                        jlong synth_handle) {

    auto *synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if (synthesizer) {
        return synthesizer->isPlaying();
    } else {
        return false;
    }
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setCarrierFrequency(JNIEnv *env, jobject thiz,
                                                                  jlong synth_handle,
                                                                  jfloat frequency) {
    auto* synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if(synthesizer){
        synthesizer->setCarrierFrequency(static_cast<float>(frequency));
    } else{
        LOGD("Synthesizer not created");
    }
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setSecondaryFrequency(JNIEnv *env, jobject thiz,
                                                                    jlong synth_handle,
                                                                    jfloat frequency) {
    auto *synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if (synthesizer) {
        synthesizer->setSecondaryFrequency(static_cast<float>(frequency));
    } else {
        LOGD("Synthesizer not created");

    }
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setSynthType(JNIEnv *env, jobject thiz,
                                                           jlong synth_handle, jint synth_type) {

    auto *synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    const auto nativeSynthType = static_cast<synth::SynthType>(synth_type);
    if (synthesizer) {
        synthesizer->setSynthType(nativeSynthType);
    } else {
        LOGD("Synthesizer not created");

    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setPinkNoiseMasking(JNIEnv *env, jobject thiz,
                                                                  jlong synth_handle,
                                                                  jboolean has_pink_noise) {
    auto *synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if (synthesizer) {
        synthesizer->setPinkNoiseMasking(static_cast<bool>(has_pink_noise));
    } else {
        LOGD("Synthesizer not created");

    }
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setMusicMasking(JNIEnv *env, jobject thiz,
                                                              jlong synth_handle,
                                                              jboolean has_music) {

    auto *synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if (synthesizer) {
        synthesizer->setMusicMasking(static_cast<bool>(has_music));
    } else {
        LOGD("Synthesizer not created");

    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setOtherMasking(JNIEnv *env, jobject thiz,
                                                              jlong synth_handle,
                                                              jboolean has_other_masking) {


    auto* synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if(synthesizer){
        synthesizer->setOtherMasking(static_cast<bool>(has_other_masking));
    } else{
        LOGD("Synthesizer not created");


    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setPlayingState(JNIEnv *env, jobject thiz,
                                                              jlong synth_handle,
                                                              jboolean is_playing) {
    auto *synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if (synthesizer) {
        synthesizer->setPlayingState(static_cast<bool>(is_playing));
    } else {
        LOGD("Synthesizer not created");

    }
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setAudioFileByteArray(JNIEnv *env, jobject thiz,
                                                                    jlong synth_handle,
                                                                    jbyteArray byte_array) {

    jsize length = env->GetArrayLength(byte_array);
    jbyte * buffer  = env->GetByteArrayElements(byte_array, nullptr);

    auto *synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if (synthesizer) {
        synthesizer->setAudioFileByteArray(reinterpret_cast<uint8_t *>(buffer), reinterpret_cast<int>(length));
    } else {
        LOGD("Synthesizer not created");

    }

    env->ReleaseByteArrayElements(byte_array, buffer, JNI_ABORT);


}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_binauralbeatsapp_NativeSynth_setMixing(JNIEnv *env, jobject thiz,
                                                        jlong synth_handle, jfloat masking_mix) {
    auto *synthesizer = reinterpret_cast<synth::Synth*>(synth_handle);
    if (synthesizer) {
        synthesizer->setMixing(static_cast<float >(masking_mix));
    } else {
        LOGD("Synthesizer not created");

    }

}