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

	}
}

