package com.example.socketclient;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by franqy on 29.10.13.
 */
public class TCP_Data implements Serializable {


enum typ{GAMEPAD, PILOT, KEYBOARD, TOUCHPAD};
    enum  pilotButton{OFF, MUSIC, MULTIMEDIA, PLAYPAUSE, PERV, NEXT, STOP, EXIT, BACK, VOLDOWN, VOLUP, MUTE,
    UP, DOWN, RIGHT, LEFT, RETTURN};

    enum touchpadTYPE{NORMAL, LONG, SCROLL, LPM, PPM};

    int touchpadX;
    int touchpadY;



}