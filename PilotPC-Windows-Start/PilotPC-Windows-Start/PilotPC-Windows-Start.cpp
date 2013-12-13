// PilotPC-Windows-Start.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <Windows.h>

int _tmain(int argc, _TCHAR* argv[])
{
	HINSTANCE hInst = ShellExecute(0,
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
			return false;
		}
	}
	//return 0;
}

