package com.jeryzhang.opengl.opengl4androiddemo.activity;

import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jeryzhang.opengl.opengl4androiddemo.ndk.NativeColorRenderer;
import com.jeryzhang.opengl.opengl4androiddemo.ndk.NativeSimpleRenderer;
import com.jeryzhang.opengl.opengl4androiddemo.render.ColorRender;
import com.jeryzhang.opengl.opengl4androiddemo.render.RectangleRenderer;
import com.jeryzhang.opengl.opengl4androiddemo.render.SimpleRenderer;
import com.jeryzhang.opengl.opengl4androiddemo.render.UniformRenderer;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        setContentView(mGLSurfaceView);
        mGLSurfaceView.setEGLContextClientVersion(3);

//        mGLSurfaceView.setRenderer(new ColorRender(Color.GRAY));
//        mGLSurfaceView.setRenderer(new NativeColorRenderer(Color.GRAY));
//        mGLSurfaceView.setRenderer(new SimpleRenderer());
//        mGLSurfaceView.setRenderer(new NativeSimpleRenderer());
//        mGLSurfaceView.setRenderer(new RectangleRenderer());
        mGLSurfaceView.setRenderer(new UniformRenderer(this));

    }

}
