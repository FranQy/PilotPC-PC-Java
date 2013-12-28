#pragma once
#include "stdafx.h"
using namespace std;
class instalacja
{
public:

	instalacja(bool systemStart, bool wszyscy, LPWSTR folder, bool skrotPulpit, bool skrotMenuStart,HWND);
public: void instalacja::start();
public: void instalacja::start(HWND hWnd);
public: static void instalacja::odinstaluj(HINSTANCE, HWND);
public: void __cdecl instalacja::start(void * Args);
		HWND okno;
	bool czyJava();
	HWND progressbar;
	bool systemStart; bool wszyscy; LPWSTR folder;
	bool skrotPulpit; bool skrotMenuStart;
	wstring folderStr;
	int ilePlikow;
	int ilePlikowGotowe;
	//WCHAR* lacz(LPCWSTR a, string b);
	int getHttp(char host[], int hostl, string path, int pathl);
	~instalacja();
	void instalacja::pobierz(string nazwa);

};
HRESULT CreateLink(LPCWSTR lpszPathObj, LPCSTR lpszPathLink, LPCWSTR lpszDesc,LPCWSTR workingDir);
#include "start.h"