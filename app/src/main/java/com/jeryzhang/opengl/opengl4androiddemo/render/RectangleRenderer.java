package com.jeryzhang.opengl.opengl4androiddemo.render;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.jeryzhang.opengl.opengl4androiddemo.util.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * https://blog.csdn.net/byhook/article/details/83759218
 */
public class RectangleRenderer implements GLSurfaceView.Renderer {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int BYTES_PER_FLOAT = 4;
    //之前定义的坐标数据中，每一行是5个数据，前两个表示坐标(x,y)，后三个表示颜色(r,g,b)
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private final FloatBuffer vertexBuffer;
    private int mProgram;
    private int aPositionLocation;
    private int aColorLocation;
    private int uMatrixLocation;

    public RectangleRenderer() {
        //分配内存空间,每个浮点型占4字节空间
        vertexBuffer = ByteBuffer.allocateDirect(vertexPoints.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        //传入指定的坐标数据
        vertexBuffer.put(vertexPoints);
        vertexBuffer.position(0);
    }

    /**
     * 顶点着色器
     */
    private String vertextShader =
            "#version 300 es \n" +
                    "layout (location = 0) in vec4 vPosition;\n"
                    + "layout (location = 1) in vec4 aColor;\n"
                    + "uniform mat4 u_Matrix;\n"
                    + "out vec4 vColor;\n"
                    + "void main() { \n"
                    + "gl_Position  = u_Matrix * vPosition;\n"
                    + "gl_PointSize = 10.0;\n"
                    + "vColor = aColor;\n"
                    + "}\n";

    private String fragmentShader =
            "#version 300 es \n" +
                    "precision mediump float;\n"
                    + "in vec4 vColor;\n"
                    + "out vec4 fragColor;\n"
                    + "void main() { \n"
                    + "fragColor = vColor;\n"
                    + "}\n";


//    private String vertextShader =
//            "#version 300 es \n" +
//                    "layout (location = 0) in vec4 vPosition;\n"//输入一个名为vPosition的4分量向量，layout (location = 0)表示这个变量的位置是顶点属性0。
//                    + "layout (location = 1) in vec4 aColor;\n"//输入一个名为aColor的4分量向量，layout (location = 1)表示这个变量的位置是顶点属性1。
//                    + "out vec4 vColor;\n"//输出一个名为vColor的4分量向量
//                    + "void main() { \n"
//                    + "gl_Position  = vPosition;\n"
//                    + "gl_PointSize = 10.0;\n"
//                    + "vColor = aColor;\n"//将输入数据aColor拷贝到vColor的变量中。
//                    + "}\n";
//
//    private String fragmentShader =
//            "#version 300 es \n"
//                    + "precision mediump float;\n"//声明着色器中浮点变量的默认精度。
//                    + "in vec4 vColor;\n"//声明一个输入名为vColor的4分向量
//                    + "out vec4 fragColor;\n"//着色器声明一个输出变量fragColor，这个是一个4分量的向量。
//                    + "void main() { \n"
//                    + "fragColor = vColor; \n"//表示将输入的颜色值数据拷贝到fragColor变量中，输出到颜色缓冲区。
//                    + "}\n";

    /**
     * 点的坐标
     */
    private float[] vertexPoints = new float[]{
            0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            -0.5f, -0.8f, 1.0f, 1.0f, 1.0f,
            0.5f, -0.8f, 1.0f, 1.0f, 1.0f,
            0.5f, 0.8f, 1.0f, 1.0f, 1.0f,
            -0.5f, 0.8f, 1.0f, 1.0f, 1.0f,
            -0.5f, -0.8f, 1.0f, 1.0f, 1.0f,

            0.0f, 0.25f, 0.5f, 0.5f, 0.5f,
            0.0f, -0.25f, 0.5f, 0.5f, 0.5f,
    };

    private final float[] mMatrix = new float[16];

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

        aPositionLocation = GLES30.glGetAttribLocation(mProgram, "vPosition");
        aColorLocation = GLES30.glGetAttribLocation(mProgram, "aColor");
        uMatrixLocation = GLES30.glGetUniformLocation(mProgram, "u_Matrix");

        //每个顶点由size指定的顶点属性分量顺序存储。stride指定顶 点索引I和(I+1），表示的顶点数据之间的位移。
        // 如果stride为0，则每个顶点的属性数据顺序存储。如果stride大于0, 则使用该值作为获取下一个索引表示的顶点数据的跨距。
        //所以这里实际是 STRIDE = (2 + 3) x 4

        vertexBuffer.position(0);
        //获取顶点数组 (POSITION_COMPONENT_COUNT = 2)
        GLES30.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES30.GL_FLOAT, false, STRIDE, vertexBuffer);
        GLES30.glEnableVertexAttribArray(aPositionLocation);


        vertexBuffer.position(POSITION_COMPONENT_COUNT);
        //颜色属性分量的数量 COLOR_COMPONENT_COUNT = 3
        GLES30.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES30.GL_FLOAT, false, STRIDE, vertexBuffer);
        GLES30.glEnableVertexAttribArray(aColorLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        //正交投影矩阵
        final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;
        if (width > height) {
            //横屏
            Matrix.orthoM(mMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            //竖屏
            Matrix.orthoM(mMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }

        //透视投影矩阵
//        Matrix.perspectiveM(mMatrix, 0, 45, (float) width / height, 1f, 10f);
//        Matrix.translateM(mMatrix, 0, 0f, 0f, -2.5f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0);

        //绘制矩形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 6);
        //绘制两个点
        GLES30.glDrawArrays(GLES30.GL_POINTS, 6, 2);

    }
}
