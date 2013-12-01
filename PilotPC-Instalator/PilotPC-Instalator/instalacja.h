#pragma once
#include "stdafx.h"
using namespace std;
class instalacja
{
public:
	instalacja(bool systemStart,bool wszyscy, char* folder);
	bool czyJava();
	int getHttp(char host[], int hostl, char* path, int pathl);
	~instalacja();
	void instalacja::pobierz(char* od, int dlugosc);
};