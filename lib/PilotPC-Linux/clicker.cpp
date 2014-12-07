#include "start.h"
#include <stdio.h>
#include <jni.h>
#include <fstream>      // std::fstream
#include <X11/Xlib.h>
#include <X11/keysym.h>
#include <X11/extensions/XTest.h>
#include <X11/XF86keysym.h>
#include <sys/stat.h>
#include <stdio.h>
enum  pilotButton{
	OFF, MUSIC, PLAYPAUSE, PERV, NEXT, STOP, EXIT, BACK, VOLDOWN, VOLUP, MUTE,
	UP, DOWN, RIGHT, LEFT, RETTURN,
	REWIND,//Przewijanie do tyłu
	FORWARD, PHOTO, VIDEO
};

Display *display;
unsigned int keycode;

JNIEXPORT void JNICALL Java_Biblioteka_click(JNIEnv *env, jclass jobj, jint a) {

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
	case MUSIC:
		keycode = XKeysymToKeycode(display, XF86XK_Music);
		break;

	}



   XTestFakeKeyEvent(display, keycode, True, 0);
   XTestFakeKeyEvent(display, keycode, False, 0);
   XFlush(display);
}
JNIEXPORT void JNICALL Java_Biblioteka_autostart
(JNIEnv *env, jclass jobj, jboolean nowy, jboolean, jstring f) {
if(nowy){
std::fstream fs;
fs.open ("/etc/init.d/PilotPC", std::fstream::in | std::fstream::out | std::fstream::app);

  fs << " ./";
  fs << f;
  fs << "/Linux.sh";

  fs.close();
  chmod("/etc/init.d/PilotPC",S_IRWXG|S_IRWXO|S_IRWXU);
  }else
  {
  remove("/etc/init.d/PilotPC");
  }
		}
JNIEXPORT void JNICALL Java_Biblioteka_runAsRoot
		(JNIEnv *env, jclass jobj, jstring str1, jstring str2){}
		
JNIEXPORT jboolean JNICALL Java_Biblioteka_CzyAutostart
(JNIEnv *env, jclass jobj) {
        std::fstream plik;
        plik.open("/etc/init.d/PilotPC", std::ios::in);  /* ważne, by nie tworzyć pliku, jeśli nie istnieje, stąd flaga nocreate */
        if ( plik.is_open() )
        {
            plik.close();
            return true;
        }
        plik.close();
        return false;
		}