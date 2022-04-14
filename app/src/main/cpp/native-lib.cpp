#include <jni.h>
#include <string>
#include <android/log.h>

#ifndef LOG_TAG
#define LOG_TAG "HELLO_JNI"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG ,__VA_ARGS__) // 定义LOGF类型
#endif

extern "C"
JNIEXPORT jstring JNICALL
Java_com_miyako_wannews_util_JNIUtils_getPsShell(JNIEnv *env, jclass clazz, jobjectArray pkg_arr) {
    FILE* in = NULL;
    FILE* out = NULL;
    char* cmd = "ps -A";
    in = popen(cmd, "r");
//    out = fopen("proc.txt","w");

    char buf[1024];

    memset(buf, '\0', sizeof(buf));

    if (in == NULL) {
        pclose(in);
//        fclose(out);
        return env->NewStringUTF("shell exec fail!");
    }

    LOGD("start exec cmd %s:", cmd);

    while (fgets(buf, sizeof(buf), in) != NULL) {
//        fread(buf, sizeof(char), sizeof(buf), in);//把返回标准I/O流内的内容读到buf中
        LOGD("-->%s", buf);
//        fwrite(buf, sizeof(char), strlen(buf), out);
    }

    pclose(in);
//    fclose(out);

    return env->NewStringUTF("fget");
}