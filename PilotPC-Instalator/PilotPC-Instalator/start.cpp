#include "stdafx.h"
#include "start.h"
#include "jezyk.h"
using namespace Gdiplus;
HWND przyciskJezyk[3];
HWND g_hPrzycisk, user1, userWiele, systemStart, skrotP, skrotMS;
BOOL systemStartBool = true;
BOOL wszyscy = false;
BOOL skrotPulpit = true;
BOOL skrotMenuStart = true;
HWND folder;
HWND FolderTxt;
HWND hProgressBar;
bool trwa = false;
// Enable Visual Style
#if defined _M_IX86
#pragma comment(linker,"/manifestdependency:\"type='win32' name='Microsoft.Windows.Common-Controls' version='6.0.0.0' processorArchitecture='x86' publicKeyToken='6595b64144ccf1df' language='*'\"")
#elif defined _M_IA64
#pragma comment(linker,"/manifestdependency:\"type='win32' name='Microsoft.Windows.Common-Controls' version='6.0.0.0' processorArchitecture='ia64' publicKeyToken='6595b64144ccf1df' language='*'\"")
#elif defined _M_X64
#pragma comment(linker,"/manifestdependency:\"type='win32' name='Microsoft.Windows.Common-Controls' version='6.0.0.0' processorArchitecture='amd64' publicKeyToken='6595b64144ccf1df' language='*'\"")
#else
#pragma comment(linker,"/manifestdependency:\"type='win32' name='Microsoft.Windows.Common-Controls' version='6.0.0.0' processorArchitecture='*' publicKeyToken='6595b64144ccf1df' language='*'\"")
#endif
#pragma endregion

#pragma comment (lib,"Gdiplus.lib")
#pragma comment (lib,"ws2_32.lib")
#pragma comment (lib,"comctl32.lib")
VOID OnPaint(HDC hdc)
{

}
HWND hWnd = NULL;
void wyswietl(HINSTANCE hInstance)
{

	HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);
	FolderTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
		SS_LEFT, 10, 10, 150, 200, hWnd, NULL, hInstance, NULL);
	SendMessage(FolderTxt, WM_SETFONT, (WPARAM)hNormalFont, 0);
	SetWindowText(FolderTxt, jezyk::napisy[n::WybierzFolder]);
	g_hPrzycisk = CreateWindowEx(0, L"BUTTON", jezyk::napisy[Instaluj], WS_CHILD | WS_VISIBLE,
		10, 250, 380, 40, hWnd, NULL, hInstance, NULL);
	SendMessage(g_hPrzycisk, WM_SETFONT, (WPARAM)hNormalFont, 0);
	//SendMessage(g_hPrzycisk, WM_CTLCOLORSTATIC, g_hBrush, 0);
	folder = CreateWindowEx(WS_EX_CLIENTEDGE, L"EDIT", NULL, WS_CHILD | WS_VISIBLE | WS_BORDER,
		10, 25, 380, 25, hWnd, NULL, hInstance, NULL);
	SendMessage(folder, WM_SETFONT, (WPARAM)hNormalFont, 0);
	SetWindowText(folder, L"c:\\Program Files\\PilotPC");
	user1 = CreateWindowEx(0, L"BUTTON", jezyk::napisy[DlaObecnegoUzytkownika], WS_CHILD | WS_VISIBLE | BS_RADIOBUTTON,
		10, 60, 190, 30, hWnd, NULL, hInstance, NULL);
	SendMessage(user1, WM_SETFONT, (WPARAM)hNormalFont, 0);
	SendMessage(user1, BM_SETCHECK, 1, 0);



	userWiele = CreateWindowEx(0, L"BUTTON", jezyk::napisy[DlaWszystkichUzytkownikow], WS_CHILD | WS_VISIBLE | BS_RADIOBUTTON,
		200, 60, 190, 30, hWnd, NULL, hInstance, NULL);
	SendMessage(userWiele, WM_SETFONT, (WPARAM)hNormalFont, 0);
	systemStart = CreateWindowEx(0, L"BUTTON", jezyk::napisy[UruchamiajAutomatyczneiPrzyStarcieSystemu], WS_CHILD | WS_VISIBLE | BS_CHECKBOX,
		10, 90, 380, 30, hWnd, NULL, hInstance, NULL);
	SendMessage(systemStart, WM_SETFONT, (WPARAM)hNormalFont, 0);

	SendMessage(systemStart, BM_SETCHECK, 1, 0);


	skrotP = CreateWindowEx(0, L"BUTTON", jezyk::napisy[SkrotNaPulpicie], WS_CHILD | WS_VISIBLE | BS_CHECKBOX,
		10, 120, 190, 30, hWnd, NULL, hInstance, NULL);
	SendMessage(skrotP, WM_SETFONT, (WPARAM)hNormalFont, 0);

	SendMessage(skrotP, BM_SETCHECK, 1, 0);

	/*	if (IsWindows8OrGreater())
	skrotMS = CreateWindowEx(0, L"BUTTON", L"Skrót na Ekranie Start", WS_CHILD | WS_VISIBLE | BS_CHECKBOX,
	200, 120, 190, 30, hWnd, NULL, hInstance, NULL);
	else*/
	skrotMS = CreateWindowEx(0, L"BUTTON", jezyk::napisy[SkrotWMenuStart], WS_CHILD | WS_VISIBLE | BS_CHECKBOX,
		200, 120, 190, 30, hWnd, NULL, hInstance, NULL);
	SendMessage(skrotMS, WM_SETFONT, (WPARAM)hNormalFont, 0);

	SendMessage(skrotMS, BM_SETCHECK, 1, 0);

	InitCommonControls();
	hProgressBar = CreateWindowEx(0, PROGRESS_CLASS, NULL, WS_CHILD | WS_VISIBLE | PBS_SMOOTH,
		10, 230, 380, 15, hWnd, (HMENU)200, hInstance, NULL);

	SendMessage(hProgressBar, PBM_SETRANGE, 0, (LPARAM)MAKELONG(0, 32 * 1024));
}

HBRUSH g_hBrush;
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
int WinMain(HINSTANCE hInstance,
	HINSTANCE hPrevInstance,
	LPSTR lpCmdLine,
	int nCmdShow
	);
LONG a, b;
HWND WyborOdinstaluj, WyborInstaluj, WyborTxt;
HINSTANCE hinstance;
void wybor(HINSTANCE hInstance){
	HKEY r;
	a = RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\PilotPC", 0, KEY_READ, &r);
	b = RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"SOFTWARE\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\PilotPC", 0, KEY_READ, &r);

	HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);
	if (a == 0 || b == 0)
	{
		WyborTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
			SS_LEFT, 10, 10, 150, 200, hWnd, NULL, hInstance, NULL);
		SendMessage(WyborTxt, WM_SETFONT, (WPARAM)hNormalFont, 0);
		SetWindowText(WyborTxt, jezyk::napisy[ProgramJestJuzZainstalowany]);
		WyborOdinstaluj = CreateWindowEx(0, L"BUTTON", jezyk::napisy[Odinstaluj], WS_CHILD | WS_VISIBLE,
			10, 30, 190, 270, hWnd, NULL, hInstance, NULL);
		SendMessage(WyborOdinstaluj, WM_SETFONT, (WPARAM)hNormalFont, 0);
		WyborInstaluj = CreateWindowEx(0, L"BUTTON", jezyk::napisy[InstalujPonownie], WS_CHILD | WS_VISIBLE,
			210, 30, 190, 270, hWnd, NULL, hInstance, NULL);
		SendMessage(WyborInstaluj, WM_SETFONT, (WPARAM)hNormalFont, 0);
	}
	else
		wyswietl(hInstance);
}

void wybierzJezyk(HINSTANCE hInstance)
{
	HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);
	przyciskJezyk[0] = CreateWindowEx(0, L"BUTTON", L"Polski", WS_CHILD | WS_VISIBLE,
		10, 10, 380, 90, hWnd, NULL, hInstance, NULL);
	SendMessage(przyciskJezyk[0], WM_SETFONT, (WPARAM)hNormalFont, 0);
	przyciskJezyk[1] = CreateWindowEx(0, L"BUTTON", L"English", WS_CHILD | WS_VISIBLE,
		10, 110, 380, 90, hWnd, NULL, hInstance, NULL);
	SendMessage(przyciskJezyk[1], WM_SETFONT, (WPARAM)hNormalFont, 0);
	przyciskJezyk[2] = CreateWindowEx(0, L"BUTTON", L"русский", WS_CHILD | WS_VISIBLE,
		10, 210, 380, 90, hWnd, NULL, hInstance, NULL);
	SendMessage(przyciskJezyk[1], WM_SETFONT, (WPARAM)hNormalFont, 0);
}
INT WINAPI WinMain(HINSTANCE hInstance, HINSTANCE, PSTR, INT iCmdShow)
{
	hinstance = hInstance;
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
		WS_MINIMIZEBOX | WS_MINIMIZE | WS_VISIBLE | WS_CAPTION | WS_OVERLAPPED | WS_SYSMENU,      // window style
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
	g_hBrush = CreateSolidBrush(RGB(255, 255, 255));
	HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);
	wybierzJezyk(hInstance);


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
	case WM_CTLCOLORSTATIC:
		return (LRESULT)g_hBrush;
	case WM_COMMAND:
		if ((HWND)lParam == g_hPrzycisk)
		{
			DWORD dlugosc = GetWindowTextLength(folder);
			LPWSTR Bufor = (LPWSTR)GlobalAlloc(GPTR, dlugosc * 2 + 2);
			GetWindowText(folder, Bufor, dlugosc + 2);
			trwa = true;
			//Bufor[dlugosc] = 0;
			DestroyWindow(user1);
			DestroyWindow(userWiele);
			DestroyWindow(systemStart);
			DestroyWindow(skrotMS);
			DestroyWindow(skrotP);
			DestroyWindow(folder);
			DestroyWindow(FolderTxt);
			DestroyWindow(g_hPrzycisk);

			instalacja* ins = new instalacja(systemStartBool, wszyscy, Bufor, skrotPulpit, skrotMenuStart, hProgressBar);
			(*ins).start(hWnd);
		}for (int i=0; i < 3; i++)
		{
			if ((HWND)lParam == przyciskJezyk[i])
			{
				for (int i2=0; i2 < 3; i2++)
				{
					DestroyWindow(przyciskJezyk[i2]);
				}
				jezyk::laduj((jezyk::jezyki)i);
				wybor(hinstance);
				return NULL;
			}
		}
		if ((HWND)lParam == WyborInstaluj)
		{
			DWORD dlugosc = GetWindowTextLength(folder);
			LPWSTR Bufor = (LPWSTR)GlobalAlloc(GPTR, dlugosc * 2 + 2);
			GetWindowText(folder, Bufor, dlugosc + 2);
			trwa = true;
			//Bufor[dlugosc] = 0;
			DestroyWindow(WyborInstaluj);
			DestroyWindow(WyborOdinstaluj);
			DestroyWindow(WyborTxt);
			wyswietl(hinstance);
		}if ((HWND)lParam == WyborOdinstaluj)
		{
			DWORD dlugosc = GetWindowTextLength(folder);
			LPWSTR Bufor = (LPWSTR)GlobalAlloc(GPTR, dlugosc * 2 + 2);
			GetWindowText(folder, Bufor, dlugosc + 2);
			trwa = true;
			//Bufor[dlugosc] = 0;
			DestroyWindow(WyborInstaluj);
			DestroyWindow(WyborOdinstaluj);
			DestroyWindow(WyborTxt);

			hProgressBar = CreateWindowEx(0, PROGRESS_CLASS, NULL, WS_CHILD | WS_VISIBLE | PBS_SMOOTH,
				10, 230, 380, 15, hWnd, (HMENU)200, hinstance, NULL);

			SendMessage(hProgressBar, PBM_SETRANGE, 0, (LPARAM)MAKELONG(0, 32 * 1024));
			instalacja::odinstaluj(hinstance, hProgressBar);
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
		else if ((HWND)lParam == skrotP)
		{
			if (skrotPulpit)
			{
				SendMessage(skrotP, BM_SETCHECK, 0, 0);
				skrotPulpit = false;
			}
			else
			{
				SendMessage(skrotP, BM_SETCHECK, 1, 0);
				skrotPulpit = true;
			}
		}
		else if ((HWND)lParam == skrotMS)
		{
			if (skrotMenuStart)
			{
				SendMessage(skrotMS, BM_SETCHECK, 0, 0);
				skrotMenuStart = false;
			}
			else
			{
				SendMessage(skrotMS, BM_SETCHECK, 1, 0);
				skrotMenuStart = true;
			}
		}

		break;
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
} // WndProc