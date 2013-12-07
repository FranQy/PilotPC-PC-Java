#pragma once
#include "stdafx.h"
#include "start.h"
using namespace std;
class instalacja
{
public:

	instalacja(bool systemStart, bool wszyscy, LPWSTR folder, bool skrotPulpit, bool skrotMenuStart);
public: void instalacja::start();
public: void instalacja::start(HWND hWnd);
	bool czyJava();
	bool systemStart; bool wszyscy; LPWSTR folder;
	bool skrotPulpit; bool skrotMenuStart;
	wstring folderStr;
	//WCHAR* lacz(LPCWSTR a, string b);
	int getHttp(char host[], int hostl, string path, int pathl);
	~instalacja();
	void instalacja::pobierz(string nazwa);
};
HRESULT CreateLink(LPCWSTR lpszPathObj, LPCSTR lpszPathLink, LPCWSTR lpszDesc);