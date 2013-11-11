#include "start.h"
#include <stdio.h>
#include <jni.h>

#include <X11/Xlib.h>
#include <X11/keysym.h>
#include <X11/extensions/XTest.h>
#include <X11/XF86keysym.h>

enum  pilotButton{
	OFF, MUSIC, MULTIMEDIA, PLAYPAUSE, PERV, NEXT, STOP, EXIT, BACK, VOLDOWN, VOLUP, MUTE,
	UP, DOWN, RIGHT, LEFT, RETTURN
};

Display *display;
unsigned int keycode;

JNIEXPORT void JNICALL Java_Pilot_click(JNIEnv *env, jobject jobj, jint a) 
{
    display = XOpenDisplay(NULL);
pilotButton przycisk = (pilotButton)a;

switch (przycisk)
	{
	case VOLUP:
		keycode = XKeysymToKeycode(display, XF86XK_AudioRaiseVolume);
		break;
	case VOLDOWN:
		keycode = XKeysymToKeycode(display, XF86XK_AudioLowerVolume);
		break;
	case MUTE:
		keycode = XKeysymToKeycode(display, XF86XK_AudioMute);
		break;
	case PLAYPAUSE:
		keycode = XKeysymToKeycode(display, XF86XK_AudioPlay);
		break;
	case NEXT:
		keycode = XKeysymToKeycode(display, XF86XK_AudioNext);
		break;
	case PERV:
		keycode = XKeysymToKeycode(display, XF86XK_AudioPrev);
		break;
	case STOP:
		keycode = XKeysymToKeycode(display, XF86XK_AudioStop);
		break;

	}



   XTestFakeKeyEvent(display, keycode, True, 0);
   XTestFakeKeyEvent(display, keycode, False, 0);
   XFlush(display);
}
