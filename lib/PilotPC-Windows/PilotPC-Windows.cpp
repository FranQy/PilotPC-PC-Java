// PilotPC-Windows.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include "start.h"
#include <string>
using namespace std;
#pragma comment (lib,"Advapi32.lib")
enum  pilotButton{
	OFF, MUSIC, MULTIMEDIA, PLAYPAUSE, PERV, NEXT, STOP, EXIT, BACK, VOLDOWN, VOLUP, MUTE,
	UP, DOWN, RIGHT, LEFT, RETTURN,
	REWIND,//Przewijanie do ty³u
	FORWARD//przewijanie do przodu
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
	case MULTIMEDIA:
		keybd_event(VK_LAUNCH_MEDIA_SELECT, 0, 1, 0);
		keybd_event(VK_LAUNCH_MEDIA_SELECT, 0, 2, 0);
		break;

	}
}


JNIEXPORT void JNICALL Java_Biblioteka_autostart
(JNIEnv *env, jclass jobj, jboolean, jboolean, jstring f) {

	jboolean blnIsCopy;
	jstring jstrOutput;
	char* strCOut;
	const char* strCIn = (env)->GetStringUTFChars(f, &blnIsCopy);

	string folder = string("\"")+string(strCIn) + string("\\Windows.exe\" /no");
	HKEY hkTest;
	RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
	RegSetValueExA(hkTest, "PilotPC", 0, REG_SZ, (BYTE*)(folder.c_str()), folder.length());
}
