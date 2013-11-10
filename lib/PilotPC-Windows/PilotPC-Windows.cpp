// PilotPC-Windows.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include "start.h"
enum  pilotButton{
	OFF, MUSIC, MULTIMEDIA, PLAYPAUSE, PERV, NEXT, STOP, EXIT, BACK, VOLDOWN, VOLUP, MUTE,
	UP, DOWN, RIGHT, LEFT, RETTURN
};
JNIEXPORT void JNICALL Java_Pilot_click(JNIEnv *env, jobject jobj, jint a) {
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

	}
}

