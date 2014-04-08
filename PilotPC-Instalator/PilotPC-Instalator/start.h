#pragma once
#include <CommCtrl.h>
#include "instalacja.h"
#include <Shobjidl.h> // dla ITaskbarList3
int WinMain(HINSTANCE hInstance,
	HINSTANCE hPrevInstance,
	LPSTR lpCmdLine,
	int nCmdShow
	);
void wyswietl3(HINSTANCE);
void convert(std::string &in, std::wstring &out);
void przerysuj(HWND);