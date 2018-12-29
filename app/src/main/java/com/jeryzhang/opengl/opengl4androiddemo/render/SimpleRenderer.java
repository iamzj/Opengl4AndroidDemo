package com.jeryzhang.opengl.opengl4androiddemo.render;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.jeryzhang.opengl.opengl4androiddemo.util.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * https://blog.csdn.net/byhook/article/details/83719500
 */
public class SimpleRenderer implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 3;

    private final FloatBuffer vertexBuffer;

    private final FloatBuffer colorBuffer;

    private int mProgram;
    private float[] vertexPoints = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };
    private float color[] = {
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    /**
     * 顶点着色器
     */
    private String vertextShader =
            "#version 300 es \n" +
                    "layout (location = 0) in vec4 vPosition;\n"//输入一个名为vPosition的4分量向量，layout (location = 0)表示这个变量的位置是顶点属性0。
                    + "layout (location = 1) in vec4 aColor;\n"//输入一个名为aColor的4分量向量，layout (location = 1)表示这个变量的位置是顶点属性1。
                    + "out vec4 vColor;\n"//输出一个名为vColor的4分量向量
                    + "void main() { \n"
                    + "gl_Position  = vPosition;\n"
                    + "gl_PointSize = 10.0;\n"
                    + "vColor = aColor;\n"//将输入数据aColor拷贝到vColor的变量中。
                    + "}\n";

    private String fragmentShader =
            "#version 300 es \n"
                    + "precision mediump float;\n"//声明着色器中浮点变量的默认精度。
                    + "in vec4 vColor;\n"//声明一个输入名为vColor的4分向量
                    + "out vec4 fragColor;\n"//着色器声明一个输出变量fragColor，这个是一个4分量的向量。
                    + "void main() { \n"
                    + "fragColor = vColor; \n"//表示将输入的颜色值数据拷贝到fragColor变量中，输出到颜色缓冲区。
                    + "}\n";

    /**
     * 由于虚拟机和OpenGL运行环境不同,需要将虚拟机数据传输到native层供其使用
     * OpenGL作为本地系统库运行在系统中，虚拟机需要分配本地内存，供其存取。
     */
    public SimpleRenderer() {
        //分配内存空间,每个浮点型占4字节空间
        vertexBuffer = ByteBuffer.allocateDirect(vertexPoints.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        //传入指定的坐标数据
        vertexBuffer.put(vertexPoints);
        vertexBuffer.position(0);

        colorBuffer = ByteBuffer.allocateDirect(color.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        //传入指定的数据
        colorBuffer.put(color);
        colorBuffer.position(0);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置背景颜色
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        //编译
        final int vertexShaderId = ShaderUtils.compileVertexShader(vertextShader);
        final int fragmentShaderId = ShaderUtils.compileFragmentShader(fragmentShader);
        //链接程序片段
        mProgram = ShaderUtils.linkProgram(vertexShaderId, fragmentShaderId);
        //在OpenGLES环境中使用程序片段
        GLES30.glUseProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        draw();
    }

    /**
     * 顶点着色器已经将vPosition变量与输入属性位置0绑定了，顶点着色器中每个属性都由一个无符号整数值唯一标识的位置。
     * mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
     * mColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
     */
    private void draw() {
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 12, vertexBuffer);
        //启用顶点的句柄
        GLES30.glEnableVertexAttribArray(0);

        //绘制三角形颜色
        GLES30.glEnableVertexAttribArray(1);
        //绑定vertex坐标值,设置顶点属性指针，size每个顶点由几个值组成，stride步幅-一个顶点的步幅3*4（float大小）
        GLES30.glVertexAttribPointer(1, 4, GLES30.GL_FLOAT, false, 16, colorBuffer);

//        drawPoint();
//        drawLine();
        drawTriancle();

        //禁止顶点数组的句柄
        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    /**
     * 绘制点
     */
    private void drawPoint() {
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 3);
    }

    /**
     * 绘制直线
     */
    private void drawLine() {
        GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, 3);
        GLES30.glLineWidth(10);
    }

    /**
     * 绘制三角形
     */
    private void drawTriancle() {
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }

    /**
     常用图元类型
     图元类型	                      描述
     GL_POINTS	        点精灵图元，对指定的每个顶点进行绘制。
     GL_LINES	        绘制一系列不相连的线段。
     GL_LINE_STRIP  	绘制一系列相连的线段。
     GL_LINE_LOOP	    绘制一系列相连的线段，首尾相连。
     GL_TRIANGLES	    绘制一系列单独的三角形。
     GL_TRIANGLE_STRIP	绘制一系列相互连接的三角形。
     GL_TRIANGLE_FAN	绘制一系列相互连接的三角形。
     */

}
