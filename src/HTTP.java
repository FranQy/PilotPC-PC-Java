import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.*;

import com.example.socketclient.TCP_Data;
import com.example.socketclient.TCP_Data.pilotButton;
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
	if(wyj.indexOf("/")==0&&wyj.indexOf("?")>0)
	{
		
		String klasaString=wyj.substring(wyj.indexOf("?")+1, wyj.indexOf(" HTTP"));
		klasaString=klasaString.replaceAll("%22", "\"");
		JSONObject klasaObjekt=new JSONObject(klasaString);
		TCP_Data ret=new TCP_Data();

		ret.touchpadX=klasaObjekt.getInt("touchpadX");
		ret.touchpadY=klasaObjekt.getInt("touchpadY");
		ret.type=typ.values()[klasaObjekt.getInt("type")];
		ret.mouse=touchedTYPE.values()[klasaObjekt.getInt("mouse")];
		ret.button=pilotButton.values()[klasaObjekt.getInt("button")];
		OutputStream os =soc.getOutputStream();

		byte i=id(wyj);
		String wysylanie="HTTP/1.1 200 OK\r\nSet-Cookie: id="+i+"; path=/\r\n\r\n";
		os.write(wysylanie.getBytes());
		os.close();
		is.close();
		
		return ret;
	}
	else if(wyj.indexOf("/")==0)
	{
		OutputStream os =soc.getOutputStream();
				byte i=id(wyj);
		String wysylanie="HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id="+i+"; path=/\r\nContent-Type: application/xhtml+xml; charset=UTF-8\r\n\r\n"
				+"<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
				+"<meta name=\"viewport\" content=\"width=240, initial-scale=1, user-scalable=no\" />"
				+ "<style>#menu{position: absolute;bottom: 0;background: gray;margin: 0;left: 0;width: 100%;}#menu li{display:inline;}.karta{position:absolute;left:0;top:0;width:100%;bottom:20px;display:none;} #menu li img{width:16px;height:16px;}</style>"
				+"<script>/*<![CDATA[*/ var touchpad=new Object();touchpad.wcisniete=false;touchpad.mPreviousX=0; touchpad.mPreviousY=0;touchpad.oldX =0;touchpad.oldY=0;touchpad.longClicked = false;touchpad.returnState = true;"
				+"touchpad.onTouchDown=function(event){"
				+ "touchpad.mPreviousX=touchpad.oldX=event.touches[0].screenX;"
				+ "touchpad.mPreviousY=touchpad.oldY=event.touches[0].screenY;"
				+ "touchpad.wcisniete=true;document.body.style.background='red';"
				+ "};"
				+"touchpad.onMouseDown=function(event){"
				+ "touchpad.mPreviousX=touchpad.oldX=event.screenX;"
				+ "touchpad.mPreviousY=touchpad.oldY=event.screenY;"
				+ "touchpad.wcisniete=true;document.body.style.background='red';"
				+ "};"
				+"touchpad.onTouchUp=function(event){"
				
				+ "touchpad.wcisniete=false;document.body.style.background='blue';"
				+ "if(event.changedTouches[0].screenX>touchpad.oldX-2&&event.changedTouches[0].screenX<touchpad.oldX+2&&event.changedTouches[0].screenY>touchpad.oldY-2&&event.changedTouches[0].screenY<touchpad.oldY+2)"
				
				//+ "if(event.screenX>touchpad.oldX-10)"
				+ "{"
				+"var data=new TCP_Data();data.mouse = TCP_Data.touchedTYPE.LPM;data.touchpadX = 0;data.touchpadY =0;"
				+ "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
				+ "}"
				+ "};"
				+"touchpad.onMouseUp=function(event){\n"
				
				+ "touchpad.wcisniete=false;\n"
				+ "document.body.style.background='blue';\n"
				+ "if(event.screenX>touchpad.oldX-2&&event.screenX<touchpad.oldX+2&&event.screenY>touchpad.oldY-2&&event.screenY<touchpad.oldY+2)"
				
				//+ "if(event.screenX>touchpad.oldX-10)"
				+ "{"
				+"var data=new TCP_Data();data.mouse = TCP_Data.touchedTYPE.LPM;data.touchpadX = 0;data.touchpadY =0;"
				+ "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
				+ "}"
				+ "};"
				+ "touchpad.onMouseMove=function(event){"
				+ "if(touchpad.wcisniete){"
				+ "var dx = event.screenX - this.mPreviousX;var dy = event.screenY - this.mPreviousY;"
				+ "this.mPreviousX=event.screenX;"
				+ "this.mPreviousY=event.screenY;"
				+"var data=new TCP_Data();data.mouse = TCP_Data.touchedTYPE.NORMAL;data.touchpadX = dx;data.touchpadY =dy;"
				+ "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
				+ "return false;}};"
				+ "touchpad.onTouchMove=function(event){"
				+ "var dx = event.touches[0].screenX - this.mPreviousX;var dy = event.touches[0].screenY - this.mPreviousY;"
				+ "this.mPreviousX=event.touches[0].screenX;"
				+ "this.mPreviousY=event.touches[0].screenY;"
				+"var data=new TCP_Data();data.mouse = TCP_Data.touchedTYPE.NORMAL;data.touchpadX = dx;data.touchpadY =dy;"
				+ "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
				+ "return false;};"
				+"function send(data){"
				+ "var socket=new XMLHttpRequest();"
				//+ "alert(data);"
				+ "socket.open('get', '?'+JSON.stringify(data));"
				//+ "alert('?'+JSON.stringify(data));"
				+ "socket.send();"
				+ "}"
				+ "function TCP_Data(){this.touchpadX=0;this.touchpadY=0;this.button=0;this.type=0;this.mouse=0;};"
				+ "TCP_Data.touchedTYPE={NORMAL:0, LONG:1, UP:2, SCROLL:3, LPM:4, PPM:5};"
				+ "TCP_Data.typ={GAMEPAD:0, PILOT:1, KEYBOARD:2, TOUCHPAD:3};"
				+ "function kartaPokaz(id){"
				+ "document.getElementById('gamepad').style.display=document.getElementById('pilot').style.display=document.getElementById('klawiatura').style.display=document.getElementById('touchpad').style.display='none';"
				+ "document.getElementById(id).style.display='block';"
				+ "}"
				+ "/*]]>*/</script>"
				+"</head><body>"
				+ "<div class=\"karta\" id=\"gamepad\">Gamepad wkrotce</div>"
				+ "<div class=\"karta\" id=\"pilot\">Pilot wkrotce</div>"
				+ "<div class=\"karta\" id=\"klawiatura\">Klawiatura wkrodce</div>"
				//Tu były problemy ze zdarzeniami
				//+ "<div class=\"karta\" style=\"display:block\" ontouchmove=\"return touchpad.onTouchMove(event)\" onmousemove=\"return touchpad.onMouseMove(event)\" ontouchdown=\"touchpad.onTouchDown(event)\" onmousedown=\"touchpad.onTouchDown(event)\" ontouchup=\"touchpad.onTouchUp(event)\" onmouseup=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\" onmouseleave=\"touchpad.onTouchUp(event)\" id=\"touchpad\"></div>"
				//+ "<div class=\"karta\" style=\"display:block\" onmousemove=\"return touchpad.onMouseMove(event)\" onmousedown=\"touchpad.onMouseDown(event)\" onmouseup=\"touchpad.onMouseUp(event)\" onmouseleave=\"touchpad.onMouseUp(event)\"  id=\"touchpad\"></div>"
				//+ "<div class=\"karta\" style=\"display:block\" ontouchmove=\"return touchpad.onTouchMove(event)\" ontouchstart=\"touchpad.onTouchDown(event)\" ontouchend=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\"  id=\"touchpad\"></div>"
				+ "<div class=\"karta\" style=\"display:block\" onmousemove=\"return touchpad.onMouseMove(event)\" onmousedown=\"touchpad.onMouseDown(event)\" onmouseup=\"touchpad.onMouseUp(event)\" onmouseleave=\"touchpad.onMouseUp(event)\" ontouchmove=\"return touchpad.onTouchMove(event)\" ontouchstart=\"touchpad.onTouchDown(event)\" ontouchend=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\" id=\"touchpad\"></div>"
				+"<ul id=\"menu\"><li onclick='kartaPokaz(\"gamepad\")'><img title=\"gamepad\"/></li><li onclick='kartaPokaz(\"pilot\")'><img title=\"pilot\"/></li><li onclick='kartaPokaz(\"klawiatura\")'><img title=\"klawiatura\"/></li><li onclick='kartaPokaz(\"touchpad\")'><img title=\"touchpad\"/></li></ul></body></html>";
		os.write(wysylanie.getBytes());
		os.close();
		is.close();
	}
	//else
			return null;
}
static byte id(String wyj)
{
	//spradzanie po cookie czy jest już
			byte i=0;
			if(wyj.contains("Cookie: id="))
			{
				i=(byte)Integer.parseInt(wyj.substring(wyj.indexOf("Cookie: id=")+11, wyj.indexOf('\r',wyj.indexOf("Cookie: id="))));
				
			}
			else
			for(;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
			{
				if(Polaczenie.polaczeniaHttp[i]==null){
					break;
				}
			}
				if(Polaczenie.polaczeniaHttp[i]==null){
				Polaczenie.polaczeniaHttp[i]=new HttpPolaczenie();
				Polaczenie.polaczeniaHttp[i].UserAgent=wyj.substring(wyj.indexOf("User-Agent:")+11, wyj.indexOf('\r',wyj.indexOf("User-Agent:")));
				}return i;
}
}
