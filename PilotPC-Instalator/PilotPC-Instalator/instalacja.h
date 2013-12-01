#pragma once
#include "stdafx.h"
using namespace std;
class instalacja
{
public:
	instalacja(bool systemStart,bool wszyscy, char* folder);
	bool czyJava();
	void getHttp(char* url);
	~instalacja();
};