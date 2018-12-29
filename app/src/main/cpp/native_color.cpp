//
// Created by zhangjian on 2018/12/29.
//
#include <jni.h>
#include "native_color.h"
#include <EGL/egl.h>
#include <GLES3/gl3.h>

/*
 * Class:     com_jeryzhang_opengl_opengl4androiddemo_NativeColorRenderer
 * Method:    surfaceCreated
 * Signature: (I)V
 */
JNIEXPORT void JNICALL
Java_com_jeryzhang_opengl_opengl4androiddemo_ndk_NativeColorRenderer_surfaceCreated
        (JNIEnv *env, jobject obj, jint color) {
    GLfloat redF = ((color >> 16) & 0xFF) * 1.0f / 255;
    GLfloat greenF = ((color >> 8) & 0xFF) * 1.0f / 255;
    GLfloat blueF = (color & 0xFF) * 1.0f / 255;
    GLfloat alphaF = ((color >> 24) & 0xFF) * 1.0f / 255;
    glClearColor(redF, greenF, blueF, alphaF);
}

/*
 * Class:     com_jeryzhang_opengl_opengl4androiddemo_NativeColorRenderer
 * Method:    surfaceChanged
 * Signature: (II)V
 */
JNIEXPORT void JNICALL
Java_com_jeryzhang_opengl_opengl4androiddemo_ndk_NativeColorRenderer_surfaceChanged
        (JNIEnv *env, jobject obj, jint width, jint height) {
    glViewport(0, 0, width, height);
}

/*
 * Class:     com_jeryzhang_opengl_opengl4androiddemo_NativeColorRenderer
 * Method:    onDrawFrame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_jeryzhang_opengl_opengl4androiddemo_ndk_NativeColorRenderer_onDrawFrame
        (JNIEnv *env, jobject obj) {
    glClear(GL_COLOR_BUFFER_BIT);
}