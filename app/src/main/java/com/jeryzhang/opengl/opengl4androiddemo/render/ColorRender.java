package com.jeryzhang.opengl.opengl4androiddemo.render;

import android.graphics.Color;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * https://blog.csdn.net/byhook/article/details/83716870
 */

public class ColorRender implements GLSurfaceView.Renderer {
    private int color;

    public ColorRender(int color) {
        this.color = color;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        float red = 1.0f * Color.red(color) / 255;
        float green = 1.0f * Color.red(color) / 255;
        float blue = 1.0f * Color.red(color) / 255;
        float alpha = 1.0f * Color.red(color) / 255;
        GLES30.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
}