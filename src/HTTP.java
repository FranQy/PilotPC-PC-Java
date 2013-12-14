import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.xml.bind.DatatypeConverter;

import org.json.*;

import com.example.socketclient.TCP_Data;
import com.example.socketclient.TCP_Data.pilotButton;
import com.example.socketclient.TCP_Data.touchedTYPE;
import com.example.socketclient.TCP_Data.typ;

public class HTTP {
static TCP_Data polaczenie(InputStream is, Socket soc,String wyj) throws IOException
{
	
	System.out.print(wyj);
	if(wyj.indexOf("/")==0&&wyj.indexOf("?")>0)
	{
		
		OutputStream os =soc.getOutputStream();

		byte i=id(wyj);
		String wysylanie;
		TCP_Data ret=new TCP_Data();
		if(Polaczenie.polaczeniaHttp[i].zablokowane)
			wysylanie="HTTP/1.1 403	Forbidden\r\nSet-Cookie: id="+i+"; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nZostałeś rozłączony";
		else if(wyj.indexOf("/"+Program.ustawienia.haslo)==0)
		{ wysylanie="HTTP/1.1 200 OK\r\nSet-Cookie: id="+i+"; path=/\r\n\r\nok";
		String klasaString=wyj.substring(wyj.indexOf("?")+1, wyj.indexOf(" HTTP"));
		klasaString=klasaString.replaceAll("%22", "\"");
		JSONObject klasaObjekt=new JSONObject(klasaString);

		try
			{ret.touchpadX=klasaObjekt.getInt("touchpadX");
		ret.touchpadY=klasaObjekt.getInt("touchpadY");
		ret.type=typ.values()[klasaObjekt.getInt("type")];
		ret.mouse=touchedTYPE.values()[klasaObjekt.getInt("mouse")];
		ret.button=pilotButton.values()[klasaObjekt.getInt("button")];
			}
		catch(Exception e){ret=null;}
		
	}
		else
		{
			wysylanie="HTTP/1.1 403	Forbidden\r\nSet-Cookie: id="+i+"; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nKod błędny";
			
		}
		os.write(wysylanie.getBytes());
		os.close();
		is.close();
		
		return ret;
	}
	else if(wyj.indexOf("/")==0)
	{
		OutputStream os =soc.getOutputStream();
				byte i=id(wyj);
				String wysylanie;
				if(Polaczenie.polaczeniaHttp[i].zablokowane)
					wysylanie="HTTP/1.1 403	Forbidden\r\nSet-Cookie: id="+i+"; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nZostałeś rozłączony";
				else if(wyj.indexOf("/"+Program.ustawienia.haslo)==0)
				{
					String gamepadBase64;
					try
					{
					  ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					  InputStream input = classLoader.getResourceAsStream(
					    "resources/gamepad.png");
					  byte[] gamepadBytes = new byte[5234];
					  input.read(gamepadBytes);
					  gamepadBase64="data:image/png;base64,"+DatatypeConverter.printBase64Binary(gamepadBytes);
					}
					catch ( IOException e ) { gamepadBase64=""; }
					String pilotBase64;
					try
					{
					  ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					  InputStream input = classLoader.getResourceAsStream(
					    "resources/pilot.png");
					  byte[] pilotBytes = new byte[2415];
					  input.read(pilotBytes);
					  pilotBase64="data:image/png;base64,"+DatatypeConverter.printBase64Binary(pilotBytes);
					}
					catch ( IOException e ) { pilotBase64=""; }
					String klawiaturaBase64;
					try
					{
					  ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					  InputStream input = classLoader.getResourceAsStream(
					    "resources/klawiatura.png");
					  byte[] klawiaturaBytes = new byte[2103];
					  input.read(klawiaturaBytes);
					  klawiaturaBase64="data:image/png;base64,"+DatatypeConverter.printBase64Binary(klawiaturaBytes);
					}
					catch ( IOException e ) { klawiaturaBase64=""; }
					String touchpadBase64;
					try
					{
					  ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					  InputStream input = classLoader.getResourceAsStream(
					    "resources/myszka.png");
					  byte[] touchpadBytes = new byte[2572];
					  input.read(touchpadBytes);
					  touchpadBase64="data:image/png;base64,"+DatatypeConverter.printBase64Binary(touchpadBytes);
					}
					catch ( IOException e ) { touchpadBase64=""; }
		 wysylanie="HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id="+i+"; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
				+"<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
				+"<meta name=\"viewport\" content=\"width=240, initial-scale=1, user-scalable=no\" />"
				+ "<style>"
				+ "body{background:#26211b;color:white;font-style:non-sherif;}"
				+ "#menu{position: absolute;bottom: 0;background: #2e2e2e;margin: 0;left: 0;width: 100%;text-align: center;margin:0;padding:0;}"
				+ "#menu li{display:inline;}.karta{position:absolute;left:0;top:0;width:100%;bottom:64px;display:none;}"
				+ "#menu li img{width:10%;padding:0 2%;}"
				+ "#pilot .przycisk{width:20%;}"
				+ "</style>"
				+"<script>/*<![CDATA[*/\n"
				+ "var czasPrzesylu=new Date();"
				+ "var polaczono=false;"
				+ "var pilot=new Object();\n"
				+ "pilot.click=function(przycisk)"
				+ "{"
				+ "var data=new TCP_Data();"
				+ "data.type=TCP_Data.typ.PILOT;"
				+ "data.button=przycisk;"
				+ "send(data);"
				+ "};\n"
				+ " var touchpad=new Object();touchpad.wcisniete=false;touchpad.mPreviousX=0; touchpad.mPreviousY=0;touchpad.oldX =0;touchpad.oldY=0;touchpad.longClicked = false;touchpad.returnState = true;"
				+"touchpad.onTouchDown=function(event){"
				+ "touchpad.mPreviousX=touchpad.oldX=event.touches[0].screenX;"
				+ "touchpad.mPreviousY=touchpad.oldY=event.touches[0].screenY;"
				+ "touchpad.wcisniete=true;"
				+ "};"
				+"touchpad.onMouseDown=function(event){"
				+ "touchpad.mPreviousX=touchpad.oldX=event.screenX;"
				+ "touchpad.mPreviousY=touchpad.oldY=event.screenY;"
				+ "touchpad.wcisniete=true;"
				+ "};"
				+"touchpad.onTouchUp=function(event){"
				
				+ "touchpad.wcisniete=false;"
				+ "if(event.changedTouches[0].screenX>touchpad.oldX-2&&event.changedTouches[0].screenX<touchpad.oldX+2&&event.changedTouches[0].screenY>touchpad.oldY-2&&event.changedTouches[0].screenY<touchpad.oldY+2)"
				
				//+ "if(event.screenX>touchpad.oldX-10)"
				+ "{"
				+"var data=new TCP_Data();data.mouse = TCP_Data.touchedTYPE.LPM;data.touchpadX = 0;data.touchpadY =0;"
				+ "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
				+ "}"
				+ "};"
				+"touchpad.onMouseUp=function(event){\n"
				
				+ "touchpad.wcisniete=false;\n"
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
				+ "var czas=new Date();"
				+ "data.czas=czas.getTime()*1000+czas.getMilliseconds();"
				//+ "alert(data);"
				+ "socket.open('get', '?'+JSON.stringify(data))\n"
				//+ "alert('?'+JSON.stringify(data));"
				+ "socket.onloadend=function(){\n"
				+ "if(socket.responseText=='ok')\n"
				+ "{czasPrzesylu=czas;"
				+ "polaczono=true;"
				+ "}};\n"
				+ "socket.send();\n"
				+ "}"
				+ "function TCP_Data(){this.touchpadX=0;this.touchpadY=0;this.button=0;this.type=0;this.mouse=0;};"
				+ "TCP_Data.touchedTYPE={NORMAL:0, LONG:1, UP:2, SCROLL:3, LPM:4, PPM:5};"
				+ "TCP_Data.typ={GAMEPAD:0, PILOT:1, KEYBOARD:2, TOUCHPAD:3};"
				+ "TCP_Data.pilotButton={OFF:0, MUSIC:1, MULTIMEDIA:2, PLAYPAUSE:3, PERV:4, NEXT:5, STOP:6, EXIT:7, BACK:8, VOLDOWN:9, VOLUP:10, MUTE:11,UP:12, DOWN:13, RIGHT:14, LEFT:15, RETTURN:16};"
				+ "function kartaPokaz(id){"
				+ "document.getElementById('gamepad').style.display=document.getElementById('pilot').style.display=document.getElementById('klawiatura').style.display=document.getElementById('touchpad').style.display='none';"
				+ "document.getElementById(id).style.display='block';"
				+ "}"
				+ "var timer=setInterval(function(){"
				+ "if(new Date()-czasPrzesylu>3000&&polaczono)"
				+ "{"
				+ "polaczono=false;"
				+ "alert('Rozłączono!');"
				+ "}"
				+ "if(new Date()-czasPrzesylu>1000)"
				+ "send(new Object());"
				+ "},250);"
				+ "/*]]>*/</script>"
				+"</head><body>"
				+ "<div class=\"karta\" id=\"gamepad\">Gamepad wkrodce</div>"
				+ "<div class=\"karta\" id=\"pilot\">"
				+ "<img alt=\"Wycisz\" onclick=\"pilot.click(TCP_Data.pilotButton.MUTE)\" class=\"przycisk\"/>"
				+ "<img alt=\"Ciszej\" onclick=\"pilot.click(TCP_Data.pilotButton.VOLDOWN)\" class=\"przycisk\"/>"
				+ "<img alt=\"Głośniej\" onclick=\"pilot.click(TCP_Data.pilotButton.VOLUP)\" class=\"przycisk\"/>"
				+ "<img alt=\"Poprzednia\" onclick=\"pilot.click(TCP_Data.pilotButton.PERV)\" class=\"przycisk\"/>"
				+ "<img alt=\"Następna\" onclick=\"pilot.click(TCP_Data.pilotButton.NEXT)\" class=\"przycisk\"/>"
				+ "<img alt=\"Play/Pauza\" onclick=\"pilot.click(TCP_Data.pilotButton.PLAYPAUSE)\" class=\"przycisk\"/>"
				+ "<img alt=\"Stop\" onclick=\"pilot.click(TCP_Data.pilotButton.STOP)\" class=\"przycisk\"/>"
				+ "</div>"
				+ "<div class=\"karta\" id=\"klawiatura\">Klawiatura wkrodce</div>"
				//Tu były problemy ze zdarzeniami
				//+ "<div class=\"karta\" style=\"display:block\" ontouchmove=\"return touchpad.onTouchMove(event)\" onmousemove=\"return touchpad.onMouseMove(event)\" ontouchdown=\"touchpad.onTouchDown(event)\" onmousedown=\"touchpad.onTouchDown(event)\" ontouchup=\"touchpad.onTouchUp(event)\" onmouseup=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\" onmouseleave=\"touchpad.onTouchUp(event)\" id=\"touchpad\"></div>"
				//+ "<div class=\"karta\" style=\"display:block\" onmousemove=\"return touchpad.onMouseMove(event)\" onmousedown=\"touchpad.onMouseDown(event)\" onmouseup=\"touchpad.onMouseUp(event)\" onmouseleave=\"touchpad.onMouseUp(event)\"  id=\"touchpad\"></div>"
				//+ "<div class=\"karta\" style=\"display:block\" ontouchmove=\"return touchpad.onTouchMove(event)\" ontouchstart=\"touchpad.onTouchDown(event)\" ontouchend=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\"  id=\"touchpad\"></div>"
				+ "<div class=\"karta\" style=\"display:block\" onmousemove=\"return touchpad.onMouseMove(event)\" onmousedown=\"touchpad.onMouseDown(event)\" onmouseup=\"touchpad.onMouseUp(event)\" onmouseleave=\"touchpad.onMouseUp(event)\" ontouchmove=\"return touchpad.onTouchMove(event)\" ontouchstart=\"touchpad.onTouchDown(event)\" ontouchend=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\" id=\"touchpad\"></div>"
				+"<ul id=\"menu\"><li onclick='kartaPokaz(\"gamepad\")'><img title=\"gamepad\" src=\""+gamepadBase64+"\"/></li><li onclick='kartaPokaz(\"pilot\")'><img title=\"pilot\" src=\""+pilotBase64+"\"/></li><li onclick='kartaPokaz(\"klawiatura\")'><img title=\"klawiatura\" src=\""+klawiaturaBase64+"\"/></li><li onclick='kartaPokaz(\"touchpad\")'><img title=\"touchpad\" src=\""+touchpadBase64+"\"/></li></ul></body></html>";
		
				}

				else if(wyj.indexOf("/ ")==0)
					{
					String typ;
					if(wyj.contains("mobile")||wyj.contains("touch")||wyj.contains("android"))
						typ="number";
					else
						typ="text";
					wysylanie="HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id="+i+"; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
							+"<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
							+"<meta name=\"viewport\" content=\"width=240, initial-scale=1, user-scalable=no\" />"
							+ "</head>"
							+ "<body style=\"color:white;background:#26211b;\">"
							+ "<form onsubmit=\"document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\"><label>wpisz kod<input type=\""+typ+"\"/></label><input type=\"submit\" value=\"ok\"/></form>"
							+ "</body></html>";
							
					}
				else
				{
					String typ;
					if(wyj.contains("mobile")||wyj.contains("touch")||wyj.contains("android"))
						typ="number";
					else
						typ="text";
					wysylanie="HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id="+i+"; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
							+"<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
							+"<meta name=\"viewport\" content=\"width=240, initial-scale=1, user-scalable=no\" />"
							+ "</head>"
							+ "<body style=\"color:white;background:#26211b;\">"
							+ "<strong>Błędny kod!</strong>"
							+ "<form onsubmit=\"document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\"><label>wpisz kod<input type=\""+typ+"\"/></label><input type=\"submit\" value=\"ok\"/></form>"
							+ "</body></html>";
				}
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
				Polaczenie.polaczeniaHttp[i].UserAgent=new UserAgent(wyj.substring(wyj.indexOf("User-Agent:")+11, wyj.indexOf('\r',wyj.indexOf("User-Agent:"))));
				}return i;
}
}
