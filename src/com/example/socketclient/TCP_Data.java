package com.example.socketclient;



import java.io.Serializable;
import java.util.Hashtable;

/**
 * Created by franqy on 29.10.13.
 */
public class TCP_Data implements Serializable {


	public enum typ{GAMEPAD, PILOT, KEYBOARD, TOUCHPAD};
	  public enum  pilotButton{OFF, MUSIC, MULTIMEDIA, PLAYPAUSE, PERV, NEXT, STOP, EXIT, BACK, VOLDOWN, VOLUP, MUTE,
	    UP, DOWN, RIGHT, LEFT, RETTURN};

	  public enum touchedTYPE {NORMAL, LONG, UP, SCROLL, LPM, PPM};

	    public int touchpadX;
	    public int touchpadY;
	    public typ type;
	    public pilotButton button;
	    public touchedTYPE mouse;
    public int key;//kalwiatura
    public boolean shift;
    public boolean alt;
	    public void clean()
	    {
	        touchpadX = 0;
	        touchpadY = 0;
	      //  type = null;
	        //button = null;
	        mouse = null;

	    }

	}



