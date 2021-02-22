#undef GLFW_DLL
#include <iostream>
#include <stdio.h>
#include <string.h>
#include <cmath>
#include <vector>

#include <ctime>
#include <chrono>
#include <thread>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include "Shader.h"
#include "Window.h"
#include "Mesh.h"

using std::chrono::duration_cast;
using std::chrono::milliseconds;
using std::chrono::seconds;
using std::chrono::system_clock;

const GLint WIDTH = 800, HEIGHT = 600;
const float toRadians = 3.14159265f / 180.0f;

Window mainWindow;
std::vector<Mesh*> meshList;
std::vector<Shader> shaderList;

bool direction = true;
float triOffset = 0.0f;
float triMaxOffset = 0.7f;
float triIncrement = 0.5f;

float secondOffset = 0.0f;
int secondDirection = 0;

float curAngle = 0.0f;

//Vertex Shader
static const char* vShader = "Shaders/shader.vert";

//Fragment Shader
static const char* fShader = "Shaders/shader.frag";

void CreateTriangle()
{
    GLfloat vertices[] =
    {
        -1.0f, -1.0f, 0.0f,
        0.0f, -1.0f, 1.0f,
        1.0f, -1.0f, 0.0f,
        0.0f, 1.0f, 0.0f,
    };

    unsigned int indices[] = 
    {
        0, 3, 1,
        1, 3, 2,
        2, 3, 0,
        0, 1, 2
    };

    Mesh *obj1 = new Mesh();
    obj1->CreateMesh(vertices, indices, 12, 12);
    meshList.push_back(obj1);

    Mesh *obj2 = new Mesh();
    obj2->CreateMesh(vertices, indices, 12, 12);
    meshList.push_back(obj2);
}

void CreateShaders()
{
    Shader* shader1 = new Shader();
    shader1->CreateFromFiles(vShader, fShader);
    shaderList.push_back(*shader1);
}

void Update(long elapsedTime)
{
    if (direction)  
    {
        // s += v * dt
        triOffset += (triIncrement * elapsedTime / 1000.0);
    }
    else  triOffset -= (triIncrement * elapsedTime / 1000.0);

    if (direction && triOffset >= triMaxOffset) direction = false;

    if (!direction && triOffset <= -triMaxOffset) direction = true;

    curAngle += 0.01f;
    if (curAngle >= 360) curAngle -= 360;

    if (secondDirection == -1)
    {
        secondOffset -= (triIncrement * elapsedTime / 1000.0);
    }
    else if (secondDirection == 1)
    {
        secondOffset += (triIncrement * elapsedTime / 1000.0);
    }
}

void KeyboardHandler(GLFWwindow* window, int key, int scancode, int action, int mods)
{
    if (key == GLFW_KEY_A && action == GLFW_PRESS)
    {
        //move triangle left
        secondDirection = -1;
    }
    else if (key == GLFW_KEY_A && action == GLFW_RELEASE)
    {
        secondDirection = 0;
    }
    if (key == GLFW_KEY_D && action == GLFW_PRESS)
    {
        //move triangle right
        secondDirection = 1;
    }
    else if (key == GLFW_KEY_D && action == GLFW_RELEASE)
    {
        secondDirection = 0;
    }
}

int main()
{
    mainWindow = Window(WIDTH, HEIGHT);
    mainWindow.initialise();

    CreateTriangle();
    CreateShaders();

    GLuint uniformModel = 0, uniformProjection = 0;

    glm::mat4 projection = glm::perspective(45.0f, (GLfloat)mainWindow.getBufferWidth() / (GLfloat)mainWindow.getBufferHeight(), 0.1f, 100.0f);

    auto currentTime = duration_cast<milliseconds>(system_clock::now().time_since_epoch()).count();
    long lastTime = currentTime;
    long elapsedTime;

    glfwSetKeyCallback(mainWindow.getWindow(), KeyboardHandler);

    //Loop until window closed
    while (!mainWindow.getShouldClose())
    {
        currentTime = duration_cast<milliseconds>(system_clock::now().time_since_epoch()).count();
        elapsedTime = currentTime - lastTime;

        lastTime = currentTime;

        //Get + Handle user input events
        glfwPollEvents();

        Update(elapsedTime);

        //Clear window
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //draw here
        shaderList[0].UseShader();
        uniformModel = shaderList[0].GetModelLocation();
        uniformProjection = shaderList[0].GetProjectionLocation();

        //Object 1
        glm::mat4 model (1.0f);

        model = glm::translate(model, glm::vec3(triOffset, 0.0f, -2.5f));
        model = glm::scale(model, glm::vec3(0.4f, 0.4f, 1.0f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        meshList[0]->RenderMesh();
        
        //Object 2
        model = glm::mat4 (1.0f);

        model = glm::translate(model, glm::vec3(-triOffset, 1.0f, -2.5f));
        model = glm::scale(model, glm::vec3(0.4f, 0.4f, 1.0f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        meshList[1]->RenderMesh();

        //Object 3
        model = glm::mat4(1.0f);
        model = glm::translate (model, glm::vec3(secondOffset, 0.5f, -2.5f));
        model = glm::scale(model, glm::vec3(0.4f, 0.4f, 1.0f));
        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
        meshList[1]->RenderMesh();

        glUseProgram(0);
        //end draw

        mainWindow.swapBuffers();
    }

    return 0;
}
