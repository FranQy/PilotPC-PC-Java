#include "stdafx.h"
#include "start.h"
#include "jezyk.h"
#include <Windowsx.h>
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
int myszX, myszY;
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
#pragma comment (lib,"Msimg32.lib")

HINSTANCE hinstance;


HBRUSH ciemnyTlo;
HBRUSH ciemnyTlo2;
HBRUSH ciemnyTlo3;
HBRUSH ciemnyTlo4;
HBRUSH ciemnyTlo5;
HBRUSH jasnyTlo1;
HBRUSH jasnyTlo2;
HBRUSH czarny;
HBRUSH niebieski;


HFONT JaebeCzcionka = CreateFont(60, 0, 0, 0, 0, 0, 0, 0, 0, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, PROOF_QUALITY, 0, L"Segoe UI");
HFONT PilotPCCzcionka = CreateFont(20, 0, 0, 0, 0, 0, 0, 0, 0, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, PROOF_QUALITY, 0, L"Segoe UI");
HFONT PilotPCCzcionka2 = CreateFont(35, 0, 0, 0, 0, 0, 0, 0, 0, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, PROOF_QUALITY, 0, L"Segoe UI");
HFONT hNormalFont = CreateFont(18, 0, 0, 0, 0, 0, 0, 0, 0, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, PROOF_QUALITY, 0, L"Segoe UI");

int stringDlugosc(wchar_t* wej)
{
	int ret = 0;
	while (wej[ret] != 0)
		ret++;
	return ret;
}
void convert(std::string &in, std::wstring &out)
{
	out.resize(in.size());
	const char   *in_next = &*in.begin();
	wchar_t      *out_next = &*out.begin();


	typedef std::codecvt<wchar_t, char, std::mbstate_t> converter;
	const converter &my_converter = std::use_facet<converter>(std::locale());


	std::mbstate_t   my_state;


	my_converter.in(my_state,
		in.c_str(), in.c_str() + in.length(), in_next,
		&out[0], (&out[0]) + in.length(), out_next);
}
HBITMAP hbmObraz;
HBITMAP Checkbox1;
HBITMAP Checkbox2;
VOID OnPaint(HDC hdc)
{
	

	hbmObraz = LoadBitmap(hinstance, MAKEINTRESOURCE(1));
	RECT prost;
	prost.left = 0;
	prost.top = 500;
	prost.right = 450;
	prost.bottom = 650;
	FillRect(hdc, &prost, ciemnyTlo3);
	prost.left = 0;
	prost.top = 0;
	prost.right = 1;
	prost.bottom = 650;
	FillRect(hdc, &prost, czarny);
	prost.left = 449;
	prost.top = 0;
	prost.right = 450;
	prost.bottom = 650;
	FillRect(hdc, &prost, czarny);
	prost.left = 0;
	prost.top = 0;
	prost.right = 450;
	prost.bottom = 1;
	FillRect(hdc, &prost, czarny);
	prost.left = 0;
	prost.top = 649;
	prost.right = 450;
	prost.bottom = 650;
	FillRect(hdc, &prost, czarny);

	HDC hdcNowy = CreateCompatibleDC(hdc);
	HBITMAP hbmOld = (HBITMAP)SelectObject(hdcNowy, hbmObraz);
	BitBlt(hdc, 1, 79, 21, 41, hdcNowy, 0, 0, SRCCOPY);
	SelectObject(hdcNowy, hbmOld);
	DeleteDC(hdcNowy);


}
byte* jezykPrzyciskStan=new byte[3];
HWND hWnd = NULL;
HWND LicencjaZaakceptuj, LicencjaTxt, folderButton;
void wyswietl(HINSTANCE hInstance)
{//licencja
	LicencjaTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
		SS_LEFT, 25, 130, 400, 300, hWnd, NULL, hInstance, NULL);
	SendMessage(LicencjaTxt, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);
	SetWindowText(LicencjaTxt, jezyk::napisy[Licencja]);
	LicencjaZaakceptuj = CreateWindowEx(0, L"BUTTON", jezyk::napisy[Zaakceptuj], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		100, 525, 250, 100, hWnd, (HMENU)2002, hInstance, NULL);
	SendMessage(LicencjaZaakceptuj, WM_SETFONT, (WPARAM)PilotPCCzcionka2, 0);
}
HWND JavaTxt, JavaTakB, JavaNieB;
void wyswietl2(HINSTANCE hInstance)
{

	if (instalacja::czyJava())
		wyswietl3(hInstance);
	else
	{
		//HFONT PilotPCCzcionka = CreateFont(18, 7, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, L"Arial");
		JavaTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
			SS_LEFT, 25, 130, 400, 100, hWnd, NULL, hInstance, NULL);
		SendMessage(JavaTxt, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);
		SetWindowText(JavaTxt, jezyk::napisy[PotrzebnaJava]);
		JavaNieB = CreateWindowEx(0, L"BUTTON", jezyk::napisy[JavaNie], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
			0, 400, 450, 100, hWnd, (HMENU)2003, hInstance, NULL);
		SendMessage(JavaNieB, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);
		JavaTakB = CreateWindowEx(0, L"BUTTON", jezyk::napisy[JavaTak], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
			0, 180, 450, 220, hWnd, (HMENU)2004, hInstance, NULL);
		SendMessage(JavaTakB, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);

		HDC hdcOkno;
		hdcOkno = GetDC(hWnd);
		RECT prost;
		prost.left = 0;
		prost.top = 500;
		prost.right = 450;
		prost.bottom = 650;


		FillRect(hdcOkno, &prost, ciemnyTlo3);
		ReleaseDC(hWnd, hdcOkno);
	}

}
void wyswietl3(HINSTANCE hInstance)
{

	FolderTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
		SS_LEFT, 25, 180, 400, 100, hWnd, NULL, hInstance, NULL);

	//HFONT PilotPCCzcionka = CreateFont(18, 7, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, L"Arial");
	SendMessage(FolderTxt, WM_SETFONT, (WPARAM)hNormalFont, 0);
	SetWindowText(FolderTxt, jezyk::napisy[n::WybierzFolder]);
	SendMessage(FolderTxt, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);
	g_hPrzycisk = CreateWindowEx(0, L"BUTTON", jezyk::napisy[Instaluj], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		100, 525, 250, 100, hWnd, (HMENU)2010, hInstance, NULL);
	SendMessage(g_hPrzycisk, WM_SETFONT, (WPARAM)PilotPCCzcionka2, 0);
	//SendMessage(g_hPrzycisk, WM_CTLCOLORSTATIC, g_hBrush, 0);
	folder = CreateWindowEx(WS_EX_CLIENTEDGE, L"EDIT", NULL, WS_CHILD | WS_VISIBLE,
		25, 200, 325, 25, hWnd, NULL, hInstance, NULL);
	SendMessage(folder, WM_SETFONT, (WPARAM)hNormalFont, 0);
	SetWindowText(folder, L"c:\\Program Files\\PilotPC");


	folderButton = CreateWindowEx(0, L"BUTTON", L"Wybierz", WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		355, 200, 70, 25, hWnd, (HMENU)2999, hInstance, NULL);
	SendMessage(folderButton, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);

	user1 = CreateWindowEx(0, L"BUTTON", jezyk::napisy[DlaObecnegoUzytkownika], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		25, 230, 400, 25, hWnd, (HMENU)3000, hInstance, NULL);
	SendMessage(user1, WM_SETFONT, (WPARAM)hNormalFont, 0);
	SendMessage(user1, BM_SETCHECK, 1, 0);



	userWiele = CreateWindowEx(0, L"BUTTON", jezyk::napisy[DlaWszystkichUzytkownikow], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		25, 260, 400, 25, hWnd, (HMENU)3001, hInstance, NULL);
	SendMessage(userWiele, WM_SETFONT, (WPARAM)hNormalFont, 0);
	systemStart = CreateWindowEx(0, L"BUTTON", jezyk::napisy[UruchamiajAutomatyczneiPrzyStarcieSystemu], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		25, 300, 400, 25, hWnd, (HMENU)3002, hInstance, NULL);
	SendMessage(systemStart, WM_SETFONT, (WPARAM)hNormalFont, 0);

	SendMessage(systemStart, BM_SETCHECK, 1, 0);


	skrotP = CreateWindowEx(0, L"BUTTON", jezyk::napisy[SkrotNaPulpicie], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		25, 330, 400, 25, hWnd, (HMENU)3003, hInstance, NULL);
	SendMessage(skrotP, WM_SETFONT, (WPARAM)hNormalFont, 0);

	SendMessage(skrotP, BM_SETCHECK, 1, 0);

	/*	if (IsWindows8OrGreater())
	skrotMS = CreateWindowEx(0, L"BUTTON", L"Skrót na Ekranie Start", WS_CHILD | WS_VISIBLE | BS_CHECKBOX,
	200, 120, 190, 30, hWnd, NULL, hInstance, NULL);
	else*/
	skrotMS = CreateWindowEx(0, L"BUTTON", jezyk::napisy[SkrotWMenuStart], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		25, 360, 400, 25, hWnd, (HMENU)3004, hInstance, NULL);
	SendMessage(skrotMS, WM_SETFONT, (WPARAM)hNormalFont, 0);

	SendMessage(skrotMS, BM_SETCHECK, 1, 0);

	InitCommonControls();
}

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
int WinMain(HINSTANCE hInstance,
	HINSTANCE hPrevInstance,
	LPSTR lpCmdLine,
	int nCmdShow
	);
LONG a, b;
HWND WyborOdinstaluj, WyborInstaluj, WyborTxt;
void wybor(HINSTANCE hInstance){
	HKEY r;
	a = RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\PilotPC", 0, KEY_READ, &r);
	b = RegOpenKeyEx(HKEY_CURRENT_USER, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\PilotPC", 0, KEY_READ, &r);

	//HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);
	if (a == 0 || b == 0)
	{

		//HFONT PilotPCCzcionka = CreateFont(18, 7, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, L"Arial");
		WyborTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
			SS_LEFT, 25, 130, 400, 100, hWnd, NULL, hInstance, NULL);
		SendMessage(WyborTxt, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);
		SetWindowText(WyborTxt, jezyk::napisy[ProgramJestJuzZainstalowany]);
		WyborOdinstaluj = CreateWindowEx(0, L"BUTTON", jezyk::napisy[Odinstaluj], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
			1, 180, 448, 220, hWnd, (HMENU)2000, hInstance, NULL);
		SendMessage(WyborOdinstaluj, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);
		WyborInstaluj = CreateWindowEx(0, L"BUTTON", jezyk::napisy[InstalujPonownie], WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
			1, 400, 448, 100, hWnd, (HMENU)2001, hInstance, NULL);
		SendMessage(WyborInstaluj, WM_SETFONT, (WPARAM)PilotPCCzcionka, 0);
	}
	else
		wyswietl(hInstance);
}
HWND przyciskX;
HWND przyciskMin;
HWND CopyTxt;
void rysujStałe(HINSTANCE hInstance)
{
	//HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);
	HFONT JaebeCzcionkaCopy = CreateFont(12, 6, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, L"Arial");
	HWND JaebeTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
		SS_LEFT, 165, 10, 449-165, 50, hWnd, NULL, hInstance, NULL);
	SendMessage(JaebeTxt, WM_SETFONT, (WPARAM)JaebeCzcionka, 0);
	SetWindowText(JaebeTxt, L"Jaebe™");
	//CopyTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
	//	SS_LEFT, 30, 630, 300, 50, hWnd, NULL, hInstance, NULL);
	//SendMessage(CopyTxt, WM_SETFONT, (WPARAM)JaebeCzcionkaCopy, 0);
	//SetWindowText(CopyTxt, L"©");
	HWND PilotPCTxt = CreateWindowEx(0, L"STATIC", NULL, WS_CHILD | WS_VISIBLE |
		SS_LEFT, 20, 80, 300, 40, hWnd, NULL, hInstance, NULL);
	SendMessage(PilotPCTxt, WM_SETFONT, (WPARAM)PilotPCCzcionka2, 0);
	SetWindowText(PilotPCTxt, L"PilotPC");
	HDC hdcOkno;
	hdcOkno = GetDC(hWnd);
	RECT prost;
	prost.left = 1;
	prost.top = 500;
	prost.right = 448;
	prost.bottom = 649;


	FillRect(hdcOkno, &prost, ciemnyTlo3);
	ReleaseDC(hWnd, hdcOkno);

	HFONT XCzcionka = CreateFont(15, 7, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, L"Arial");
	przyciskX = CreateWindowEx(0, L"BUTTON", L"X", WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		424, 1, 25, 25, hWnd, (HMENU)1999, hInstance, NULL);
	SendMessage(przyciskX, WM_SETFONT, (WPARAM)XCzcionka, 0);
	przyciskMin = CreateWindowEx(0, L"BUTTON", L"_", WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		399, 1, 25, 25, hWnd, (HMENU)1998, hInstance, NULL);
	SendMessage(przyciskMin, WM_SETFONT, (WPARAM)XCzcionka, 0);
}

HFONT jezykCzcionka = CreateFont(20, 8, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, L"Arial");
#define BTNRED  1001
#define BTNBLUE 1002
void wybierzJezyk(HINSTANCE hInstance)
{
	jezyk::nazwyLoad();
	HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);

	przyciskJezyk[0] = CreateWindowEx(0, L"BUTTON", L"Polski", WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		1, 150, 448, 113, hWnd, (HMENU)1000, hInstance, NULL);
	SendMessage(przyciskJezyk[0], WM_SETFONT, (WPARAM)jezykCzcionka, 0);
	przyciskJezyk[1] = CreateWindowEx(0, L"BUTTON", L"English", WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		1, 263, 448, 113, hWnd, (HMENU)1001, hInstance, NULL);
	SendMessage(przyciskJezyk[1], WM_SETFONT, (WPARAM)jezykCzcionka, 0);
	przyciskJezyk[2] = CreateWindowEx(0, L"BUTTON", L"русский", WS_CHILD | WS_VISIBLE | BS_OWNERDRAW,
		1, 376, 448, 114, hWnd, (HMENU)1002, hInstance, NULL);
	SendMessage(przyciskJezyk[2], WM_SETFONT, (WPARAM)jezykCzcionka, 0);


}

void drawButtonRed(HDC hDC, UINT itemState, HWND hwnd, HBRUSH zaznaczone, HBRUSH niezaznaczone, LPWSTR txt, int Txtlen,int szerokosc,int wysokosc){


	RECT prost;
	prost.left = 0;
	prost.top = 0;
	prost.right = szerokosc;
	prost.bottom = wysokosc;
	if (itemState & ODS_SELECTED) {


		FillRect(hDC, &prost, zaznaczone);
	}
	else if (itemState & ODS_HOTLIGHT) {


		FillRect(hDC, &prost, zaznaczone);
	}
	else{

		FillRect(hDC, &prost, niezaznaczone);

	}
	RECT rc;
	GetClientRect(hwnd, &rc);

	SetBkMode(hDC, TRANSPARENT);

	char buf[255];
	GetWindowText(hwnd, txt, 255);

	SetTextColor(hDC, RGB(255, 255, 255));
	DrawText(hDC, txt, Txtlen, &rc, DT_CENTER | DT_VCENTER | DT_SINGLELINE);
}
void drawButtonRed(DRAWITEMSTRUCT *dis, HWND hwnd, HBRUSH zaznaczone, HBRUSH niezaznaczone, LPWSTR txt, int Txtlen){
	drawButtonRed(dis->hDC, dis->itemState, hwnd, zaznaczone, niezaznaczone,txt, Txtlen,450,650);
}
INT WINAPI WinMain(HINSTANCE hInstance, HINSTANCE, PSTR test, INT iCmdShow)
{
	hbmObraz = LoadBitmap(hinstance, MAKEINTRESOURCE(1));

	ciemnyTlo = CreateSolidBrush(RGB(38, 33, 27));
	ciemnyTlo2 = CreateSolidBrush(RGB(48, 43, 35));
	ciemnyTlo3 = CreateSolidBrush(RGB(28, 24, 20));
	ciemnyTlo4 = CreateSolidBrush(RGB(58, 50, 40));
	ciemnyTlo5 = CreateSolidBrush(RGB(68, 60, 50));
	jasnyTlo1 = CreateSolidBrush(RGB(225, 220, 210));
	jasnyTlo2 = CreateSolidBrush(RGB(255, 255, 255));
	czarny = CreateSolidBrush(RGB(0, 0, 0));
	niebieski = CreateSolidBrush(RGB(20, 40, 240));
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
	wndClass.hbrBackground = (HBRUSH)ciemnyTlo;
	wndClass.lpszMenuName = NULL;
	wndClass.lpszClassName = TEXT("GettingStarted");

	RegisterClass(&wndClass);

	hWnd = CreateWindow(
		TEXT("GettingStarted"),   // window class name
		TEXT("PilotPC - Instalator"),  // window caption
		WS_POPUP | WS_MINIMIZE,      // window style
		GetSystemMetrics(SM_CXSCREEN) / 2 - 225,            // initial x position
		GetSystemMetrics(SM_CYSCREEN) / 2 - 325,            // initial y position
		450,            // initial x size
		650,            // initial y size
		NULL,                     // parent window handle
		NULL,                     // window menu handle
		hInstance,                // program instance handle
		NULL);                    // creation parameters
	//SendMessage(hWnd, WM_CTLCOLORSTATIC, g_hBrush, 0);
	ShowWindow(hWnd, iCmdShow);

	UpdateWindow(hWnd);
	HFONT hNormalFont = (HFONT)GetStockObject(DEFAULT_GUI_FONT);

	rysujStałe(hInstance);

	string parametry = string(test);

	if (parametry.length() > 1 && parametry.substr(0, 2) == string("PL"))
	{
		jezyk::laduj(jezyk::jezyki::Polski);

	}
	else if (parametry.length() > 1 && parametry.substr(0, 2) == string("EN"))
	{
		jezyk::laduj(jezyk::jezyki::Angielski);

	}
	else if (parametry.length() > 1 && parametry.substr(0, 2) == string("RU"))
	{
		jezyk::laduj(jezyk::jezyki::Rosyjski);

	}
	if (parametry.length() > 1 && (parametry.substr(0, 2) == string("PL") || parametry.substr(0, 2) == string("RU") || parametry.substr(0, 2) == string("EN")))
	{
		if (parametry.length() > 4 && (parametry.substr(3, 2) == string("/u")))
		{
			DWORD dlugosc = GetWindowTextLength(folder);
			LPWSTR Bufor = (LPWSTR)GlobalAlloc(GPTR, dlugosc * 2 + 2);
			GetWindowText(folder, Bufor, dlugosc + 2);
			trwa = true;

			hProgressBar = CreateWindowEx(0, PROGRESS_CLASS, NULL, WS_CHILD | WS_VISIBLE | PBS_SMOOTH,
				25, 250, 400, 20, hWnd, (HMENU)200, hinstance, NULL);
			SendMessage(hProgressBar, PBM_SETBKCOLOR, 0, (LPARAM)niebieski);

			SendMessage(hProgressBar, PBM_SETRANGE, 0, (LPARAM)MAKELONG(0, 32 * 1024));
			instalacja::odinstaluj(hinstance, hProgressBar, hWnd);
		}
		else if (parametry.length() > 4 && parametry[3] == '"')
		{
			string folder1;
			wstring folder2;
			LPCWSTR folder3 = L"c:\program files\PilotPC";
			systemStartBool = wszyscy = skrotMenuStart = skrotPulpit = false;
			int i = 4;
			for (; i < parametry.length(); i++)
			{
				if (parametry[i] == '"')
				{
					folder1 = parametry.substr(4, i - 4);
					convert(folder1, folder2);
					folder3 = folder2.c_str();
					break;
				}

			}
			for (; i < parametry.length(); i++)
			{
				if (parametry[i] == '/')
				{
					if (parametry[i + 1] == 's')
					{
						systemStartBool = true;
						i = i + 2;
					}
					else if (parametry[i + 1] == 'm')
					{
						skrotMenuStart = true;
						i = i + 2;
					}
					else if (parametry[i + 1] == 'p')
					{
						skrotPulpit = true;
						i = i + 2;
					}
					else if (parametry[i + 1] == 'w')
					{
						wszyscy = true;
						i = i + 2;
					}
				}

			}
			trwa = true;

			hProgressBar = CreateWindowEx(0, PROGRESS_CLASS, NULL, WS_CHILD | WS_VISIBLE | PBS_SMOOTH,
				25, 250, 400, 20, hWnd, (HMENU)200, hinstance, NULL);
			SendMessage(hProgressBar, PBM_SETBKCOLOR, 0, (LPARAM)niebieski);

			SendMessage(hProgressBar, PBM_SETRANGE, 0, (LPARAM)MAKELONG(0, 32 * 1024));
			instalacja* ins = new instalacja(systemStartBool, wszyscy, folder3, skrotPulpit, skrotMenuStart, hProgressBar, folder2);
			(*ins).start(hWnd);
		}
		else
			wybor(hinstance);

	}
	else
		wybierzJezyk(hInstance);

	while (GetMessage(&msg, NULL, 0, 0))
	{
		if (msg.message == WM_MOUSEMOVE)
		{
			int xPos = GET_X_LPARAM(msg.lParam);
			int yPos = GET_Y_LPARAM(msg.lParam);

			for (int i = 0; i < 3; i++)
			{
				if (msg.hwnd == przyciskJezyk[i]&&jezykPrzyciskStan[i]==0)
				{
					jezykPrzyciskStan[i] = 1;
					drawButtonRed(GetDC(przyciskJezyk[i]), 0, przyciskJezyk[i], ciemnyTlo2, ciemnyTlo3, jezyk::nazwyJezykow[i], jezyk::nazwyJezykowLen[i], 448, 113);

				}
				else if(msg.hwnd != przyciskJezyk[i] && jezykPrzyciskStan[i] != 0)
				{
					jezykPrzyciskStan[i] = 0;
					drawButtonRed(GetDC(przyciskJezyk[i]), 0, przyciskJezyk[i], ciemnyTlo2, ciemnyTlo, jezyk::nazwyJezykow[i], jezyk::nazwyJezykowLen[i], 448, 113);

				}
			}
		}
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	GdiplusShutdown(gdiplusToken);



	return msg.wParam;
}  // WinMain


void drawRadio(HDC hDC, int itemState, HWND hwnd, HBRUSH tlo, LPWSTR txt, int Txtlen, boolean zaznaczone){

	if (Checkbox1 == NULL)
		Checkbox1 = LoadBitmap(hinstance, MAKEINTRESOURCE(2));
	if (Checkbox2 == NULL)
		Checkbox2 = LoadBitmap(hinstance, MAKEINTRESOURCE(3));
	RECT prost;
	prost.left = 0;
	prost.top = 0;
	prost.right = 32;
	prost.bottom = 32;

	RECT prostTlo;
	prostTlo.left = 0;
	prostTlo.top = 0;
	prostTlo.right = 400;
	prostTlo.bottom = 25;
	FillRect(hDC, &prostTlo, tlo);
	/*HBRUSH PedzelZiel, Pudelko;
	HPEN OlowekCzerw, Piornik;
	if (itemState & ODS_SELECTED) {


		PedzelZiel = CreateSolidBrush(0x444444);
		OlowekCzerw = CreatePen(PS_DOT, 1, 0xFFFFFF);
	}
	else if (itemState & ODS_HOTLIGHT) {


		PedzelZiel = CreateSolidBrush(0x444444);
		OlowekCzerw = CreatePen(PS_DOT, 1, 0xFFFFFF);
	}
	else{

		PedzelZiel = CreateSolidBrush(0x000000);
		OlowekCzerw = CreatePen(PS_DOT, 1, 0xEEEEEE);

	}
	Pudelko = (HBRUSH)SelectObject(hDC, PedzelZiel);
	Piornik = (HPEN)SelectObject(hDC, OlowekCzerw);
	Ellipse(hDC, 0, 0, 24, 24);
	SelectObject(hDC, Pudelko);
	SelectObject(hDC, Piornik);
	if (zaznaczone)
	{
		Ellipse(hDC, 8, 8, 16, 16);

	}
	DeleteObject(OlowekCzerw);
	DeleteObject(PedzelZiel);*/
	RECT rc;
	GetClientRect(hwnd, &rc);

	SetBkMode(hDC, TRANSPARENT);


	HDC hdcNowy = CreateCompatibleDC(hDC);
	HBITMAP hbmOld;
	if (zaznaczone)
	hbmOld = (HBITMAP)SelectObject(hdcNowy, Checkbox1);
	else
	hbmOld = (HBITMAP)SelectObject(hdcNowy, Checkbox2);
	BitBlt(hDC, 4, 5, 16, 16, hdcNowy, 0, 0, SRCCOPY);
	SelectObject(hdcNowy, hbmOld);
	DeleteDC(hdcNowy);

	char buf[255];
	GetWindowText(hwnd, txt, 255);
	rc.left += 28;
	SetTextColor(hDC, RGB(255, 255, 255));
	SelectObject(hDC, hNormalFont);
	DrawText(hDC, txt, Txtlen, &rc, DT_VCENTER | DT_SINGLELINE);
}

void drawButtonBlue(DRAWITEMSTRUCT *dis, HWND hwnd){
	RECT rc;
	GetClientRect(hwnd, &rc);

	if (dis->itemState & ODS_SELECTED) {
		TRIVERTEX vertex[2];
		vertex[0].x = 0;
		vertex[0].y = 0;
		vertex[0].Red = 0x0000;
		vertex[0].Green = 0x0000;
		vertex[0].Blue = 0xffff;

		vertex[1].x = 100;
		vertex[1].y = 50;
		vertex[1].Red = 0x0000;
		vertex[1].Green = 0x0000;
		vertex[1].Blue = 0xcccc;


		GRADIENT_RECT gRect;
		gRect.UpperLeft = 0;
		gRect.LowerRight = 1;

		GradientFill(dis->hDC, vertex, 2, &gRect, 1, GRADIENT_FILL_RECT_H);
	}
	else{
		TRIVERTEX vertex[2];
		vertex[0].x = 0;
		vertex[0].y = 0;
		vertex[0].Red = 0x0000;
		vertex[0].Green = 0x0000;
		vertex[0].Blue = 0x9999;

		vertex[1].x = 100;
		vertex[1].y = 50;
		vertex[1].Red = 0x0000;
		vertex[1].Green = 0x0000;
		vertex[1].Blue = 0xdddd;

		GRADIENT_RECT gRect;
		gRect.UpperLeft = 0;
		gRect.LowerRight = 1;

		GradientFill(dis->hDC, vertex, 2, &gRect, 1, GRADIENT_FILL_RECT_H);
	}

	SetBkMode(dis->hDC, TRANSPARENT);

	char buf[255];
	GetWindowText(hwnd, L"aaa", 255);
	DrawText(dis->hDC, L"aaa", 3, &rc, DT_CENTER | DT_VCENTER | DT_SINGLELINE);
}

LRESULT CALLBACK WndProc(HWND hWnd, UINT message,
	WPARAM wParam, LPARAM lParam)
{
	HDC          hdc;
	PAINTSTRUCT  ps;

	int xPos, yPos;
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
		break; case WM_CTLCOLORSTATIC:
		{
			hdc = (HDC)wParam;

			//for (int i = 0; i < 3;i++)
			//if (hCtl == przyciskJezyk[i])
			if ((HWND)lParam == CopyTxt)
			{
				SetBkMode(hdc, TRANSPARENT);
				SetTextColor(hdc, RGB(255, 255, 255));
				return(LRESULT)ciemnyTlo3;
			}
			else
			{
				SetBkMode(hdc, TRANSPARENT);
				SetTextColor(hdc, RGB(255, 255, 255));
				return(LRESULT)ciemnyTlo;
			}
		}
			break;

		case WM_CTLCOLORDLG:

			hdc = (HDC)wParam;

			//for (int i = 0; i < 3;i++)
			//if (hCtl == przyciskJezyk[i])
			if ((HWND)lParam == CopyTxt)
			{
				SetBkMode(hdc, TRANSPARENT);
				SetTextColor(hdc, RGB(255, 255, 255));
				return(LRESULT)ciemnyTlo3;
			}
			else
			{
				SetBkMode(hdc, TRANSPARENT);
				SetTextColor(hdc, RGB(255, 255, 255));
				return(LRESULT)ciemnyTlo;
			}

			break;
		case WM_DRAWITEM:
		{
							DRAWITEMSTRUCT *dis = (DRAWITEMSTRUCT*)lParam;
							if (dis->CtlID > 999 && dis->CtlID < 1003)
								drawButtonRed(dis, przyciskJezyk[dis->CtlID - 1000], ciemnyTlo2, ciemnyTlo, jezyk::nazwyJezykow[dis->CtlID - 1000], jezyk::nazwyJezykowLen[dis->CtlID - 1000]);
							else if (dis->CtlID == 2000)
								drawButtonRed(dis, WyborOdinstaluj, ciemnyTlo4, ciemnyTlo5, jezyk::napisy[Odinstaluj], stringDlugosc(jezyk::napisy[Odinstaluj]));
							else if (dis->CtlID == 2001)
								drawButtonRed(dis, WyborInstaluj, ciemnyTlo2, ciemnyTlo, jezyk::napisy[InstalujPonownie], stringDlugosc(jezyk::napisy[InstalujPonownie]));
							else if (dis->CtlID == 1998)
								drawButtonRed(dis, przyciskX, ciemnyTlo2, ciemnyTlo, L"_", 1);
							else if (dis->CtlID == 1999)
								drawButtonRed(dis, przyciskX, ciemnyTlo2, ciemnyTlo, L"X", 1);
							else if (dis->CtlID == 2002)
								drawButtonRed(dis, LicencjaZaakceptuj, ciemnyTlo, ciemnyTlo3, jezyk::napisy[Zaakceptuj], stringDlugosc(jezyk::napisy[Zaakceptuj]));
							else if (dis->CtlID == 2003)
								drawButtonRed(dis, JavaNieB, ciemnyTlo2, ciemnyTlo, jezyk::napisy[JavaNie], stringDlugosc(jezyk::napisy[JavaNie]));
							else if (dis->CtlID == 2004)
								drawButtonRed(dis, JavaTakB, ciemnyTlo4, ciemnyTlo5, jezyk::napisy[JavaTak], stringDlugosc(jezyk::napisy[JavaTak]));
							else if (dis->CtlID == 2010)
								drawButtonRed(dis, g_hPrzycisk, ciemnyTlo, ciemnyTlo3, jezyk::napisy[Instaluj], stringDlugosc(jezyk::napisy[Instaluj]));
							else if (dis->CtlID == 2999)
								drawButtonRed(dis, folderButton, ciemnyTlo2, ciemnyTlo3, L"Wybierz", 7);
							else if (dis->CtlID == 3000)
								drawRadio(dis->hDC, dis->itemState, user1, ciemnyTlo, jezyk::napisy[DlaObecnegoUzytkownika], stringDlugosc(jezyk::napisy[DlaObecnegoUzytkownika]), !wszyscy);
							else if (dis->CtlID == 3001)
								drawRadio(dis->hDC, dis->itemState, userWiele, ciemnyTlo, jezyk::napisy[DlaWszystkichUzytkownikow], stringDlugosc(jezyk::napisy[DlaWszystkichUzytkownikow]), wszyscy);
							else if (dis->CtlID == 3002)
								drawRadio(dis->hDC, dis->itemState, systemStart, ciemnyTlo, jezyk::napisy[UruchamiajAutomatyczneiPrzyStarcieSystemu], stringDlugosc(jezyk::napisy[UruchamiajAutomatyczneiPrzyStarcieSystemu]), systemStartBool);
							else if (dis->CtlID == 3003)
								drawRadio(dis->hDC, dis->itemState, skrotP, ciemnyTlo, jezyk::napisy[SkrotNaPulpicie], stringDlugosc(jezyk::napisy[SkrotNaPulpicie]), skrotPulpit);
							else if (dis->CtlID == 3004)
								drawRadio(dis->hDC, dis->itemState, skrotMS, ciemnyTlo, jezyk::napisy[SkrotWMenuStart], stringDlugosc(jezyk::napisy[SkrotWMenuStart]), skrotMenuStart);

							/*switch (dis->CtlID){
							case (int)1000: drawButtonRed(dis, przyciskJezyk[0]); break;
							case (int)1001: drawButtonBlue(dis, przyciskJezyk[1]); break;
							}*/
							return TRUE;
		}

		case WM_COMMAND:

			/*switch (LOWORD(wParam)){
			case BTNRED: MessageBox(hWnd, L"Red Button Pressed", L"Red", MB_OK); break;
			case BTNBLUE: MessageBox(hWnd, L"Blue Button Pressed", L"Blue", MB_OK); break;
			}*/

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

				hProgressBar = CreateWindowEx(0, PROGRESS_CLASS, NULL, WS_CHILD | WS_VISIBLE | PBS_SMOOTH,
					25, 250, 400, 20, hWnd, (HMENU)200, hinstance, NULL);
				SendMessage(hProgressBar, PBM_SETBKCOLOR, 0, (LPARAM)niebieski);

				SendMessage(hProgressBar, PBM_SETRANGE, 0, (LPARAM)MAKELONG(0, 32 * 1024));
				wstring wfolder;
				instalacja* ins = new instalacja(systemStartBool, wszyscy, Bufor, skrotPulpit, skrotMenuStart, hProgressBar, wstring(Bufor));
				(*ins).start(hWnd);
			}for (int i = 0; i < 3; i++)
			{
				if ((HWND)lParam == przyciskJezyk[i])
				{
					for (int i2 = 0; i2 < 3; i2++)
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
					25, 250, 400, 20, hWnd, (HMENU)200, hinstance, NULL);
				SendMessage(hProgressBar, PBM_SETBKCOLOR, 0, (LPARAM)niebieski);

				SendMessage(hProgressBar, PBM_SETRANGE, 0, (LPARAM)MAKELONG(0, 32 * 1024));
				instalacja::odinstaluj(hinstance, hProgressBar, hWnd);
			}
			else if ((HWND)lParam == user1)
			{
				SendMessage(user1, BM_SETCHECK, 1, 0);
				SendMessage(userWiele, BM_SETCHECK, 0, 0);
				wszyscy = false;
				if (userWiele != NULL)
					drawRadio(GetDC(userWiele), 0, userWiele, ciemnyTlo, jezyk::napisy[DlaWszystkichUzytkownikow], stringDlugosc(jezyk::napisy[DlaWszystkichUzytkownikow]), wszyscy);
				if (user1 != NULL)
					drawRadio(GetDC(user1), 0, user1, ciemnyTlo, jezyk::napisy[DlaObecnegoUzytkownika], stringDlugosc(jezyk::napisy[DlaObecnegoUzytkownika]), !wszyscy);
			}
			else if ((HWND)lParam == userWiele)
			{
				SendMessage(user1, BM_SETCHECK, 0, 0);
				SendMessage(userWiele, BM_SETCHECK, 1, 0);
				wszyscy = true;
				if (userWiele != NULL)
					drawRadio(GetDC(userWiele), 0, userWiele, ciemnyTlo, jezyk::napisy[DlaWszystkichUzytkownikow], stringDlugosc(jezyk::napisy[DlaWszystkichUzytkownikow]), wszyscy);
				if (user1 != NULL)
					drawRadio(GetDC(user1), 0, user1, ciemnyTlo, jezyk::napisy[DlaObecnegoUzytkownika], stringDlugosc(jezyk::napisy[DlaObecnegoUzytkownika]), !wszyscy);
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
			else if ((HWND)lParam == przyciskX)
			{
				exit(0);
			}
			else if ((HWND)lParam == przyciskMin)
			{
				ShowWindow(hWnd, SW_MINIMIZE);
			}
			else if ((HWND)lParam == LicencjaZaakceptuj)
			{
				DWORD dlugosc = GetWindowTextLength(folder);
				LPWSTR Bufor = (LPWSTR)GlobalAlloc(GPTR, dlugosc * 2 + 2);
				GetWindowText(folder, Bufor, dlugosc + 2);
				trwa = true;
				//Bufor[dlugosc] = 0;
				DestroyWindow(LicencjaZaakceptuj);
				DestroyWindow(LicencjaTxt);
				wyswietl2(hinstance);
			}
			else if ((HWND)lParam == JavaNieB)
			{
				exit(0);
			}
			else if ((HWND)lParam == JavaTakB)
			{
				DestroyWindow(JavaTakB);
				DestroyWindow(JavaNieB);
				DestroyWindow(JavaTxt);
				wyswietl3(hinstance);
			}

			break;
		default:
			return DefWindowProc(hWnd, message, wParam, lParam);
	}
} // WndProc