cmake_minimum_required(VERSION 3.4.1)

##官方标准配置
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fno-rtti -fno-exceptions -Wall")

##ANDROID_PLATFORM_LEVEL=18
add_definitions("-DDYNAMIC_ES3")
set(OPENGL_LIB GLESv3)

find_library(log-lib log )


add_library(native_color
            SHARED
            src/main/cpp/native_color.cpp )
add_library(native_simple
            SHARED
            src/main/cpp/native_simple.cpp )




target_link_libraries(native_color
            ${OPENGL_LIB}
            android
            EGL
            ${log-lib}
            m)

target_link_libraries(native_simple
            ${OPENGL_LIB}
            android
            EGL
            ${log-lib}
            m)