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
instalacja::instalacja(bool _systemStart, bool _wszyscy, LPWSTR _folder)
{
	systemStart = _systemStart;
	wszyscy = _wszyscy;
	folder = _folder;
	folderStr = wstring(folder);
}
void instalacja::start()
{
	if (!czyJava())
	{

	}
	CreateDirectory(folder, NULL);
	//int soc=getHttp("pilotpc.za.pl", 13, "pilotpc-pc-java.jar", 19);
	int soc = getHttp("pilotpc.za.pl", 13, "version.ini", 11);
	const int BuffSize = 10000;
	char buff[1 + BuffSize];
	//memset(&buff, 0, BuffSize + 1);
	//char[] = new char[];
	//while (true)
	//{
	int n = recv(soc, buff, BuffSize, 0);

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
	for (int x = 0; x < n - i; x++)
	{
		if (tresc[x] == '\n'&&tresc[x + 1] == 'p'&&tresc[x + 2] == 'l'&&tresc[x + 3] == 'i'&&tresc[x + 4] == 'k'&&tresc[x + 5] == '=')
		{
			x = x + 6;
			int x2 = x;
			string plik = tresc.substr(x);
			plik = plik.substr(0, plik.find_first_of('\r'));
			pobierz(plik);
		}
	}
	WCHAR bufor[1024];
	GetModuleFileName(NULL, bufor,1024);
	CopyFile(bufor, (folderStr + (L"\\uninstall.exe")).c_str(), false);
	HKEY hkUninstall;
	HKEY hkProgram;
	DWORD dwDisp;
	RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall", 0, KEY_ALL_ACCESS, &hkUninstall);
	RegCreateKeyEx(hkUninstall, L"PilotPC", 0, NULL, REG_OPTION_NON_VOLATILE,
		KEY_ALL_ACCESS, NULL, &hkProgram, &dwDisp);
	RegSetValueEx(hkProgram, L"DisplayName", 0, REG_SZ, (byte*)L"PilotPC", 14);
	RegSetValueEx(hkProgram, L"UninstallString", 0, REG_SZ, (byte*)(folderStr + (L"\\uninstall.exe")).c_str(), 28 + 2 * folderStr.length());
	RegSetValueEx(hkProgram, L"URLInfoAbout", 0, REG_SZ, (byte*)L"https://github.com/FranQy/PilotPC-PC-Java", 82);
	RegSetValueEx(hkProgram, L"Publisher", 0, REG_SZ, (byte*)L"Matrix0123456789&FranQy", 46);
	RegSetValueEx(hkProgram, L"Readme", 0, REG_SZ, (byte*)(folderStr + (L"\\readme.html")).c_str(), 24 + 2 * folderStr.length());
	int rozmiar = 1740;//sprawdziæ, czy dzia³a
	RegSetValueEx(hkProgram, L"EstimatedSize", 0, REG_DWORD, (byte*)&rozmiar,4);
	//string wersja = "0.1.14";
	//RegSetValueEx(hkProgram, L"DisplayVersion", 0, REG_SZ, (byte*)wersja.c_str(), 2 * wersja.length());

	if (systemStart)
	{
		HKEY hkTest;
		RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
		RegSetValueEx(hkTest, L"PilotPC", 0, REG_SZ, (byte*)((L"java -jar ") + folderStr + (L"\\PilotPC-PC-Java.jar")).c_str(), 38 + 2 * folderStr.length());
	}
}
void instalacja::start(HWND hWnd)
{
	start();
		MessageBox(hWnd, L"Zainstalowano", L"Zainstalowano", MB_ICONINFORMATION);
		PostQuitMessage(0);
}


instalacja::~instalacja()
{
}
void instalacja::pobierz(string nazwa)
{
	int soc = getHttp("pilotpc.za.pl", 13, nazwa,nazwa.length());
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
	HANDLE  hPlik = CreateFile(c + L"\\" + b, GENERIC_WRITE, 0, NULL, CREATE_ALWAYS, 0, NULL);
	
	if (hPlik == INVALID_HANDLE_VALUE) {
		MessageBox(NULL, L"B³¹d podczas instalacji",L"Nie mo¿na utworzyæ pliku.", MB_ICONEXCLAMATION);
		PostQuitMessage(0); // Zakoñcz program
	}
	LPDWORD zapisane = 0;
	while (n>0)
	{
		 n= recv(soc, buff, BuffSize, 0);
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
		 if (znalezione)
		 {
			 if (!WriteFile(hPlik, buff + i, n-i, 0, NULL)) {
				 MessageBox(NULL, L"B³¹d podczas instalacji", L"B³¹d zapisu do pliku", MB_ICONEXCLAMATION);
				 PostQuitMessage(0); // Zakoñcz program
			 }
			 else
				 zapisane += BuffSize - i;
		 }
	}
	
	CloseHandle(hPlik);
	shutdown(soc, 2);
}
bool instalacja::czyJava()
{
	HINSTANCE hInst = ShellExecute(0,
		L"open",                      // Operation to perform
		L"C:\\Windows\\System32\\javaw.exe",  // Application name
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