// PilotPC-Windows.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include "start.h"
#include <string>
using namespace std;
#pragma comment (lib,"Advapi32.lib")
#pragma comment (lib,"Shell32.lib")
enum  pilotButton {
	OFF, MUSIC, PLAYPAUSE, PERV, NEXT, STOP, EXIT, BACK, VOLDOWN, VOLUP, MUTE,
	UP, DOWN, RIGHT, LEFT, RETTURN,
	REWIND,//Przewijanie do ty³u
	FORWARD, PHOTO, VIDEO
};
JNIEXPORT void JNICALL Java_Biblioteka_click(JNIEnv *env, jclass jobj, jint a) {
	pilotButton przycisk = (pilotButton)a;
	switch (przycisk)
	{
	case VOLUP:
		keybd_event(VK_VOLUME_UP, 0, 1, 0);
		keybd_event(VK_VOLUME_UP, 0, 2, 0);
		break;
	case VOLDOWN:
		keybd_event(VK_VOLUME_DOWN, 0, 1, 0);
		keybd_event(VK_VOLUME_DOWN, 0, 2, 0);
		break;
	case MUTE:
		keybd_event(VK_VOLUME_MUTE, 0, 1, 0);
		keybd_event(VK_VOLUME_MUTE, 0, 2, 0);
		break;
	case PLAYPAUSE:
		keybd_event(VK_MEDIA_PLAY_PAUSE, 0, 1, 0);
		keybd_event(VK_MEDIA_PLAY_PAUSE, 0, 2, 0);
		break;
	case NEXT:
		keybd_event(VK_MEDIA_NEXT_TRACK, 0, 1, 0);
		keybd_event(VK_MEDIA_NEXT_TRACK, 0, 2, 0);
		break;
	case PERV:
		keybd_event(VK_MEDIA_PREV_TRACK, 0, 1, 0);
		keybd_event(VK_MEDIA_PREV_TRACK, 0, 2, 0);
		break;
	case STOP:
		keybd_event(VK_MEDIA_STOP, 0, 1, 0);
		keybd_event(VK_MEDIA_STOP, 0, 2, 0);
		break;
	case MUSIC:
		keybd_event(VK_LAUNCH_MEDIA_SELECT, 0, 1, 0);
		keybd_event(VK_LAUNCH_MEDIA_SELECT, 0, 2, 0);
		break;

	}
}


JNIEXPORT void JNICALL Java_Biblioteka_autostart
(JNIEnv *env, jclass jobj, jboolean wlacz, jboolean wszyscy, jstring f) {

	jboolean blnIsCopy;
	jstring jstrOutput;
	char* strCOut;
	const char* strCIn = (env)->GetStringUTFChars(f, &blnIsCopy);

	string folder = string("\"") + string(strCIn) + string("\\Windows.exe\" /no");
	HKEY hkTest;
	RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
	if (wlacz)
		RegSetValueExA(hkTest, "PilotPC", 0, REG_SZ, (BYTE*)(folder.c_str()), folder.length());
	else
		RegDeleteValue(hkTest, L"PilotPC");
}
JNIEXPORT void JNICALL Java_Biblioteka_runAsRoot
(JNIEnv *env, jclass, jstring adres, jstring parametry){
	{
		// Launch itself as administrator.
		SHELLEXECUTEINFOA sei = { sizeof(sei) };
		sei.lpVerb = "runas";
		jboolean blnIsCopy;
		sei.lpFile = (env)->GetStringUTFChars(adres, &blnIsCopy);
		sei.lpParameters = (env)->GetStringUTFChars(parametry, &blnIsCopy);
		//sei.hwnd = ((instalacja*)Args)[0].okno;
		sei.nShow = SW_NORMAL;

		if (!ShellExecuteExA(&sei))
		{
			//return true;
		}
		else
		{
			//return false;
		}
	}
}JNIEXPORT jboolean JNICALL Java_Biblioteka_CzyAutostart
(JNIEnv *env, jclass){
	{

		HKEY hkTest;
		RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
		char buf[21];
		DWORD dwBufSize = 20;
		DWORD dwRegsz = REG_SZ;
		LSTATUS result = RegQueryValueEx(hkTest, L"PilotPC", NULL, &dwRegsz, (LPBYTE)buf, &dwBufSize);
		if (result == ERROR_SUCCESS)
			return true;
		RegOpenKeyEx(HKEY_LOCAL_MACHINE, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
		LSTATUS result = RegQueryValueEx(hkTest, L"PilotPC", NULL, &dwRegsz, (LPBYTE)buf, &dwBufSize);
		if (result == ERROR_SUCCESS)
			return true;
		return false;
	}
}