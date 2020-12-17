//
// Created by ariqbasyar on 16/12/2020.
//
#include <jni.h>
#include <string>
#include <sstream>

extern "C" {
    using namespace std;

    #define ll long long

    JNIEXPORT jstring JNICALL
    Java_id_ac_ui_cs_mobileprogramming_muhammad_1ariq_1basyar_speedcooking_ui_stopwatch_ElapsedTime_parseToString(
            JNIEnv* env,
            jobject obj,
            jlong duration) {
        ll milliseconds = duration % 1000;
        duration = duration / (ll) 1e3;
        ll seconds = (duration) % 60;
        ll minutes = (duration / 60) % 60;
        ll hours = (duration / 3600) % 100;
        char a [13];
        sprintf(a, "%02lld:%02lld:%02lld %03lld", hours, minutes, seconds, milliseconds);
        return env->NewStringUTF(a);
    }
}
