cmake_minimum_required(VERSION 3.6)
project(prime)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp test.cpp)
add_executable(prime ${SOURCE_FILES})