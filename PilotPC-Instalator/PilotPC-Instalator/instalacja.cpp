
#define _WINSOCK_DEPRECATED_NO_WARNINGS
#include "instalacja.h"
#include <stdio.h>
#include <stdlib.h>
;
#include <shellapi.h>
;
#ifdef WIN32
#include <io.h>
#else
#include <unistd.h>
#endif
;
#include <Winsock2.h>
#include <comdef.h>
#include <Winbase.h>
#include "windows.h"
#include "winnls.h"
#include "shobjidl.h"
#include "objbase.h"
#include "objidl.h"
#include "shlguid.h"
#include <process.h>
#include <Shobjidl.h>
#include <Sddl.h>
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

// CreateLink - Uses the Shell's IShellLink and IPersistFile interfaces 
//              to create and store a shortcut to the specified object. 
//
// Returns the result of calling the member functions of the interfaces. 
//
// Parameters:
// lpszPathObj  - Address of a buffer that contains the path of the object,
//                including the file name.
// lpszPathLink - Address of a buffer that contains the path where the 
//                Shell link is to be stored, including the file name.
// lpszDesc     - Address of a buffer that contains a description of the 
//                Shell link, stored in the Comment field of the link
//                properties.

#include "stdafx.h"
#include "windows.h"
#include "winnls.h"
#include "shobjidl.h"
#include "objbase.h"
#include "objidl.h"
#include "shlguid.h"

HRESULT CreateLink(LPCWSTR lpszPathObj, LPCSTR lpszPathLink, LPCWSTR lpszDesc)
{
	HRESULT hres;
	IShellLink* psl;
	CoInitialize(NULL);
	// Get a pointer to the IShellLink interface. It is assumed that CoInitialize
	// has already been called.
	hres = CoCreateInstance(CLSID_ShellLink, NULL, CLSCTX_INPROC_SERVER, IID_IShellLink, (LPVOID*)&psl);

	if (SUCCEEDED(hres))
	{
		IPersistFile* ppf;

		// Set the path to the shortcut target and add the description. 
		psl->SetPath(lpszPathObj);
		psl->SetDescription(lpszDesc);

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
}
int instalacja::serNr = 0;
char** instalacja::serwery = new char*[] { "jaebestudio.tk", "jaebe.za.pl", "pilotpc.za.pl" };
int* instalacja::serweryl = new int[] { 14, 11, 13 };

instalacja::instalacja(bool _systemStart, bool _wszyscy, LPCWSTR _folder, bool _skrotPulpit, bool _skrotMenuStart, HWND _progressbar, wstring _wfolder, HWND _StanInstalacji)
{

	systemStart = _systemStart;
	wszyscy = _wszyscy;
	folder = _folder;
	wfolder = _wfolder;
	skrotMenuStart = _skrotMenuStart;
	skrotPulpit = _skrotPulpit;
	folderStr = wstring(folder);
	progressbar = _progressbar;
	StanInstalacji = _StanInstalacji;
	//MessageBox(okno, folder,wfolder.c_str(), MB_ICONINFORMATION);


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
				wstring parametry = wstring(jezyk::napisy[Kod]) + wstring(L" \"") + (((instalacja*)Args)[0].wfolder) + wstring(L"\"");
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
			((instalacja*)Args)[0].start(((instalacja*)Args)[0].wfolder);
			MessageBox(((instalacja*)Args)[0].okno, jezyk::napisy[Zainstalowano], jezyk::napisy[Zainstalowano], MB_ICONINFORMATION);
			if (!instalacja::czyJava())
			{
				//pobierz("javaInstalacja.exe", fol, this, false);
				//MoveFile((fol + L"\\java.bin").c_str(), (fol + L"\\javaInstalacja.exe").c_str());
				STARTUPINFO si;
				PROCESS_INFORMATION pi;
				ZeroMemory(&si, sizeof(si));
				si.cb = sizeof(si);
				ZeroMemory(&pi, sizeof(pi));
				//MessageBox(NULL, L"W systemie brak Javy. Proszê zainstalowaæ Javê", L"Informacja o Javie", MB_ICONEXCLAMATION);
				const WCHAR* adr = (instalacja::folderInst + L"\\javaInstalacja.exe").c_str();
				CreateProcess((instalacja::folderInst + L"\\javaInstalacja.exe").c_str(), L"", NULL, NULL, false, 0, NULL, instalacja::folderInst.c_str(), &si, &pi);
			}
			else
			{
				STARTUPINFO si;
				PROCESS_INFORMATION pi;
				ZeroMemory(&si, sizeof(si));
				si.cb = sizeof(si);
				ZeroMemory(&pi, sizeof(pi));
				//MessageBox(NULL, L"W systemie brak Javy. Proszê zainstalowaæ Javê", L"Informacja o Javie", MB_ICONEXCLAMATION);
				const WCHAR* adr = (instalacja::folderInst + L"\\Windows.exe").c_str();
				CreateProcess((instalacja::folderInst + L"\\Windows.exe").c_str(), L"", NULL, NULL, false, 0, NULL, instalacja::folderInst.c_str(), &si, &pi);

			}
			exit(0);
		}
	}
	catch (DWORD dwError)
	{
		((instalacja*)Args)[0].start(((instalacja*)Args)[0].wfolder);
		MessageBox(((instalacja*)Args)[0].okno, jezyk::napisy[Zainstalowano], jezyk::napisy[Zainstalowano], MB_ICONINFORMATION);
		if (!instalacja::czyJava())
		{
			//pobierz("javaInstalacja.exe", fol, this, false);
			//MoveFile((fol + L"\\java.bin").c_str(), (fol + L"\\javaInstalacja.exe").c_str());
			STARTUPINFO si;
			PROCESS_INFORMATION pi;
			ZeroMemory(&si, sizeof(si));
			si.cb = sizeof(si);
			ZeroMemory(&pi, sizeof(pi));
			//MessageBox(NULL, L"W systemie brak Javy. Proszê zainstalowaæ Javê", L"Informacja o Javie", MB_ICONEXCLAMATION);
			const WCHAR* adr = (instalacja::folderInst + L"\\javaInstalacja.exe").c_str();
			CreateProcess((instalacja::folderInst + L"\\javaInstalacja.exe").c_str(), L"", NULL, NULL, false, 0, NULL, instalacja::folderInst.c_str(), &si, &pi);
		}
		exit(0);
	}



}
wstring instalacja::folderInst;
void instalacja::start(wstring fol)
{
	try{
		postepFaktyczny = 16;
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

		for (int i = 3; i < fol.length(); i++)
		{

			if (fol[i] == '\\')
			{
				if (!CreateDirectory(fol.substr(0, i).c_str(), &sa) && _waccess(fol.substr(0, i).c_str(), 0) == ENOENT){
					MessageBox(NULL, (wstring(jezyk::napisy[NieMoznaUtworzycFolderu]) + fol.substr(0, i)).c_str(), jezyk::napisy[BladPodczasInstalacji], MB_ICONERROR);
					exit(1);
				}
			}
		}
		if (!CreateDirectory(fol.c_str(), &sa) && _waccess(fol.c_str(), 0) == ENOENT){
			MessageBox(NULL, (wstring(jezyk::napisy[NieMoznaUtworzycFolderu]) + fol).c_str(), jezyk::napisy[BladPodczasInstalacji], MB_ICONERROR);
			exit(1);
		}
		if (!czyJava())
		{
			pobierz("javaInstalacja.exe", fol, this, false);
			//MoveFile((fol + L"\\java.bin").c_str(), (fol + L"\\javaInstalacja.exe").c_str());
			STARTUPINFO si;
			PROCESS_INFORMATION pi;
			instalacja::folderInst = fol;
			/*	ZeroMemory(&si, sizeof(si));
				si.cb = sizeof(si);
				ZeroMemory(&pi, sizeof(pi));
				//MessageBox(NULL, L"W systemie brak Javy. Proszê zainstalowaæ Javê", L"Informacja o Javie", MB_ICONEXCLAMATION);
				const WCHAR* adr= (fol + L"\\javaInstalacja.exe").c_str();
				CreateProcess((fol + L"\\javaInstalacja.exe").c_str(), L"javaInstalacja.exe", NULL, NULL, false, 0, NULL, folder, &si, &pi);
				*/
		}
		WCHAR bufor[1024];
		GetModuleFileName(NULL, bufor, 1024);
		CopyFile(bufor, (folderStr + (L"\\uninstall.exe")).c_str(), false);
		postepFaktyczny = 512;

		//int soc=getHttp("pilotpc.za.pl", 13, "pilotpc-pc-java.jar", 19);
	restart:
		if (instalacja::serNr > 2)
		{
			MessageBox(0, jezyk::napisy[BladPolaczeniaZSerwerrem], jezyk::napisy[BladPodczasInstalacji], MB_ICONERROR);
			return;
		}
		int soc = getHttp(instalacja::serwery[instalacja::serNr], instalacja::serweryl[instalacja::serNr], "version.php?instal", 18);
		if (soc == -1)
		{
			instalacja::serNr++;
			goto restart;
		}

		const int BuffSize = 20000;
		char buff[1 + BuffSize];
		//memset(&buff, 0, BuffSize + 1);
		//char[] = new char[];
		//while (true)
		//{
		char bufJeden[1];
		int n = -1;
		while (true)
		{
			n = n + recv(soc, bufJeden, 1, 0);
			buff[n] = bufJeden[0];
			if (n > 10)
			{
				if (buff[n] == ']'&&buff[n - 1] == 'E'&&buff[n - 2] == 'L'&&buff[n - 3] == 'I'&&buff[n - 4] == 'F'&&buff[n - 5] == 'D'&&buff[n - 6] == 'N'&&buff[n - 7] == 'E'&&buff[n - 8] == '[')
				{
					n = n - 8;
					break;
				}
			}
			if (n >= BuffSize)
			{
				instalacja::serNr++;
				goto restart;
				return;
			}
		}
		string buforS = string(buff);
		if (buforS.find("HTTP/1.1 200") != 0)
		{
			instalacja::serNr++;
			goto restart;
		}
		postepFaktyczny = 2048;
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
				pobierz(plik, fol, this, true);
				ilePlikowGotowe++;
				postepFaktyczny = 2048 + (29 * 1024 * ilePlikowGotowe) / ilePlikow;

			}
		}
		postepFaktyczny = 31 * 1024;

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
		RegSetValueEx(hkProgram, L"URLInfoAbout", 0, REG_SZ, (byte*)L"http://jaebestudio.tk/pilotpc", 58);
		RegSetValueEx(hkProgram, L"Publisher", 0, REG_SZ, (byte*)L"Jaebe Studio", 24);
		RegSetValueEx(hkProgram, L"Readme", 0, REG_SZ, (byte*)(folderStr + (L"\\readme.html")).c_str(), 24 + 2 * folderStr.length());
		int rozmiar = 1605;
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
			wstring polecenie = (L"\"" + folderStr + (L"\\Windows.exe\" -no"));
			RegSetValueEx(hkTest, L"PilotPC", 0, REG_SZ, (byte*)polecenie.c_str(), 2 * polecenie.length());
		}
		CHAR userprofile[1024];

		GetEnvironmentVariableA("USERPROFILE", userprofile, 1024);
		char appdata[1024];

		const WCHAR* test2 = folderStr.c_str();
		wstring cel = wstring(test2) + wstring(L"\\Windows.exe");
		wstring celUin = wstring(test2) + wstring(L"\\Uninstall.exe");
		GetEnvironmentVariableA("appdata", appdata, 1024);
		if (skrotPulpit)
		{
			string Pulpit = userprofile + string("\\Desktop\\PilotPC.lnk");
			CreateLink(cel.c_str(), Pulpit.c_str(), jezyk::napisy[SterujKomputeremZdalnie]);
			//CreateShortcut(gcnew System::String(Pulpit.c_str()), gcnew System::String(cel.c_str()), gcnew System::String("Steruj swoim komputerem zdalnie"));
		}
		if (skrotMenuStart)
		{
			string folderMS;
			string folderMSlnk;
			string folderMSuin;
			folderMS = userprofile + (string)"\\Start Menu\\Programs\\PilotPC";
			folderMSlnk = userprofile + (string)"\\Start Menu\\Programs\\PilotPC\\PilotPC.lnk";
			folderMSuin = userprofile + (string)"\\Start Menu\\Programs\\PilotPC\\Odinstaluj.lnk";
			CreateDirectoryA(folderMS.c_str(), NULL);

			CreateLink(cel.c_str(), folderMSlnk.c_str(), jezyk::napisy[SterujKomputeremZdalnie]);
			CreateLink(celUin.c_str(), folderMSuin.c_str(), jezyk::napisy[OdinstalujPilotPC]);
			//CreateLink((folderStr + (L"\\PilotPC-PC-Java.jar")).c_str(), (folderMS + (string)"\\PilotPC").c_str(), L"PilotPC - program do sterowania komputerem z poziomu telefonu", folderStr.c_str());
			//CreateLink((folderStr + (L"\\Uninstall.exe")).c_str(), (folderMS + (string)"\\Odinstaluj").c_str(), L"Usuwa program PilotPC z tego komputera", folderStr.c_str());
			folderMS = appdata + (string)"\\Microsoft\\Windows\\Start Menu\\Programs\\PilotPC";
			folderMSlnk = appdata + (string)"\\Microsoft\\Windows\\Start Menu\\Programs\\PilotPC\\PilotPC.lnk";
			folderMSuin = appdata + (string)"\\Microsoft\\Windows\\Start Menu\\Programs\\PilotPC\\Odinstaluj.lnk";
			CreateDirectoryA(folderMS.c_str(), NULL);

			CreateLink(cel.c_str(), folderMSlnk.c_str(), jezyk::napisy[SterujKomputeremZdalnie]);
			CreateLink(celUin.c_str(), folderMSuin.c_str(), jezyk::napisy[OdinstalujPilotPC]);
			//CreateLink((folderStr + (L"\\PilotPC-PC-Java.jar")).c_str(), (folderMS + (string)"\\PilotPC").c_str(), L"PilotPC - program do sterowania komputerem z poziomu telefonu", folderStr.c_str());
			//CreateLink((folderStr + (L"\\Uninstall.exe")).c_str(), (folderMS + (string)"\\Odinstaluj").c_str(), L"Usuwa program PilotPC z tego komputera", folderStr.c_str());

		}
		postepFaktyczny = 32 * 1024;
	}
	catch (exception)
	{
		MessageBox(0, jezyk::napisy[BladPodczasInstalacji], jezyk::napisy[BladPodczasInstalacji], MB_ICONERROR);

	}


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
	/*OSVERSIONINFO osv;
	ZeroMemory(&osv, sizeof(OSVERSIONINFO));
	osv.dwOSVersionInfoSize = sizeof(OSVERSIONINFO);
	GetVersionEx(&osv);*/
	int test = (int)this;
	_beginthread(watekStart, 0, this);
}


instalacja::~instalacja()
{
}
void instalacja::pobierz(string nazwa, wstring fol, instalacja* objekt, bool szybkie)
{
	int soc;

	//SetWindowTextA(StanInstalacji, nazwa.c_str());
	int leng = nazwa.length();
	if (nazwa[leng - 4] == '.'&&nazwa[leng - 3] == 'e'&&nazwa[leng - 2] == 'x'&&nazwa[leng - 1] == 'e')
		soc = getHttp(instalacja::serwery[instalacja::serNr], instalacja::serweryl[instalacja::serNr], nazwa + ".bin", nazwa.length() + 4);
	else
		soc = getHttp(instalacja::serwery[instalacja::serNr], instalacja::serweryl[instalacja::serNr], nazwa, nazwa.length());
	const int BuffSize = 10240;
	char buff[1 + BuffSize];
	int n = 1;
	bool znalezione = false;

	for (int i = 0; i < nazwa.length(); i++)
		if (nazwa[i] == '/')
			nazwa[i] = '\\';

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
	wstring b;
	convert(nazwa, b);
	const WCHAR* nazwa2 = b.c_str();;

	const WCHAR* folder2 = fol.c_str();
	//tworzy podfoldery
	wstring calyplik = (fol + wstring(L"\\") + b);
	for (int i = 3; i < calyplik.length(); i++)
	{

		if (calyplik[i] == '\\')
		{
			if (!CreateDirectory(calyplik.substr(0, i).c_str(), &sa) && _waccess(calyplik.substr(0, i).c_str(), 0) == ENOENT){
				MessageBox(NULL, (wstring(L"Nie mo¿na utworzyæ folderu ") + calyplik.substr(0, i)).c_str(), jezyk::napisy[BladPodczasInstalacji], MB_ICONERROR);
				exit(1);
			}
		}
	}
	//_bstr_t naz2 = new _bstr_t()
	//HANDLE  hPlik = CreateFile(lacz(folder, nazwa), GENERIC_WRITE, 0, NULL, CREATE_ALWAYS, 0, NULL);
	//WCHAR* test = c +L"\\"+ b;
	long flaga = 0;
	if (szybkie)
		flaga = FILE_FLAG_SEQUENTIAL_SCAN;
	HANDLE  hPlik = CreateFile((fol + wstring(L"\\") + b).c_str(), GENERIC_ALL, 0, NULL, CREATE_ALWAYS, flaga, NULL);


	if (hPlik == INVALID_HANDLE_VALUE) {
		MessageBox(NULL, (wstring(jezyk::napisy[NieMoznaUtworzycPliku]) + wstring(L" ") + fol + wstring(L"\\") + b).c_str(), jezyk::napisy[BladPodczasInstalacji], MB_ICONEXCLAMATION);
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
			if ((*objekt).ilePlikow > 0)
				(*objekt).postepFaktyczny = (2048 + (29 * 1024 * (*objekt).ilePlikowGotowe) / (*objekt).ilePlikow + (29 * 1024 * ((float)zapisane / (float)(rozmiar)) / (*objekt).ilePlikow));
			else
				(*objekt).postepFaktyczny = ((1024 * ((float)zapisane / (float)(rozmiar))));
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

	FlushFileBuffers(hPlik);
	CloseHandle(hPlik);
	shutdown(soc, 2);
	//HANDLE  hPlik2 = CreateFile((fol + wstring(L"\\") + b).c_str(), GENERIC_ALL, 0, NULL, NULL, FILE_FLAG_SEQUENTIAL_SCAN, NULL);
	//CloseHandle(hPlik2);

}
bool instalacja::czyJava()
{
	//return false;
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
		return -1;
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
		return -1;
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