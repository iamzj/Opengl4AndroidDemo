package com.jeryzhang.opengl.opengl4androiddemo.ndk;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class NativeSimpleRenderer implements GLSurfaceView.Renderer {

    static {
        System.loadLibrary("native_simple");
    }


    public NativeSimpleRenderer() {
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        surfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        surfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        onDrawFrame();
    }

    public native void surfaceCreated();

    public native void surfaceChanged(int width, int height);

    public native void onDrawFrame();
}
