//
// Created by 문명주 on 2019-12-22.
//

#include <jni.h>

extern "C" {
    JNIEXPORT jstring JNICALL Java_com_fundito_fundito_common_util_NativeUtil_sayHello(JNIEnv* jniEnv, jobject obj) {
        return jniEnv->NewStringUTF("Hello World!");
    }
}