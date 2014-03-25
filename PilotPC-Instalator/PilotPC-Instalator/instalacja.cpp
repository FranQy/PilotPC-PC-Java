#include "instalacja.h"
#include <stdio.h>
#include <stdlib.h>
#include <shellapi.h>
#ifdef WIN32
#include <io.h>
#else
#include <unistd.h>
#endif
#include <Winsock2.h>
#include <comdef.h>
#include <Winbase.h>
#include "stdafx.h"
#include "windows.h"
#include "winnls.h"
#include "shobjidl.h"
#include "objbase.h"
#include "objidl.h"
#include "shlguid.h"
#include <process.h>
#include <Shobjidl.h>
#include <Sddl.h>
#include <ShObjIdl.h>
#include "jezyk.h"
using namespace std;

/*WCHAR* lacz(LPCWSTR a, string b)
{
int i = 0;
for (; a[i] != 0; i++)
{
}
cstring ret = (WCHAR*)GlobalAlloc(GPTR, (i + b.length()) * 2);

for (int i = 0; a[i] != 0; i++)
{
ret[i] = a[i];
}
for (int i2 = 0; i2<b.length(); i++)
{
ret[i + i2] = a[i2];
}
return ret;
}*/


instalacja::instalacja(bool _systemStart, bool _wszyscy, LPCWSTR _folder, bool _skrotPulpit, bool _skrotMenuStart, HWND _progressbar)
{


	systemStart = _systemStart;
	wszyscy = _wszyscy;
	folder = _folder;
	skrotMenuStart = _skrotMenuStart;
	skrotPulpit = _skrotPulpit;
	folderStr = wstring(folder);
	progressbar = _progressbar;


}
void __cdecl watekStart(void * Args)
{
	// Check the current process's "run as administrator" status.
	BOOL fIsRunAsAdmin;
	try
	{
		fIsRunAsAdmin = IsRunAsAdmin();


		// Elevate the process if it is not run as administrator.
		if (!fIsRunAsAdmin)
		{
			wchar_t szPath[MAX_PATH];
			if (GetModuleFileName(NULL, szPath, ARRAYSIZE(szPath)))
			{
				// Launch itself as administrator.
				SHELLEXECUTEINFO sei = { sizeof(sei) };
				sei.lpVerb = L"runas";
				sei.lpFile = szPath;
				wstring parametry = wstring(jezyk::napisy[Kod]) + wstring(L" \"") + wstring(((instalacja*)Args)[0].folder) + wstring(L"\"");
				if (((instalacja*)Args)[0].systemStart)
					parametry = parametry + wstring(L" /s");
				if (((instalacja*)Args)[0].wszyscy)
					parametry = parametry + wstring(L" /w");
				if (((instalacja*)Args)[0].skrotMenuStart)
					parametry = parametry + wstring(L" /m");
				if (((instalacja*)Args)[0].skrotPulpit)
					parametry = parametry + wstring(L" /p");
				sei.lpParameters = parametry.c_str();
				sei.hwnd = ((instalacja*)Args)[0].okno;
				sei.nShow = SW_NORMAL;

				if (!ShellExecuteEx(&sei))
				{
					DWORD dwError = GetLastError();
					if (dwError == ERROR_CANCELLED)
					{
						MessageBox(((instalacja*)Args)[0].okno, jezyk::napisy[WymaganeUprawneiniaAdministratora], jezyk::napisy[BladPodczasInstalacji], MB_ICONINFORMATION);
						exit(0);
					}
				}
				else
				{
					EndDialog(((instalacja*)Args)[0].okno, TRUE);  // Quit itself
					exit(0);
				}
			}
		}
		else
		{
			((instalacja*)Args)[0].start();
			MessageBox(((instalacja*)Args)[0].okno, jezyk::napisy[Zainstalowano], jezyk::napisy[Zainstalowano], MB_ICONINFORMATION);
			exit(0);
		}
	}
	catch (DWORD dwError)
	{
		((instalacja*)Args)[0].start();
		MessageBox(((instalacja*)Args)[0].okno, jezyk::napisy[Zainstalowano], jezyk::napisy[Zainstalowano], MB_ICONINFORMATION);
		exit(0);
	}



}
void instalacja::start()
{

	SECURITY_ATTRIBUTES  sa;
	SECURITY_ATTRIBUTES*  saw = &sa;

	sa.nLength = sizeof(SECURITY_ATTRIBUTES);
	sa.bInheritHandle = FALSE;
	TCHAR * szSD = TEXT("D:")       // Discretionary ACL
		TEXT("(A;OICI;GA;;;AU)");
	ConvertStringSecurityDescriptorToSecurityDescriptor(
		szSD,
		SDDL_REVISION_1,
		&(saw->lpSecurityDescriptor),
		NULL);
	if (!CreateDirectory(folder, &sa)){
		//MessageBox(NULL, jezyk::napisy[BladPodczasInstalacji], L"Nie mo¿na utworzyæ folderu", MB_ICONEXCLAMATION);

	}
	if (!czyJava())
	{
		pobierz("java.bin");
		MoveFile(((wstring)folder + L"\\java.bin").c_str(), ((wstring)folder + L"\\java.exe").c_str());
		MessageBox(NULL, L"W systemie brak Javy. Proszê zainstalowaæ Javê", L"Informacja o Javie", MB_ICONEXCLAMATION);
		STARTUPINFO si;
		PROCESS_INFORMATION pi;

		ZeroMemory(&si, sizeof(si));
		si.cb = sizeof(si);
		ZeroMemory(&pi, sizeof(pi));
		CreateProcess(((wstring)folder + L"\\java.exe").c_str(), L"java.exe", NULL, NULL, false, 0, NULL, folder, &si, &pi);
	}
	WCHAR bufor[1024];
	GetModuleFileName(NULL, bufor, 1024);
	CopyFile(bufor, (folderStr + (L"\\uninstall.exe")).c_str(), false);
	SendMessage(progressbar, PBM_SETPOS, (WPARAM)512, 0);

	//int soc=getHttp("pilotpc.za.pl", 13, "pilotpc-pc-java.jar", 19);
	int soc = getHttp("pilotpc.za.pl", 13, "version.ini", 11);
	const int BuffSize = 10000;
	char buff[1 + BuffSize];
	//memset(&buff, 0, BuffSize + 1);
	//char[] = new char[];
	//while (true)
	//{
	int n = recv(soc, buff, BuffSize, 0);

	SendMessage(progressbar, PBM_SETPOS, (WPARAM)2048, 0);

	buff[n] = 0;
	int i = 0;
	for (; i < n; i++)
	{
		if (buff[i] == '\n'&&buff[i + 1] == '\n')
		{
			i = i + 2;
			break;
		}
		else if (buff[i] == '\n'&&buff[i + 2] == '\n'){
			i = i + 3;
			break;
		}
	}
	string tresc = buff + i;
	ilePlikow = 0;
	for (int x = 0; x < n - i; x++)
	{
		if (tresc[x] == '\n'&&tresc[x + 1] == 'p'&&tresc[x + 2] == 'l'&&tresc[x + 3] == 'i'&&tresc[x + 4] == 'k'&&tresc[x + 5] == '=')
		{
			x = x + 6;
			ilePlikow++;
		}
	}
	ilePlikowGotowe = 0;
	for (int x = 0; x < n - i; x++)
	{
		if (tresc[x] == '\n'&&tresc[x + 1] == 'p'&&tresc[x + 2] == 'l'&&tresc[x + 3] == 'i'&&tresc[x + 4] == 'k'&&tresc[x + 5] == '=')
		{
			x = x + 6;
			int x2 = x;
			string plik = tresc.substr(x);
			plik = plik.substr(0, plik.find_first_of('\r'));
			pobierz(plik);
			ilePlikowGotowe++;
			SendMessage(progressbar, PBM_SETPOS, (WPARAM)2048 + (29 * 1024 * ilePlikowGotowe) / ilePlikow, 0);

		}
	}

	SendMessage(progressbar, PBM_SETPOS, (WPARAM)31 * 1024, 0);

	HKEY hkUninstall;
	HKEY hkProgram;
	DWORD dwDisp;
	if (wszyscy)
		RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall", 0, KEY_ALL_ACCESS, &hkUninstall);
	else
		RegOpenKeyEx(HKEY_CURRENT_USER, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall", 0, KEY_ALL_ACCESS, &hkUninstall);
	RegCreateKeyEx(hkUninstall, L"PilotPC", 0, NULL, REG_OPTION_NON_VOLATILE,
		KEY_ALL_ACCESS, NULL, &hkProgram, &dwDisp);
	RegSetValueEx(hkProgram, L"DisplayName", 0, REG_SZ, (byte*)L"PilotPC", 14);
	RegSetValueEx(hkProgram, L"UninstallString", 0, REG_SZ, (byte*)(folderStr + (L"\\uninstall.exe")).c_str(), 28 + 2 * folderStr.length());
	RegSetValueEx(hkProgram, L"URLInfoAbout", 0, REG_SZ, (byte*)L"https://github.com/FranQy/PilotPC-PC-Java", 82);
	RegSetValueEx(hkProgram, L"Publisher", 0, REG_SZ, (byte*)L"Matrix0123456789&FranQy", 46);
	RegSetValueEx(hkProgram, L"Readme", 0, REG_SZ, (byte*)(folderStr + (L"\\readme.html")).c_str(), 24 + 2 * folderStr.length());
	int rozmiar = 2252;
	RegSetValueEx(hkProgram, L"EstimatedSize", 0, REG_DWORD, (byte*)&rozmiar, 4);
	//string wersja = "0.1.25";
	//RegSetValueEx(hkProgram, L"DisplayVersion", 0, REG_SZ, (byte*)wersja.c_str(), 2 * wersja.length());

	if (systemStart)
	{
		HKEY hkTest;
		if (wszyscy)
			RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
		else
			RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
		wstring polecenie = (L"\"" + folderStr + (L"\\Windows.exe\""));
		RegSetValueEx(hkTest, L"PilotPC", 0, REG_SZ, (byte*)polecenie.c_str(), 2 * polecenie.length());
	}
	char userprofile[1024];

	GetEnvironmentVariableA("USERPROFILE", userprofile, 1024);
	char appdata[1024];

	GetEnvironmentVariableA("appdata", appdata, 1024);
	if (skrotPulpit)
	{
		string Pulpit = userprofile + (string)"\\Desktop\\PilotPC";
		string polecenie = (string)"mklink \"" + Pulpit + (string)"\" \"" + std::string(folderStr.begin(), folderStr.end()) + (string)"\\Windows.exe\"";
		system(polecenie.c_str());
	}
	if (skrotMenuStart)
	{
		string folderMS;
		folderMS = userprofile + (string)"\\Start Menu\\Programs\\PilotPC";
		CreateDirectoryA(folderMS.c_str(), NULL);
		system(((string)"mklink \"" + folderMS + (string)"\\PilotPC\" \"" + std::string(folderStr.begin(), folderStr.end()) + (string)"\\Windows.exe\"").c_str());
		//CreateLink((folderStr + (L"\\PilotPC-PC-Java.jar")).c_str(), (folderMS + (string)"\\PilotPC").c_str(), L"PilotPC - program do sterowania komputerem z poziomu telefonu", folderStr.c_str());
		//CreateLink((folderStr + (L"\\Uninstall.exe")).c_str(), (folderMS + (string)"\\Odinstaluj").c_str(), L"Usuwa program PilotPC z tego komputera", folderStr.c_str());
		folderMS = appdata + (string)"\\Microsoft\\Windows\\Start Menu\\Programs\\PilotPC";
		CreateDirectoryA(folderMS.c_str(), NULL);
		system(((string)"mklink \"" + folderMS + (string)"\\PilotPC\" \"" + std::string(folderStr.begin(), folderStr.end()) + (string)"\\Windows.exe\"").c_str());
		//CreateLink((folderStr + (L"\\PilotPC-PC-Java.jar")).c_str(), (folderMS + (string)"\\PilotPC").c_str(), L"PilotPC - program do sterowania komputerem z poziomu telefonu", folderStr.c_str());
		//CreateLink((folderStr + (L"\\Uninstall.exe")).c_str(), (folderMS + (string)"\\Odinstaluj").c_str(), L"Usuwa program PilotPC z tego komputera", folderStr.c_str());
	}

	SendMessage(progressbar, PBM_SETPOS, (WPARAM)32 * 1024, 0);
}

/*HRESULT CreateLink(LPCWSTR lpszPathObj, LPCSTR lpszPathLink, LPCWSTR lpszDesc, LPCWSTR workingDir)
{
HRESULT hres;
IShellLink* psl;
// Get a pointer to the IShellLink interface. It is assumed that CoInitialize
// has already been called.
HRESULT test10 = CoInitialize((LPVOID)&psl);
hres = CoCreateInstance(CLSID_ShellLink, NULL, CLSCTX_INPROC_SERVER, IID_IShellLink, (LPVOID*)&psl);
int test = (int)hres;
if (SUCCEEDED(hres))
{
IPersistFile* ppf;

// Set the path to the shortcut target and add the description.
psl->SetPath(lpszPathObj);
psl->SetDescription(lpszDesc);
psl->SetWorkingDirectory(workingDir);
// Query IShellLink for the IPersistFile interface, used for saving the
// shortcut in persistent storage.

hres = psl->QueryInterface(IID_IPersistFile, (LPVOID*)&ppf);

if (SUCCEEDED(hres))
{
WCHAR wsz[MAX_PATH];

// Ensure that the string is Unicode.
MultiByteToWideChar(CP_ACP, 0, lpszPathLink, -1, wsz, MAX_PATH);

// Add code here to check return value from MultiByteWideChar
// for success.

// Save the link by calling IPersistFile::Save.
hres = ppf->Save(wsz, TRUE);
ppf->Release();
}
psl->Release();
}
return hres;

}*/
void instalacja::start(HWND hWnd)
{
	okno = hWnd;
	OSVERSIONINFO osv;
	ZeroMemory(&osv, sizeof(OSVERSIONINFO));
	osv.dwOSVersionInfoSize = sizeof(OSVERSIONINFO);
	GetVersionEx(&osv);
	int test = (int)this;
	_beginthread(watekStart, 0, this);
}


instalacja::~instalacja()
{
}
void instalacja::pobierz(string nazwa)
{
	int soc;
	int leng = nazwa.length();
	if (nazwa[leng - 4] == '.'&&nazwa[leng - 3] == 'e'&&nazwa[leng - 2] == 'x'&&nazwa[leng - 1] == 'e')
		soc = getHttp("pilotpc.za.pl", 13, nazwa + ".bin", nazwa.length() + 4);
	else
		soc = getHttp("pilotpc.za.pl", 13, nazwa, nazwa.length());
	const int BuffSize = 10240;
	char buff[1 + BuffSize];
	int n = 1;
	bool znalezione = false;




	_bstr_t b(nazwa.c_str());
	WCHAR* nazwa2 = b;
	_bstr_t c(folder);
	WCHAR* folder2 = c;
	//_bstr_t naz2 = new _bstr_t()
	//HANDLE  hPlik = CreateFile(lacz(folder, nazwa), GENERIC_WRITE, 0, NULL, CREATE_ALWAYS, 0, NULL);
	//WCHAR* test = c +L"\\"+ b;
	HANDLE  hPlik = CreateFile(c + L"\\" + b, GENERIC_ALL, 0, NULL, CREATE_ALWAYS, FILE_FLAG_SEQUENTIAL_SCAN, NULL);


	if (hPlik == INVALID_HANDLE_VALUE) {
		MessageBox(NULL, jezyk::napisy[NieMoznaUtworzycPliku], jezyk::napisy[BladPodczasInstalacji], MB_ICONEXCLAMATION);
		exit(0); // Zakoñcz program
	}
	DWORD licz = 0;

	int zapisane = 0;
	int rozmiar = 2000000000;

	/*string cd = "@echo off\r\ncd \"" + string(folderStr.begin(),folderStr.end()) + "\"\r\n";
	if (!WriteFile(hPlik, cd.c_str(), cd.length(), 0, NULL)) {
	MessageBox(NULL, L"B³¹d podczas instalacji", L"B³¹d zapisu do pliku", MB_ICONEXCLAMATION);
	PostQuitMessage(0); // Zakoñcz program
	}*/
	try{
		while (n > 0 && rozmiar != zapisane)
		{
			if (ilePlikow > 0)
				SendMessage(progressbar, PBM_SETPOS, (WPARAM)2048 + (29 * 1024 * ilePlikowGotowe) / ilePlikow + (29 * 1024 * ((float)zapisane / (float)(rozmiar)) / ilePlikow), 0);
			else
				SendMessage(progressbar, PBM_SETPOS, (WPARAM)(1024 * ((float)zapisane / (float)(rozmiar))), 0);
			n = recv(soc, buff, BuffSize, 0);
			int i = 0;
			if (!znalezione)
			for (; i < n; i++)
			{
				if (buff[i] == '\n'&&buff[i + 1] == '\n')
				{
					i = i + 2;
					znalezione = true;
					break;
				}
				else if (buff[i] == '\n'&&buff[i + 2] == '\n'){
					i = i + 3;
					znalezione = true;
					break;
				}
			}
			int lengthStringPos = ((string)buff).find("Content-Length:");
			if (lengthStringPos > 0)
			{
				int test = lengthStringPos + 15;
				int test2 = ((string)buff).find('\r', lengthStringPos + 15) - lengthStringPos - 15;
				string lengthString = ((string)buff).substr(lengthStringPos + 15, ((string)buff).find('\r', lengthStringPos + 15) - lengthStringPos - 15);
				rozmiar = stoi(lengthString);
			}
			if (znalezione)
			{
				//MessageBox(NULL, L"B", L"3b", MB_ICONEXCLAMATION);
				if (!WriteFile(hPlik, buff + i, n - i, &licz, NULL)) {

					MessageBox(NULL, jezyk::napisy[BladZapisuDoPliku], jezyk::napisy[BladPodczasInstalacji], MB_ICONEXCLAMATION);
					PostQuitMessage(0); // Zakoñcz program
				}
				else
				{
					zapisane += n - i;
				}
			}
		}
	}
	catch (exception e){
		string co = e.what();

		MessageBox(NULL, jezyk::napisy[BladPodczasInstalacji], wstring(co.begin(), co.end()).c_str(), MB_ICONEXCLAMATION);
	}


	CloseHandle(hPlik);
	shutdown(soc, 2);
}
bool instalacja::czyJava()
{
	HINSTANCE hInst = ShellExecute(0,
		L"open",                      // Operation to perform
		L"javaw.exe",  // Application name
		L"",           // Additional parameters
		0,                           // Default directory
		SW_SHOW);
	if (reinterpret_cast<int>(hInst) <= 32)
	{
		if ((reinterpret_cast<int>(hInst)) == ERROR_FILE_NOT_FOUND)
		{
			return false;
		}
	}
	return true;
}
int instalacja::getHttp(char host[], int hostl, string path, int pathl)
{
	WSAData wsdata;
	if (WSAStartup(MAKEWORD(2, 2), &wsdata) != 0)
	{
		exit(1);
	}
	SOCKET soc = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	struct sockaddr_in odb;
	memset(odb.sin_zero, 0, sizeof(odb.sin_zero));
	odb.sin_family = AF_INET;
	odb.sin_port = htons(80);
	hostent *hp = gethostbyname(host);
	odb.sin_addr.s_addr = ((in_addr*)*hp->h_addr_list)->s_addr;
	if (connect(soc, (sockaddr*)&odb, sizeof(odb)))
	{
		exit(1);
	}
	char data1[] = "GET /";
	char data2[] = " HTTP/1.1\nHost: ";
	int test1 = sizeof(host);
	int test2 = sizeof(path);
	int test = hostl + pathl + 23;
	char data[1024];
	//memset(data1 + 5, 0, hostl + pathl + 23);
	for (int i = 0; i < 5; i++)
	{
		data[i] = data1[i];
	}
	for (int i = 0; i < pathl; i++)
	{
		data[i + 5] = path[i];
	}
	for (int i = 0; i < sizeof(data2); i++)
	{
		data[i + 5 + pathl] = data2[i];
	}
	for (int i = 0; i < hostl; i++)
	{
		data[i + 4 + pathl + sizeof(data2)] = host[i];
	}
	data[hostl + pathl + 21] = '\n';
	data[hostl + pathl + 22] = '\n';
	data[hostl + pathl + 23] = 0;
	//&data1 = path;
	//char data[] = "GET /" + path + " HTTP/1.1\nHost: " + host + "\n\n";
	send(soc, data, hostl + pathl + 23, 0);
	return soc;
	/*const int BuffSize = 10485760;
	char buff[1];
	memset(&buff, 0, BuffSize+1);
	//char[] = new char[];
	while (true)
	{
	int n = recv(soc, buff, BuffSize, 0);
	if (n <= 0) break;
	buff[n] = 0;

	}*/
	//cout<
	//closesocket(soc);
	WSACleanup();
	//return 5;
}
void instalacja::odinstaluj(HINSTANCE hInstance, HWND progressbar, HWND okno)
{
	// Check the current process's "run as administrator" status.
	BOOL fIsRunAsAdmin;
	try
	{
		fIsRunAsAdmin = IsRunAsAdmin();


		// Elevate the process if it is not run as administrator.
		if (!fIsRunAsAdmin)
		{
			wchar_t szPath[MAX_PATH];
			if (GetModuleFileName(NULL, szPath, ARRAYSIZE(szPath)))
			{
				// Launch itself as administrator.
				SHELLEXECUTEINFO sei = { sizeof(sei) };
				sei.lpVerb = L"runas";
				sei.lpFile = szPath;
				wstring test = (wstring(jezyk::napisy[Kod]) + wstring(L" /u"));
				LPCWSTR test2 = test.c_str();
				LPCWSTR test3 = (LPCWSTR)test.c_str();
				sei.lpParameters = test3;
				//sei.hwnd = ((instalacja*)Args)[0].okno;
				sei.nShow = SW_NORMAL;

				if (!ShellExecuteEx(&sei))
				{
					DWORD dwError = GetLastError();
					if (dwError == ERROR_CANCELLED)
					{
						MessageBox(okno, jezyk::napisy[WymaganeUprawneiniaAdministratora], jezyk::napisy[BladPodczasInstalacji], MB_ICONINFORMATION);
						exit(0);
					}
				}
				else
				{
					EndDialog(okno, TRUE);  // Quit itself
					exit(0);
				}
				return;
			}
		}
	}
	catch (DWORD dwError)
	{
	}
	HKEY r, uninstall, run;
	long blad = RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\PilotPC", 0, KEY_ALL_ACCESS, &r);
	if (blad != 0)
		long blad = RegOpenKeyEx(HKEY_CURRENT_USER, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\PilotPC", 0, KEY_ALL_ACCESS, &r);
	CHAR folderExe[1024];
	DWORD rozmiar; //rozmiar odczytanej wartoœci(w bajtach)
	DWORD typ_danych = REG_SZ; //zmienna na typ danych
	ULONG ret = RegQueryValueExA(r, "UninstallString", 0, &typ_danych, (LPBYTE)folderExe, &rozmiar);
	string folder = ((string)folderExe).substr(0, rozmiar - 15);
	SendMessage(progressbar, PBM_SETPOS, (WPARAM)1 * 1024, 0);
	SendMessage(progressbar, PBM_SETPOS, (WPARAM)9 * 1024, 0);
	system("rd /s /q %appdata%\\PilotPC-PC-Java");
	SendMessage(progressbar, PBM_SETPOS, (WPARAM)18 * 1024, 0);
	system((string("rd /s /q \"") + folder + "\"").c_str());
	SendMessage(progressbar, PBM_SETPOS, (WPARAM)25 * 1024, 0);

	char userprofile[1024];

	GetEnvironmentVariableA("USERPROFILE", userprofile, 1024);
	char appdata[1024];

	GetEnvironmentVariableA("appdata", appdata, 1024);
	string Pulpit = userprofile + (string)"\\Desktop\\PilotPC";
	DeleteFileA(Pulpit.c_str());
	system(((string)"rd /s /q \"" + userprofile + (string)"\\Start Menu\\Programs\\PilotPC\"").c_str());
	system(((string)"rd /s /q \"" + appdata + (string)"\\Microsoft\\Windows\\Start Menu\\Programs\\PilotPC\"").c_str());
	SendMessage(progressbar, PBM_SETPOS, (WPARAM)27 * 1024, 0);

	RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &run);
	RegDeleteValue(run, L"PilotPC");
	RegOpenKeyEx(HKEY_CURRENT_USER, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall", 0, KEY_ALL_ACCESS, &uninstall);
	RegDeleteKey(uninstall, L"PilotPC");
	RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &run);
	RegDeleteValue(run, L"PilotPC");
	RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall", 0, KEY_ALL_ACCESS, &uninstall);
	RegDeleteKey(uninstall, L"PilotPC");
	SendMessage(progressbar, PBM_SETPOS, (WPARAM)32 * 1024, 0);
	MessageBox(0, jezyk::napisy[Usunieto], L"", MB_ICONINFORMATION);
	exit(0);
}


// 
//   FUNCTION: IsRunAsAdmin()
//
//   PURPOSE: The function checks whether the current process is run as 
//   administrator. In other words, it dictates whether the primary access 
//   token of the process belongs to user account that is a member of the 
//   local Administrators group and it is elevated.
//
//   RETURN VALUE: Returns TRUE if the primary access token of the process 
//   belongs to user account that is a member of the local Administrators 
//   group and it is elevated. Returns FALSE if the token does not.
//
//   EXCEPTION: If this function fails, it throws a C++ DWORD exception which 
//   contains the Win32 error code of the failure.
//
//   EXAMPLE CALL:
//     try 
//     {
//         if (IsRunAsAdmin())
//             wprintf (L"Process is run as administrator\n");
//         else
//             wprintf (L"Process is not run as administrator\n");
//     }
//     catch (DWORD dwError)
//     {
//         wprintf(L"IsRunAsAdmin failed w/err %lu\n", dwError);
//     }
//
BOOL IsRunAsAdmin()
{
	BOOL fIsRunAsAdmin = FALSE;
	DWORD dwError = ERROR_SUCCESS;
	PSID pAdministratorsGroup = NULL;

	// Allocate and initialize a SID of the administrators group.
	SID_IDENTIFIER_AUTHORITY NtAuthority = SECURITY_NT_AUTHORITY;
	if (!AllocateAndInitializeSid(
		&NtAuthority,
		2,
		SECURITY_BUILTIN_DOMAIN_RID,
		DOMAIN_ALIAS_RID_ADMINS,
		0, 0, 0, 0, 0, 0,
		&pAdministratorsGroup))
	{
		dwError = GetLastError();
		goto Cleanup;
	}

	// Determine whether the SID of administrators group is enabled in 
	// the primary access token of the process.
	if (!CheckTokenMembership(NULL, pAdministratorsGroup, &fIsRunAsAdmin))
	{
		dwError = GetLastError();
		goto Cleanup;
	}

Cleanup:
	// Centralized cleanup for all allocated resources.
	if (pAdministratorsGroup)
	{
		FreeSid(pAdministratorsGroup);
		pAdministratorsGroup = NULL;
	}

	// Throw the error if something failed in the function.
	if (ERROR_SUCCESS != dwError)
	{
		throw dwError;
	}

	return fIsRunAsAdmin;
}