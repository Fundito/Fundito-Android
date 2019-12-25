//
// Created by 문명주 on 2019-12-22.
//

#include <jni.h>
#include "android/log.h"

/**
 * Log Macro
 */
#define LOGTAG "Native"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOGTAG,__VA_ARGS__)

extern "C" {
    JNIEXPORT jstring JNICALL Java_com_fundito_fundito_common_util_NativeUtil_sayHello(JNIEnv* jniEnv, jobject obj) {
        LOGE("Hello! from NDK! %d",1);
        return jniEnv->NewStringUTF("Hello World!");
    }
}