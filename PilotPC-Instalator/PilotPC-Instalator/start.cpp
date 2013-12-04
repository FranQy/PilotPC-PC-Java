#include "stdafx.h"
#include <windows.h>
#include <objidl.h>
#include <gdiplus.h>
#include "start.h"
using namespace Gdiplus;
#include "instalacja.h"
#pragma comment (lib,"Gdiplus.lib")

HWND g_hPrzycisk, user1, userWiele, systemStart;
BOOL systemStartBool = true;
BOOL wszyscy = false;
HWND folder;
VOID OnPaint(HDC hdc)
{
	Graphics    graphics(hdc);
	SolidBrush  brush(Color(255, 0, 0, 0));
	FontFamily  fontFamily(L"Tahoma");
	Font        font(&fontFamily, 12, FontStyleRegular, UnitPixel);
	PointF      pointF(10.0f, 10.0f);

	graphics.DrawString(L"Podaj folder", -1, &font, pointF, &brush);

}

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
int WinMain(HINSTANCE hInstance,
	HINSTANCE hPrevInstance,
	LPSTR lpCmdLine,
	int nCmdShow
	);
	HWND                hWnd;
INT WINAPI WinMain(HINSTANCE hInstance, HINSTANCE, PSTR, INT iCmdShow)
{
	MSG                 msg;
	WNDCLASS            wndClass;
	GdiplusStartupInput gdiplusStartupInput;
	ULONG_PTR           gdiplusToken;

	// Initialize GDI+.
	GdiplusStartup(&gdiplusToken, &gdiplusStartupInput, NULL);

	wndClass.style = CS_HREDRAW | CS_VREDRAW;
	wndClass.lpfnWndProc = WndProc;
	wndClass.cbClsExtra = 0;
	wndClass.cbWndExtra = 0;
	wndClass.hInstance = hInstance;
	wndClass.hIcon = LoadIcon(NULL, IDI_APPLICATION);
	wndClass.hCursor = LoadCursor(NULL, IDC_ARROW);
	wndClass.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
	wndClass.lpszMenuName = NULL;
	wndClass.lpszClassName = TEXT("GettingStarted");

	RegisterClass(&wndClass);

	hWnd = CreateWindow(
		TEXT("GettingStarted"),   // window class name
		TEXT("PilotPC - Instalator"),  // window caption
		WS_OVERLAPPEDWINDOW,      // window style
		CW_USEDEFAULT,            // initial x position
		CW_USEDEFAULT,            // initial y position
		420,            // initial x size
		340,            // initial y size
		NULL,                     // parent window handle
		NULL,                     // window menu handle
		hInstance,                // program instance handle
		NULL);                    // creation parameters

	ShowWindow(hWnd, iCmdShow);

	UpdateWindow(hWnd);
	HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);
	g_hPrzycisk = CreateWindowEx(0, L"BUTTON", L"Instaluj", WS_CHILD | WS_VISIBLE,
		10, 250, 380, 40, hWnd, NULL, hInstance, NULL);
	SendMessage(g_hPrzycisk, WM_SETFONT, (WPARAM)hNormalFont, 0);
	folder = CreateWindowEx(WS_EX_CLIENTEDGE, L"EDIT", NULL, WS_CHILD | WS_VISIBLE | WS_BORDER,
		10, 25, 380, 25, hWnd, NULL, hInstance, NULL);
	SendMessage(folder, WM_SETFONT, (WPARAM)hNormalFont, 0);
	SetWindowText(folder, L"c:\\Program Files\\PilotPC");
	user1 = CreateWindowEx(0, L"BUTTON", L"Dla obecnego u¿ytkownika", WS_CHILD | WS_VISIBLE | BS_RADIOBUTTON,
		10, 60, 190, 30, hWnd, NULL, hInstance, NULL);
	SendMessage(user1, WM_SETFONT, (WPARAM)hNormalFont, 0);
	SendMessage(user1, BM_SETCHECK, 1, 0);



	userWiele = CreateWindowEx(0, L"BUTTON", L"Dla wszystkich urzytkowników", WS_CHILD | WS_VISIBLE | BS_RADIOBUTTON,
		200, 60, 190, 30, hWnd, NULL, hInstance, NULL);
	SendMessage(userWiele, WM_SETFONT, (WPARAM)hNormalFont, 0);
	systemStart = CreateWindowEx(0, L"BUTTON", L"Uruchamiaj automatycznie przy starcie systemu", WS_CHILD | WS_VISIBLE | BS_CHECKBOX,
		10, 90, 380, 30, hWnd, NULL, hInstance, NULL);
	SendMessage(systemStart, WM_SETFONT, (WPARAM)hNormalFont, 0);

	SendMessage(systemStart, BM_SETCHECK, 1, 0);
	while (GetMessage(&msg, NULL, 0, 0))
	{

		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	GdiplusShutdown(gdiplusToken);
	return msg.wParam;
}  // WinMain

LRESULT CALLBACK WndProc(HWND hWnd, UINT message,
	WPARAM wParam, LPARAM lParam)
{
	HDC          hdc;
	PAINTSTRUCT  ps;

	switch (message)
	{
	case WM_PAINT:
		hdc = BeginPaint(hWnd, &ps);
		OnPaint(hdc);
		EndPaint(hWnd, &ps);
		return 0;
	case WM_DESTROY:
		PostQuitMessage(0);
		return 0;
		break;
	case WM_COMMAND:
		if ((HWND)lParam == g_hPrzycisk)
		{
			DWORD dlugosc = GetWindowTextLength(folder);
			LPWSTR Bufor = (LPWSTR)GlobalAlloc(GPTR, dlugosc*2 + 2);
			GetWindowText(folder, Bufor, dlugosc + 2);
			//Bufor[dlugosc] = 0;
			instalacja* ins=new instalacja(systemStartBool, wszyscy, Bufor);
			(*ins).start(hWnd);
		}
		else if ((HWND)lParam == user1)
		{
			SendMessage(user1, BM_SETCHECK, 1, 0);
			SendMessage(userWiele, BM_SETCHECK, 0, 0);
			wszyscy = false;
		}
		else if ((HWND)lParam == userWiele)
		{
			SendMessage(user1, BM_SETCHECK, 0, 0);
			SendMessage(userWiele, BM_SETCHECK, 1, 0);
			wszyscy = true;
		}
		else if ((HWND)lParam == systemStart)
		{
			if (systemStartBool)
			{
				SendMessage(systemStart, BM_SETCHECK, 0, 0);
				systemStartBool = false;
			}
			else
			{
				SendMessage(systemStart, BM_SETCHECK, 1, 0);
				systemStartBool = true;
			}
		}

		break;
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
} // WndProc