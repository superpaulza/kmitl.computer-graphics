#ifndef SHADER____H
#define SHADER____H

#include <iostream>
#include <fstream>
#include <stdio.h>
#include <string.h>

#include <GL/glew.h>

class Shader
{
    public:
        Shader();
        ~Shader();

        void CreateFromString (const char* vertexCode, const char* fragmentCode);
        void CreateFromFiles (const char* vertexLocation, const char* fragmentLocation);
        std::string ReadFile(const char* fileLocation);

        void UseShader();
        void ClearShader();

        GLuint GetProjectionLocation() {return uniformProjection;}
        GLuint GetModelLocation() {return uniformModel;}
        GLuint GetColourLocation() {return uniformColour;}

    private:
        GLuint shader, uniformModel, uniformProjection, uniformColour;
        void CompileShaders(const char* vertexCode, const char* fragmentCode);
        void AddShader(GLuint theProgram, const char* shaderCode, GLenum shaderType);

};

#endif