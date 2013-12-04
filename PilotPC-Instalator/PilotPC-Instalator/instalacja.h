#pragma once
#include "stdafx.h"
using namespace std;
class instalacja
{
public:

	instalacja(bool systemStart, bool wszyscy, LPWSTR folder);
public: void instalacja::start();
public: void instalacja::start(HWND hWnd);
	bool czyJava();
	bool systemStart; bool wszyscy; LPWSTR folder;
	//WCHAR* lacz(LPCWSTR a, string b);
	int getHttp(char host[], int hostl, string path, int pathl);
	~instalacja();
	void instalacja::pobierz(string nazwa);
};