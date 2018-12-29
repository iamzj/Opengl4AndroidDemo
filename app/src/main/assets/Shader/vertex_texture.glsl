#version 300 es
layout (location = 0) in vec4 vPosition;//输入属性的数组(一个名为vPosition的4分量向量)，layout (location = 0)表示这个变量的位置是顶点属性0。
void main() {
     gl_Position  = vPosition;//将vPosition输入属性拷贝到名为gl_Position的特殊输出变量。
     gl_PointSize = 10.0;//将浮点数据10.0拷贝到gl_PointSize的变量中。
}