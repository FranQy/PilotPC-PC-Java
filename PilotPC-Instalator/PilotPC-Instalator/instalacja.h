#pragma once
#include "stdafx.h"
using namespace std;

BOOL IsRunAsAdmin();
class instalacja
{
public: int postepFaktyczny = 0;
public: int postepAnim = 0;
public:HWND StanInstalacji;
public:	instalacja(bool systemStart, bool wszyscy, LPCWSTR folder, bool skrotPulpit, bool skrotMenuStart, HWND,wstring wfolder,HWND);
public: void instalacja::start(wstring fol);
public: void instalacja::start(HWND hWnd);
public: static void instalacja::odinstaluj(HINSTANCE, HWND, HWND);
public: void __cdecl instalacja::start(void * Args);
		HWND okno;
public: static bool czyJava();
	HWND progressbar;
	bool systemStart; bool wszyscy; LPCWSTR folder; wstring wfolder;
	bool skrotPulpit; bool skrotMenuStart;
	wstring folderStr;
	int ilePlikow;
	int ilePlikowGotowe;
	//WCHAR* lacz(LPCWSTR a, string b);
	int getHttp(char host[], int hostl, string path, int pathl);
	~instalacja();
	void instalacja::pobierz(string nazwa, wstring fold, instalacja*);
public: static int serNr;
public:static char** serwery;
public:static int* serweryl;
};
//HRESULT CreateLink(LPCWSTR lpszPathObj, LPCSTR lpszPathLink, LPCWSTR lpszDesc,LPCWSTR workingDir);
#include "start.h"