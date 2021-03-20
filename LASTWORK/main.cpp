#undef GLFW_DLL
#include <stdio.h>
#include <iostream>
#include <string.h>
#include <cmath>
#include <vector>

#include <GL/glew.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include <ctime>
#include <chrono>
#include <thread>

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
std::vector<Shader> shaderList;

GLuint uniformModel, uniformProjection, uniformColour;

//Vertex Shader
static const char* vShader = "Shaders/shader.vert";

//Fragment Shader
static const char* fShader = "Shaders/shader.frag";

float animation_time = 0.0f;
void Update(long elapsedTime) {
	animation_time += (elapsedTime / 1000.0 );
}

//Create new item
GLuint BGa, BGv, BGi, BGiU, BGiO; // Bread Ground
GLuint MOa, MOv, MOiO, MOiU; // Meat Oval
GLuint CPa, CPv, CPi; // Cheese plate
GLuint MPa, MPv, MPi; // Meat Bolona Oval Plate
GLuint VRa, VRv, VRi, VRiU, VRiO; // Vegetable random design
GLuint BTa, BTv, BTiU, BTiT; // Bread Top Hat
GLuint DBa, DBv, DSa, DSv, cDS; // Details of Objects
int cDSi[125];

void ItemModel() {
	// First Calculate
	int quality = 20;
	float curve_x[20];
	float curve_y[20];
	float delta = (180 * toRadians);
	for (int i = 0; i < quality; i++) {
		curve_x[i] = cos(float(i)/ (quality-1) * delta);
		curve_y[i] = sin(float(i) / (quality - 1) * delta);
		//std::cout << curve[i] << "\n";
	}
	float floor_line, size_chift, size_thick;
	
	// Ground Bread
	GLint l = quality * 3;
	GLfloat GBV[180]; // l * 3
	for (int i = 0; i < quality; i++) {
		GBV[i*3]		= curve_x[i];
		GBV[i*3 + 1]	= -curve_y[i] /2;
		GBV[i*3 + 2]	= 0.0f;

		GBV[l + i * 3]		= -curve_x[i];
		GBV[l + i * 3 + 1]  = -curve_y[i] /3;
		GBV[l + i * 3 + 2]  = 0.0f;

		GBV[l * 2 + i * 3]		= curve_x[i];
		GBV[l * 2 + i * 3 + 1]  = curve_y[i] /3;
		GBV[l * 2 + i * 3 + 2]  = 0.0f;
	}

	unsigned int GBI[60]; // l * 3
	for (int i = 0; i < 30; i++)
		GBI[i] = i;

	unsigned int GBIU[40]; // l * 2
	for (int i = 39; i >= 0; i--)
		GBIU[i] = i;

	unsigned int GBIO[40]; // l * 2
	for (int i = 0; i < 40; i++)
		GBIO[i] = i+20;
	
	glGenBuffers(1, &BGi);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, BGi);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GBI), GBI, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenBuffers(1, &BGiU);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, BGiU);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GBIU), GBIU, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenBuffers(1, &BGiO);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, BGiO);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(GBIO), GBIO, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenVertexArrays(1, &BGa);
	glBindVertexArray(BGa); //
	glGenBuffers(1, &BGv);
	glBindBuffer(GL_ARRAY_BUFFER, BGv); //
	glBufferData(GL_ARRAY_BUFFER, sizeof(GBV), GBV, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0); //
	glBindVertexArray(0); //

	// MEAT cheat model
	GLfloat MCV[180];	
	 size_thick = 0.08f;
	 size_chift = 0.96f;
	for (int i = 0; i < quality; i++) {
		float x = size_chift * curve_x[i], y = size_chift * curve_y[i];
		MCV[i * 3] = x;
		MCV[i * 3 + 1] = size_thick + y / 3;
		MCV[i * 3 + 2] = 0.0f;

		MCV[l + i * 3] = x;
		MCV[l + i * 3 + 1] = size_thick - y / 3;
		MCV[l + i * 3 + 2] = 0.0f;

		MCV[l * 2 + i * 3] = x;
		MCV[l * 2 + i * 3 + 1] = -y / 3;
		MCV[l * 2 + i * 3 + 2] = 0.0f;
	}

	unsigned int MCIO[40];
	for (int i = 0; i < 20; i++) {
		MCIO[i*2]	= i;
		MCIO[i*2+1] = i + 20;
	}

	unsigned int MCIU[40];
	for (int i = 0; i < 20; i++) {
		MCIU[i*2]	= i + 20;
		MCIU[i*2+1] = i + 40;
	}


		
	glGenBuffers(1, &MOiO);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, MOiO);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(MCIO), MCIO, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenBuffers(1, &MOiU);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, MOiU);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(MCIU), MCIU, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenVertexArrays(1, &MOa);
	glBindVertexArray(MOa); //
	glGenBuffers(1, &MOv);
	glBindBuffer(GL_ARRAY_BUFFER, MOv); //
	glBufferData(GL_ARRAY_BUFFER, sizeof(MCV), MCV, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0); //
	glBindVertexArray(0); //

	// Cheese Design
	floor_line = 0.08f;
	size_chift = 0.96f;
	size_thick = 0.05f;

	GLfloat CPV[96];
	l = 16 * 3;
	for (int i = 0; i < quality-4; i++) {
		float x = size_chift * curve_x[i+2], y = size_chift * curve_y[i+2];
		CPV[i * 3] = x;
		CPV[i * 3 + 1] = floor_line + y / 3;
		CPV[i * 3 + 2] = 0.0f;

		CPV[l + i * 3] = x;
		CPV[l + i * 3 + 1] = - 3 * floor_line + y / 6;
		CPV[l + i * 3 + 2] = 0.0f;

		//std::cout << x << " , " << y << "\n";
	}

	unsigned int CPI[32];
	for (int i = 0; i < 16; i++) {
		CPI[i * 2] = i;
		CPI[i * 2 + 1] = i + 16;
	}

	glGenBuffers(1, &CPi);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, CPi);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(CPI), CPI, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenVertexArrays(1, &CPa);
	glBindVertexArray(CPa); //
	glGenBuffers(1, &CPv);
	glBindBuffer(GL_ARRAY_BUFFER, CPv); //
	glBufferData(GL_ARRAY_BUFFER, sizeof(CPV), CPV, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0); //
	glBindVertexArray(0); //

	// Bolona model
	floor_line += 0.025f;
	size_chift = 0.94f;
	l = 20 * 3;
	GLfloat MPV[120];
	for (int i = 0; i < quality; i++) {
		float x = size_chift * curve_x[i], y = size_chift * curve_y[i];
		MCV[i * 3] = x;
		MCV[i * 3 + 1] = floor_line + y / 3;
		MCV[i * 3 + 2] = 0.0f;

		MCV[l + i * 3] = x;
		MCV[l + i * 3 + 1] = floor_line - y / 3;
		MCV[l + i * 3 + 2] = 0.0f;
	}

	unsigned int MPI[40];
	for (int i = 0; i < 20; i++) {
		MPI[i * 2] = i;
		MPI[i * 2 + 1] = i + 20;
	}

	glGenBuffers(1, &MPi);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, MPi);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(MPI), MPI, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenVertexArrays(1, &MPa);
	glBindVertexArray(MPa); //
	glGenBuffers(1, &MPv);
	glBindBuffer(GL_ARRAY_BUFFER, MPv); //
	glBufferData(GL_ARRAY_BUFFER, sizeof(MCV), MCV, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0); //
	glBindVertexArray(0); //

	size_chift = 0.65f;
	float move = -0.1f;
	GLfloat BLO[120];
	for (int i = 0; i < quality; i++) {
		float x = size_chift * curve_x[i], y = size_chift * curve_y[i];
		BLO[i * 6] = x + move;
		BLO[i * 6 + 1] = floor_line + y / 3;
		BLO[i * 6 + 2] = 0.0f;

		BLO[i * 6 + 3] = x + move;
		BLO[i * 6 + 4] = floor_line - y / 3;
		BLO[i * 6 + 5] = 0.0f;
	}

	glGenVertexArrays(1, &DBa);
	glBindVertexArray(DBa); //
	glGenBuffers(1, &DBv);
	glBindBuffer(GL_ARRAY_BUFFER, DBv); //
	glBufferData(GL_ARRAY_BUFFER, sizeof(BLO), BLO, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0); //
	glBindVertexArray(0); //


	// Vegetable 
	floor_line += 0.15f;
	size_thick = 0.15f;
	l = 20 * 3;
	GLfloat VRV[180];
	for (int i = 0; i < quality; i++) {
		VRV[i * 3] = curve_x[i];
		VRV[i * 3 + 1] = floor_line - 0.05f - ( curve_y[i] + curve_y[(i * 5) % quality] * 0.1f ) / 3 ;
		VRV[i * 3 + 2] = 0.0f;

		VRV[l + i * 3] = curve_x[i];
		VRV[l + i * 3 + 1] = floor_line - curve_y[i] / 3;
		VRV[l + i * 3 + 2] = 0.0f;

		VRV[l * 2 + i * 3] = curve_x[i];
		VRV[l * 2 + i * 3 + 1] = floor_line + curve_y[i] / 3 ;
		VRV[l * 2 + i * 3 + 2] = 0.0f;
	}

	unsigned int VRI[60];
	for (int i = 0; i < 20; i++) {
		VRI[i * 2] = i;
		VRI[i * 2 + 1] = i + 20;
	}

	unsigned int VRIU[40], VRIO[40];
	for (int i = 0; i < 20; i++) {
		VRIU[i * 2] = i;
		VRIU[i * 2 + 1] = i + 20;

		VRIO[i * 2] = i + 20;
		VRIO[i * 2 + 1] = i + 40;
	}
	
	glGenBuffers(1, &VRi);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VRi);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(VRI), VRI, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenBuffers(1, &VRiU);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VRiU);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(VRIU), VRIU, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenBuffers(1, &VRiO);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VRiO);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(VRIO), VRIO, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenVertexArrays(1, &VRa);
	glBindVertexArray(VRa); //
	glGenBuffers(1, &VRv);
	glBindBuffer(GL_ARRAY_BUFFER, VRv); //
	glBufferData(GL_ARRAY_BUFFER, sizeof(VRV), VRV, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0); //
	glBindVertexArray(0); //

	// Bread top hat
	floor_line += 0.05f;
	//size_chift = 0.94f;
	size_thick = 0.1f;
	l = 20 * 3;
	GLfloat BTV[120];
	for (int i = 0; i < quality; i++) {
		BTV[i * 3] = curve_x[i];
		BTV[i * 3 + 1] = floor_line - curve_y[i] / 3;
		BTV[i * 3 + 2] = 0.0f;

		BTV[l + i * 3] = curve_x[i];
		BTV[l + i * 3 + 1] = floor_line + curve_y[i];
		BTV[l + i * 3 + 2] = 0.0f;
	}

	unsigned int BTI[40];
	for (int i = 0; i < 20; i++) {
		BTI[i * 2] = i;
		BTI[i * 2 + 1] = i + 20;
	}

	glGenBuffers(1, &BTiT);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, BTiT);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(BTI), BTI, GL_STATIC_DRAW);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

	glGenVertexArrays(1, &BTa);
	glBindVertexArray(BTa); //
	glGenBuffers(1, &BTv);
	glBindBuffer(GL_ARRAY_BUFFER, BTv); //
	glBufferData(GL_ARRAY_BUFFER, sizeof(BTV), BTV, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0); //
	glBindVertexArray(0); //


	//Seed details
	GLfloat WDP[] = {
		0.0f, 3.0f, 0.0f,
		-1.0f,	0.0f, 0.0f,
		1.0f,	0.0f, 0.0f,
		-1.0f,	-0.6f, 0.0f,
		1.0f,	-0.6f, 0.0f,
		-0.75f, -0.9f, 0.0f,
		0.75f, -0.9f, 0.0f,
		0.0f,	-1.0f, 0.0f
	};

	GLfloat PIN[250];
	for (int i = 0; i < 125; i++) {
		float y = i / 125.0f , x = 1;
		while(pow(x,2) + pow(y,2) > 1)
			x = ( rand() % 2000 - 1000 ) / 1000.0f ;
		
		PIN[i * 2] = x;
		PIN[i * 2 + 1] = floor_line + y;

		//std::cout << x << " , " << y << " - " << (rand() % 1000)/1000.0f << "\n";
	}
	

	size_chift = 0.1f;
	for (int i = 0; i < 24; i++)
		WDP[i] *= size_chift;

	GLfloat SDV[3000];
	for (int i = 0; i < 125; i++) {
		l = i * 24;
		for (int j = 0; j < 8; j++) {
			SDV[l + j * 3]		= PIN[i * 2 + 0] + size_chift * WDP[j*3];
			SDV[l + j * 3 + 1]	= PIN[i * 2 + 1] + size_chift * WDP[j*3+1];
			SDV[l + j * 3 + 2]	= 0.0f;
		}
	}


	for (int i = 0; i < 125; i++)
		cDSi[i] = rand() % 3;


	glGenVertexArrays(1, &DSa);
	glBindVertexArray(DSa); //
	glGenBuffers(1, &DSv);
	glBindBuffer(GL_ARRAY_BUFFER, DSv); //
	glBufferData(GL_ARRAY_BUFFER, sizeof(SDV), SDV, GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
	glEnableVertexAttribArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0); //
	glBindVertexArray(0); //	
}

void CreateShaders()
{
    Shader* shader1 = new Shader();
    shader1->CreateFromFiles(vShader, fShader);
    shaderList.push_back(*shader1);
}
int main() {

	mainWindow = Window(WIDTH, HEIGHT);
    mainWindow.initialise();

	//ADD item
	ItemModel();
	CreateShaders();

	glm::vec4 color;
	glm::mat4 projection = glm::perspective(45.0f, (GLfloat)mainWindow.getBufferWidth() / (GLfloat)mainWindow.getBufferHeight(), 0.1f, 100.0f);

	//// Time setting
	auto currentTime = duration_cast<milliseconds>(system_clock::now().time_since_epoch()).count();
	long lastTime = currentTime;
	long elapsedTime;

	glm::vec4 cBurger, cVegetable, cMeat, cCheese, cHam, cBlack, cWhite, cBurgerIn, cMeatIn, cHamIn, cVegetableIn , 
		cSeedClosetMix, cSeedMasterMix, cSeedMacaroonCream;
	color = glm::vec4(1.0f, 0.5f, 0.15f, 1.0f);

	cBurger		= glm::vec4(0.87f, 0.63f, 0.22f, 1.0f);
	cVegetable	= glm::vec4(0.39f, 0.71f, 0.13f, 1.0f);
	cMeat		= glm::vec4(0.69f, 0.40f, 0.19f, 1.0f);
	cCheese		= glm::vec4(0.99f, 0.82f, 0.10f, 1.0f);
	cHam		= glm::vec4(0.96f, 0.45f, 0.50f, 1.0f);

	cBurgerIn	= glm::vec4(0.97f, 0.73f, 0.32f, 1.0f);
	cMeatIn		= glm::vec4(0.79f, 0.50f, 0.29f, 1.0f);
	cVegetableIn= glm::vec4(0.44f, 0.76f, 0.18f, 1.0f);
	cHamIn		= glm::vec4(1.0f, 0.55f, 0.60f, 1.0f);

	cBlack		= glm::vec4(0.0f, 0.0f, 0.0f, 1.0f);
	cWhite		= glm::vec4(1.0f, 1.0f, 1.0f, 1.0f);

	cSeedClosetMix		= glm::vec4(0.17f, 0.08f, 0.12f, 1.0f);
	cSeedMasterMix		= glm::vec4(0.93f, 0.93f, 0.92f, 1.0f);
	cSeedMacaroonCream	= glm::vec4(0.95f, 0.85f, 0.70f, 1.0f);



	// Create operate variable
	float rotater = 0.0f;
	float subAnimate;
	int subSelect;
	float setScale = 1.0f ,resScale;

	//Loop until window closed
	while (!mainWindow.getShouldClose()) {

		if (rotater > 360.0f) rotater = 0.0f;
		//Time processing
		currentTime = duration_cast<milliseconds>(system_clock::now().time_since_epoch()).count();
		elapsedTime = currentTime - lastTime;
		lastTime = currentTime;

		// Get + Handle user input events
		glfwPollEvents();
		Update(elapsedTime);

		glClearColor(0.7f, 1.0f, 0.45f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shaderList[0].UseShader();
        uniformModel = shaderList[0].GetModelLocation();
        uniformProjection = shaderList[0].GetProjectionLocation();
        uniformColour = shaderList[0].GetColourLocation();

		subSelect = (int)animation_time;
		subAnimate = animation_time - subSelect;
		resScale = setScale * (1 - subAnimate);
		glm::mat4 model(1.0f);
		if(subSelect < 9)
			model = glm::scale(model, glm::vec3(setScale, setScale, 1.0f));
		else
			model = glm::scale(model, glm::vec3(resScale, resScale, 1.0f));

		glm::mat4 modelDrop(1.0f);
		modelDrop = glm::translate(model, glm::vec3(0.0f, 2.0f*(1-subAnimate) -0.5f, -2.5f));
		modelDrop = glm::rotate(modelDrop, rotater * toRadians, glm::vec3(0.0f, 1.0f, 0.0f));

		model = glm::translate(model, glm::vec3(0.0f, -0.5f, -2.5f));
		model = glm::rotate(model, rotater * toRadians, glm::vec3(0.0f, 1.0f, 0.0f));

		glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
		glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));
		glUniform4fv(uniformColour, 1, glm::value_ptr(cBlack));

		if (animation_time > 1.0f)	{
			glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr((subSelect == 1) ? modelDrop: model));
			
			glBindVertexArray(BGa);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cBurger));
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, BGiU);
			glDrawElements(GL_TRIANGLE_FAN, 20, GL_UNSIGNED_INT, 0);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cBurgerIn));
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, BGiO);
			glDrawElements(GL_TRIANGLE_FAN, 40, GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}
		
		if (animation_time > 2.0f) {
			glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr((subSelect == 2) ? modelDrop : model));

			glBindVertexArray(MOa);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cMeat));
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, MOiO);
			glDrawElements(GL_TRIANGLE_STRIP, 40, GL_UNSIGNED_INT, 0);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cMeatIn));
			glDrawElements(GL_LINES_ADJACENCY, 40, GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, MOiU);
			glDrawElements(GL_TRIANGLE_STRIP, 40, GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}

		if (animation_time > 3.0f) {
			glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr((subSelect == 3) ? modelDrop : model));

			glBindVertexArray(CPa);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cCheese));
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, CPi);
			glDrawElements(GL_TRIANGLE_STRIP, 32, GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}

		if (animation_time > 4.0f) {
			glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr((subSelect == 4) ? modelDrop : model));

			glBindVertexArray(MPa);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cHam));
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, MPi);
			glDrawElements(GL_TRIANGLE_STRIP, 40, GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);

			glBindVertexArray(DBa);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cHamIn));
			glDrawArrays(GL_TRIANGLE_STRIP, 0,40);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}

		if (animation_time > 5.0f) {
			glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr((subSelect == 5) ? modelDrop : model));

			glBindVertexArray(VRa);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cVegetable));
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VRiU);
			glDrawElements(GL_TRIANGLE_STRIP, 40, GL_UNSIGNED_INT, 0);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cVegetableIn));
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, VRiO);
			glDrawElements(GL_TRIANGLE_STRIP, 40, GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}

		if (animation_time > 6.0f) {
			glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr((subSelect == 6) ? modelDrop : model));

			glBindVertexArray(BTa);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cBurger));
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, BTiT);
			glDrawElements(GL_TRIANGLE_STRIP, 40, GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);

			glBindVertexArray(DSa);
			glUniform4fv(uniformColour, 1, glm::value_ptr(cWhite));
			for (int i = 0,j = 0; i < 1000; i += 8 ,j++) {
				glUniform4fv(uniformColour, 1, glm::value_ptr( cDSi[j] == 0 ? cSeedClosetMix : cDSi[j] == 1 ? cSeedMasterMix : cSeedMacaroonCream));
				glDrawArrays(GL_TRIANGLE_STRIP,i,8);
			}
			glBindVertexArray(0);

		}

		if (animation_time > 10.0f) {
			animation_time = 0.0f;
		}
		
		glUseProgram(0);

		mainWindow.swapBuffers();
	}

	return 0;
}
