// PilotPC-Windows-Start.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <Windows.h>
#include <io.h>
#include <string>
using namespace std;
#pragma comment (lib,"Advapi32.lib")
int _tmain(const int argc, _TCHAR* argv[])
{
	if (argc == 1)
	{

		HINSTANCE hInst;

		
		//	if (!access("PilotPC-PC-Java.jar.new", 0))
		if (argv[0][1] == L':')
		{
			hInst = ShellExecute(0,
				L"open",                      // Operation to perform
				L"javaw.exe",  // Application name
				L"-jar PilotPC-PC-Java.jar",           // Additional parameters
				wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\')).c_str(),                           // Default directory
				SW_SHOW);
			wprintf(wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\')).c_str());
		}
		else
			hInst = ShellExecute(0,
			L"open",                      // Operation to perform
			L"javaw.exe",  // Application name
			L"-jar PilotPC-PC-Java.jar",           // Additional parameters
			0,                           // Default directory
			SW_SHOW);
		if (reinterpret_cast<int>(hInst) <= 32)
		{
			if ((reinterpret_cast<int>(hInst)) == ERROR_FILE_NOT_FOUND)
			{
				//brak javy
				printf("Brak zainstalowanej Javy!");
				return false;
			}
		}
	}
	else if (wstring(argv[1]) == wstring(L"/?"))
	{
		wprintf(L"PilotPC\r\n\r\n/a	wlacza autostart przy starcie systemu");
	}
	else if (wstring(argv[1]) == wstring(L"/a"))
	{

		wstring folder;
		if (argv[0][1] == L':')
			folder = wstring(L"\"") + wstring(argv[0]) + wstring(L"\"");
		else
		{
			WCHAR curDir[1024];
			GetCurrentDirectory(2048, curDir);
			folder = wstring(L"\"") + curDir + wstring(L"\\") + wstring(argv[0]) + wstring(L"\"");

		}
		wprintf(folder.c_str());
		HKEY hkTest;
		RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
		RegSetValueEx(hkTest, L"PilotPC", 0, REG_SZ, (byte*)folder.c_str(), 2 * folder.length());
	}
	//return 0;
}

