// PilotPC-Windows-Start.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <Windows.h>
#include <io.h>
#include <string>
using namespace std;
#pragma comment (lib,"Advapi32.lib")
#pragma comment (lib,"User32.lib")
#pragma comment (lib,"Kernel32.lib")
void plikiNew(wstring fold);
void plikiNew(wstring fold){
	WIN32_FIND_DATA  FindFileData;

	HANDLE pliczekH = FindFirstFile((fold + wstring(L"*.new")).c_str(), &FindFileData);
	if (pliczekH != INVALID_HANDLE_VALUE)
	{
		while (true){
		wstring pliczek = wstring(FindFileData.cFileName);


		MoveFileEx((fold+pliczek).c_str(), ((fold+pliczek.substr(0, pliczek.length() - 4)).c_str()), MOVEFILE_REPLACE_EXISTING);
		if (!FindNextFile(pliczekH, &FindFileData))
			break;
	}
	}
	FindClose(pliczekH);

	pliczekH = FindFirstFile((fold + wstring(L"*")).c_str(), &FindFileData);
	while (pliczekH != INVALID_HANDLE_VALUE)
	{
		wstring pliczek = wstring(FindFileData.cFileName);
		if (FindFileData.dwFileAttributes == FILE_ATTRIBUTE_DIRECTORY&&pliczek != wstring(L".") && pliczek != wstring(L"..") && pliczek != wstring(L""))
			plikiNew(fold + pliczek + wstring(L"\\"));
		if (!FindNextFile(pliczekH, &FindFileData))
			break;
	}
	FindClose(pliczekH);

	
}
int run(_TCHAR* argv[], LPCWSTR polec, bool konsola)
{
	HINSTANCE hInst;
	//	if (!access("PilotPC-PC-Java.jar.new", 0))
	WCHAR *pol;
	if (konsola)
		pol = L"java.exe";
	else

		pol = L"javaw.exe";
	if (konsola){

		wstring komenda = wstring(pol) + wstring(L" ") + wstring(polec);
		system(string(komenda.begin(), komenda.end()).c_str());
	}
	else
	{

		if (argv[0][1] == L':')
		{
			hInst = ShellExecute(0,
				L"open",                      // Operation to perform
				pol,  // Application name
				polec,           // Additional parameters
				wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\')).c_str(),                           // Default directory
				SW_SHOW);
			wprintf(wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\')).c_str());
		}
		else
			hInst = ShellExecute(0,
			L"open",                      // Operation to perform
			pol,  // Application name
			polec,           // Additional parameters
			0,                           // Default directory
			SW_SHOW);
	}
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
int _tmain(const int argc, _TCHAR* argv[])
{
	if (argv[0][1] == L':')
		plikiNew(wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\'))+wstring(L"\\"));
	else
		plikiNew(wstring(L""));
	if (argc == 1)
	{
		LPCWSTR polec = L"-jar java/pilotpc.jar -o";

		for (short i = 1; i < argc;i++)
		if ((argv[i][0] == '/' || argv[i][0] == '-') && argv[i][1] == 'n'&&argv[i][2] == 'o')
			polec = L"-jar java/pilotpc.jar";
			
		return run(argv,polec, false);
		
		
	}/*
	else if (wstring(argv[1]) == wstring(L"/?"))
	{
		wprintf(L"PilotPC\r\n\r\n/a	wlacza autostart przy starcie systemu");
	}*/
	else if (wstring(argv[1]) == wstring(L"/a"))
	{

		wstring folder;
		if (argv[0][1] == L':')
			folder = wstring(L"\"") + wstring(argv[0]) + wstring(L" /no\"");
		else
		{
			WCHAR curDir[1024];
			GetCurrentDirectory(2048, curDir);
			folder = wstring(L"\"") + curDir + wstring(L"\\") + wstring(argv[0]) + wstring(L" /no\"");

		}
		wprintf(folder.c_str());
		HKEY hkTest;
		RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
		RegSetValueEx(hkTest, L"PilotPC", 0, REG_SZ, (byte*)folder.c_str(), 2 * folder.length());
		return 0;
	}
	if (argc > 1)
	{
		wstring polec = wstring(L"-jar java/pilotpc.jar -o");
		bool konsola = false;
		for (short i = 1; i < argc; i++)
		if ((argv[i][0] == '/' || argv[i][0] == '-') && argv[i][1] == 'n'&&argv[i][2] == 'o')
			polec = wstring(L"-jar java/pilotpc.jar");
		else
			konsola = true;

		for (short i = 1; i < argc; i++)
		
			polec += wstring(L" ")+wstring(argv[i]);

		run(argv, polec.c_str(), konsola);

	}
	//return 0;
}

