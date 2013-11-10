// PilotPC-Windows.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include "start.h"
enum przyciskiDoCpp{  VOLUP, VOLDOWN, PLAYPAUSE } ;
JNIEXPORT jint JNICALL Java_test_Start_kwadrat(JNIEnv *env, jclass jobj, jint a) {
	przyciskiDoCpp przycisk = (przyciskiDoCpp)a;
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
	case PLAYPAUSE:
		keybd_event(VK_MEDIA_PLAY_PAUSE, 0, 1, 0);
		keybd_event(VK_MEDIA_PLAY_PAUSE, 0, 2, 0);
		break;

	}
	
	return a*a;
	//return a*a;
}

