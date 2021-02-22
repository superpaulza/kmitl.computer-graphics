// FirstGL.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

//#include <iostream>
#include <stdio.h>
#include <string.h>
#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

const GLint WIDTH = 800, HEIGHT = 600;
const float toRadians = 3.14159265f / 180.0f;

float triOffset = 0.3f;
float triIncrement = 0.0005f;
float triMaxOffset = 0.7f;
bool direction = true;

GLuint VAO, VBO, shader, uniformModel;

//Vertex Shader
static const char* vShader =
"																\n\
#version 330													\n\
																\n\
layout (location = 0) in vec3 pos;								\n\
																\n\
uniform mat4 model;												\n\
																\n\
void main()														\n\
{																\n\
	gl_Position = model * vec4(pos.x, pos.y, pos.z, 1.0);		\n\
}																\n\
";

//Fragment Shader
static const char* fShader =
"																\n\
#version 330													\n\
																\n\
out	vec4 colour;												\n\
																\n\
void main()														\n\
{																\n\
	colour = vec4(1.0, 1.0, 0.0, 1.0);							\n\
}																\n\
";

void AddShader(GLuint theProgram, const char* shaderCode, GLenum shaderType)
{
	GLuint theShader = glCreateShader(shaderType);

	const GLchar* theCode[1];
	theCode[0] = shaderCode;

	GLint codeLength[1];
	codeLength[0] = strlen(shaderCode);

	glShaderSource(theShader, 1, theCode, codeLength);
	glCompileShader(theShader);

	GLint result = 0;
	GLchar elog[1024] = { 0 };

	glGetShaderiv(theShader, GL_COMPILE_STATUS, &result);

	if (!result)
	{
		glGetShaderInfoLog(theShader, sizeof(elog), NULL, elog);
		printf("Error compiling the %d shader: '%s'\n", shaderType, elog);
		return;
	}

	glAttachShader(theProgram, theShader);
}

void CompileShaders()
{
	shader = glCreateProgram();

	if (!shader)
	{
		printf("Error creating shader program!\n");
		return;
	}

	AddShader(shader, vShader, GL_VERTEX_SHADER);
	AddShader(shader, fShader, GL_FRAGMENT_SHADER);

	GLint result = 0;
	GLchar elog[1024] = { 0 };

	glLinkProgram(shader);
	glGetProgramiv(shader, GL_LINK_STATUS, &result);

	if (!result)
	{
		glGetProgramInfoLog(shader, sizeof(elog), NULL, elog);
		printf("Error linking program: '%s'", elog);
		return;
	}

	glValidateProgram(shader);
	glGetProgramiv(shader, GL_VALIDATE_STATUS, &result);

	if (!result)
	{
		glGetProgramInfoLog(shader, sizeof(elog), NULL, elog);
		printf("Error vaildating program: '%s'", elog);
		return;
	}


}

void CreateTriangle()
{
	GLfloat vertices[] =
	{
		-1.0f, -1.0f, 0.0f,
		1.0f, -1.0f, 0.0f,
		0.0f, 1.0f, 0.0f
	};

	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);

	glGenBuffers(1, &VBO);
	glBindBuffer(GL_ARRAY_BUFFER, VBO);

	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);

	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindVertexArray(0);



}


int main()
{
	//init GLFW
	if (!glfwInit())
	{
		printf("GLFW initialisation failed!");
		glfwTerminate();
		return 1;
	}

	//Setup GLFW window properties
	//OpenGL version (using 3.3)
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

	//Core Profile = No Backwards Compatibility
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	//Allow forward compatibility
	glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

	GLFWwindow* mainWindow = glfwCreateWindow(WIDTH, HEIGHT, "Test Window", NULL, NULL);

	if (!mainWindow)
	{
		printf("GLFW window creation failed!");
		glfwTerminate();
		return 1;
	}

	//Get buffer size information
	int bufferWidth, bufferHeight;
	glfwGetFramebufferSize(mainWindow, &bufferWidth, &bufferHeight);

	//Set context for GLEW to use
	glfwMakeContextCurrent(mainWindow);

	//Allow mordern extension features
	//for secret room 3 enter - https://forms.gle/zfjYzUEzAHDMdM8r5
	glewExperimental = GL_TRUE;

	if (glewInit() != GLEW_OK)
	{
		printf("GLEW initialisation failed!");
		glfwDestroyWindow(mainWindow);
		glfwTerminate();
		return 1;

		//magic word - AMAZON
	}

	//Setup Viewport size
	glViewport(0, 0, bufferWidth, bufferHeight);

	CreateTriangle();
	CompileShaders();

	//Loop until window closed
	while (!glfwWindowShouldClose(mainWindow))
	{
		//Get + Handle user input events
		glfwPollEvents();
		
		if(direction) triOffset += triIncrement;
		else triOffset -= triIncrement;

		if (abs(triOffset) >= triMaxOffset) direction = !direction;


		//Clear window
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);


		//draw here
		glUseProgram(shader);

		//transform
		glm::mat4 model(1.0f);
		model = glm::translate(model, glm::vec3(triOffset, 0.0f, 0.0f));
		model = glm::rotate(model, 90.0f * toRadians, glm::vec3(0.0f, 0.0f, 1.0f));
		model = glm::scale(model, glm::vec3(0.4f, 0.4f, 1.0f));

		glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));

		glBindVertexArray(VAO);
		glDrawArrays(GL_TRIANGLES, 0, 3);

		glBindVertexArray(0);
		glUseProgram(0);
		//end here
		             

		glfwSwapBuffers(mainWindow);
	}

	return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
