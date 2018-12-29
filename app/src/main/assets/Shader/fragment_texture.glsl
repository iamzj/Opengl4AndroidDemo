#version 300 es //着色器的版本，OpenGL ES 2.0版本可以不写。
precision mediump float;//声明着色器中浮点变量的默认精度。
out vec4 fragColor;//明一个输出变量fragColor，这个是一个4分量的向量。
void main() {
     fragColor = vec4(1.0,1.0,1.0,1.0);//将颜色值(1.0,1.0,1.0,1.0)，输出到颜色缓冲区。
}