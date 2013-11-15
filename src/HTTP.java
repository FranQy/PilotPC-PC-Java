import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.*;

import com.example.socketclient.TCP_Data;
import com.example.socketclient.TCP_Data.touchedTYPE;
import com.example.socketclient.TCP_Data.typ;

public class HTTP {
static TCP_Data polaczenie(InputStream is, Socket soc) throws IOException
{
	byte liczbaNowychLinii=0;
	String wyj="";
	while (true) {
int n = is.read();
	if (n == -1){

		break;
	}
	else
	{
		//robot.keyPress(n+128);
		if(n=='\n'||n=='\r')
			liczbaNowychLinii++;
		else
			liczbaNowychLinii=0;
		wyj+=(char)n;

	}

	if (liczbaNowychLinii==4){

		break;
	}}
	System.out.print(wyj);
	if(wyj.indexOf("/?")==0)
	{
		String klasaString=wyj.substring(2, wyj.indexOf(" HTTP"));
		klasaString=klasaString.replaceAll("%22", "\"");
		JSONObject klasaObjekt=new JSONObject(klasaString);
		TCP_Data ret=new TCP_Data();

		ret.touchpadX=klasaObjekt.getInt("touchpadX");
		ret.touchpadY=klasaObjekt.getInt("touchpadY");
		ret.type=typ.values()[klasaObjekt.getInt("type")];
		ret.mouse=touchedTYPE.values()[klasaObjekt.getInt("mouse")];
		
		OutputStream os =soc.getOutputStream();
		
		os.close();
		is.close();
		return ret;
	}
	else if(wyj.indexOf("/")==0)
	{
		OutputStream os =soc.getOutputStream();
		String wysylanie="HTTP/1.1 200 OK\r\nServer: PilotPC\r\nContent-Type: application/xhtml+xml\r\n\r\n"
				+"<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
				+"<style>#menu{position: absolute;bottom: 0;background: gray;margin: 0;left: 0;width: 100%;}#menu li{display:inline;}.karta{position:absolute;left:0;top:0;width:100%;bottom:20px;display:none;}</style>"
				+"<script> var touchpad=new Object();touchpad.mPreviousX=0; touchpad.mPreviousY=0;touchpad.oldX =0;touchpad.oldY=0;touchpad.longClicked = false;touchpad.returnState = true;"
				+"touchpad.onTouchMove=function(e){var dx=this.mPreviousX = event.clientX - this.mPreviousX;var dy =this.mPreviousY= event.clientY - this.mPreviousY;"
				+"var data=new TCP_Data();data.mouse = TCP_Data.touchedTYPE.NORMAL;data.touchpadX = dx;data.touchpadY =dy;data.type=TCP_Data.typ.TOUCHPAD;send(data);};"
				+"function send(data){"
				+ "var socket=new XMLHttpRequest();"
				+ "socket.open('get', '?'+JSON.stringify(data));"
				+ "socket.send();"
				+ "}"
				+ "function TCP_Data(){this.touchpadX=0;this.touchpadY=0;};"
				+ "TCP_Data.touchedTYPE={NORMAL:0, LONG:1, UP:2, SCROLL:3, LPM:4, PPM:5};"
				+ "TCP_Data.typ={GAMEPAD:0, PILOT:1, KEYBOARD:2, TOUCHPAD:3};</script>"
				+"</head><body><div class=\"karta\" id=\"gamepad\"></div><div class=\"karta\" id=\"pilot\"></div><div class=\"karta\" id=\"klawiatura\"></div><div class=\"karta\" style=\"display:block\" ontouchmove=\"touchpad.onTouchMove(event)\" onmousemove=\"touchpad.onTouchMove(event)\" id=\"touchpad\"></div>"
				+"<ul id=\"menu\"><li><img/></li><li><img title=\"gamepad\"/></li><li><img/></li><li><img title=\"touchpad\"/></li></ul>Aplikacja w trakcie pisania :)</body></html>";
		os.write(wysylanie.getBytes());
		os.close();
		is.close();
	}
	//else
			return null;
}
}
