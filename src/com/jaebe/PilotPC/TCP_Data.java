package com.jaebe.PilotPC;

import java.io.Serializable;

/**
 * Created by franqy on 29.10.13.
 */
public class TCP_Data implements Serializable {

    public enum typ {GAMEPAD, PILOT, KEYBOARD, TOUCHPAD, YT}

    ;

    public enum pilotButton {
        OFF, MUSIC, PLAYPAUSE, PERV, NEXT, STOP, EXIT, BACK, VOLDOWN, VOLUP, MUTE,
        UP, DOWN, RIGHT, LEFT, RETTURN,
        REWIND,//Przewijanie do tyłu
        FORWARD, PHOTO, VIDEO
    }

    ;

    public enum touchedTYPE {NORMAL, LONG, UP, SCROLL, LPM, PPM}

    ;

    public int touchpadX;
    public int touchpadY;
    public typ type;
    public pilotButton button;
    public touchedTYPE mouse;
    public int key;//kalwiatura
    public String ytUrl; // link do filmiku na yt


    public void clean() {
        touchpadX = 0;
        touchpadY = 0;
        //type = null;
        button = null;
        mouse = null;

    }

}