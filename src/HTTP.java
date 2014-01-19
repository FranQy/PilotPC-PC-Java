import com.example.socketclient.TCP_Data;
import com.example.socketclient.TCP_Data.pilotButton;
import com.example.socketclient.TCP_Data.touchedTYPE;
import com.example.socketclient.TCP_Data.typ;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class HTTP {
    public static TCP_Data[] doWykonania = new TCP_Data[16];

    public static TCP_Data polaczenie(InputStream is, Socket soc, String wyj) throws IOException {

        //System.out.print(wyj);
        if (wyj.indexOf("/") == 0 && wyj.indexOf("?") > 0) {

            OutputStream os = soc.getOutputStream();

            byte i = id(wyj);
            String wysylanie;
            TCP_Data ret = new TCP_Data();
            if (Polaczenie.polaczeniaHttp[i].zablokowane)
                wysylanie = "HTTP/1.1 403	Forbidden\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nZostałeś rozłączony";
            else if (wyj.indexOf("/" + Program.ustawienia.haslo) == 0) {
                wysylanie = "HTTP/1.1 200 OK\r\nSet-Cookie: id=" + i + "; path=/\r\n\r\nok";
                String klasaString = wyj.substring(wyj.indexOf("?") + 1, wyj.indexOf(" HTTP"));
                klasaString = klasaString.replaceAll("%22", "\"");
                JSONObject klasaObjekt = new JSONObject(klasaString);

                try {
                    ret.touchpadX = klasaObjekt.getInt("touchpadX");
                    ret.touchpadY = klasaObjekt.getInt("touchpadY");
                    ret.type = typ.values()[klasaObjekt.getInt("type")];
                    ret.mouse = touchedTYPE.values()[klasaObjekt.getInt("mouse")];
                    try
                    {
                        ret.key = klasaObjekt.getInt("key");
                    }
                    catch(Exception e)
                    {}

                        ret.button = pilotButton.values()[klasaObjekt.getInt("button")];

                } catch (Exception e) {
                    ret = null;
                }

            } else {
                wysylanie = "HTTP/1.1 403	Forbidden\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nKod błędny";

            }
            os.write(wysylanie.getBytes());
            os.close();
            is.close();

            return ret;
        } else if (wyj.indexOf("/") == 0) {
            OutputStream os = soc.getOutputStream();
            byte i = id(wyj);
            String wysylanie;
            if (Polaczenie.polaczeniaHttp[i].zablokowane)
                wysylanie = "HTTP/1.1 403	Forbidden\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\nZostałeś rozłączony";
            else if (wyj.indexOf("/dodatek") == 0) {
                wysylanie = "HTTP/1.1 200 OK\n" +
                        "Server: PilotPC\n" +
                        "Set-Cookie: id="+i+"; path=/\n" +
                        "Content-Type: application/json; charset=UTF-8\n" +
                        "\n" +
                        "{\"polecenia\":[";
                boolean przecinek=false;
                for (byte x = 0; x < doWykonania.length; x++) {
                    if (doWykonania[x] != null) {
                        if (doWykonania[x].type == typ.PILOT) {
                            if(przecinek)
                                wysylanie+=",";
                            wysylanie += "{\"akcja\":" + doWykonania[x].button.ordinal() + "}";
                            przecinek=true;
                        }
                        doWykonania[x] = null;
                    }

                }
                wysylanie += "]}";
            } else if (wyj.indexOf("/" + Program.ustawienia.haslo+"/pulpit/") == 0) {
            wysylanie=Pulpit.HTTP(wyj,i, os);
            }
                else if (wyj.indexOf("/" + Program.ustawienia.haslo) == 0) {
                String gamepadBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/gamepad.png");
                    byte[] gamepadBytes = new byte[5234];
                    input.read(gamepadBytes);
                    gamepadBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(gamepadBytes);
                } catch (IOException e) {
                    gamepadBase64 = "";
                }
                String pilotBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/pilot.png");
                    byte[] pilotBytes = new byte[2415];
                    input.read(pilotBytes);
                    pilotBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(pilotBytes);
                } catch (IOException e) {
                    pilotBase64 = "";
                }
                String klawiaturaBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/klawiatura.png");
                    byte[] klawiaturaBytes = new byte[2103];
                    input.read(klawiaturaBytes);
                    klawiaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(klawiaturaBytes);
                } catch (IOException e) {
                    klawiaturaBase64 = "";
                }
                String touchpadBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/myszka.png");
                    byte[] touchpadBytes = new byte[2572];
                    input.read(touchpadBytes);
                    touchpadBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(touchpadBytes);
                } catch (IOException e) {
                    touchpadBase64 = "";
                }
                String menuBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/opcje.png");
                    byte[] menuBytes = new byte[3000];
                    input.read(menuBytes);
                    menuBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(menuBytes);
                } catch (IOException e) {
                    menuBase64 = "";
                }
                String przyciskiBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/pilot720p.png");
                    przyciskiBase64 = "data:image/png;base64,";
                    byte[] przyciskiBytes = new byte[94707];
                    byte[] przyciskiBytes2 = new byte[1024];
                    int licznik = 0;
                    int a = 1;
                    while (a > 0) {

                        a = input.read(przyciskiBytes2);
                        for (int z = 0; z < a; z++) {
                            przyciskiBytes[licznik + z] = przyciskiBytes2[z];
                        }
                        licznik += a;
                    }
                    przyciskiBase64 += DatatypeConverter.printBase64Binary(przyciskiBytes);
                } catch (IOException e) {
                    przyciskiBase64 = "";
                }
                wysylanie = "HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
                        + "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
                        + "<meta name=\"viewport\" content=\"width=220px, initial-scale=1, user-scalable=no\" />"
                        + "<style>"
                        + "*{-ms-touch-action: none;touch-action: none;}" +
                        "body{font-family:\"Segoe UI Light\",\"Segoe UI\",arial;overflow:hidden;background:#26211b;color:white;font-style:non-sherif;}"
                        + "#menu{z-index:10;transition:all 300ms;-webkit-transition:all 300ms;height:10%;position: absolute;top: 90%;background: #2e2e2e;margin: 0;left: 0;width: 100%;text-align: center;margin:0;padding:0;}"
                        + "#menu li{display:inline;}" +
                        ".karta{position:absolute;left:0;top:0;width:100%;bottom:10%;display:none;}"
                        + "#menu li img{height:100%;padding:0 2%;}" +
                        "#menur{z-index:10;background:#2e2e2e;text-align:left;height:85%;width:100%;position:absolute;top:100%;transition:all 300ms;-webkit-transition:all 300ms;left:0;padding-left:5px;}" +
                        "#menur h2{margin:0;}" +
                        "#menur .podmenu{background:#5a555a;padding:5px 0 5px 5px;margin:5px 0 5px -5px;}"
                        + "#pilot #przyciski{height:100%;margin: auto; display: block;}" +
                        "#pulpit img{width:100%;height:100%;position:absolute;}" +
                        "</style>"
                        + "<script>/*<![CDATA[*/\n"
                        + "var czasPrzesylu=new Date();"
                        + "var polaczono=false;" +
                        "var jakosc=[];"
                        + "var pilot=new Object();\n"
                        + "pilot.trzymajLicz=0;" +
                        "pilot.trzymaj=null;" +
                        "pilot.click=function(przycisk)"
                        + "{"
                        + "var data=new TCP_Data();"
                        + "data.type=TCP_Data.typ.PILOT;"
                        + "data.button=przycisk;"
                        + "send(data);"
                        + "};\n"
                        + "pilot.clickTrzymaj=function(przycisk, przycisk2)"
                        + "{" +
                        "if(przycisk2==undefined)"
                        + "pilot.trzymaj=przycisk;" +
                        "else " +
                         "pilot.trzymaj=przycisk2;" +
                        "" +
                        "pilot.trzymajLicz=-10;" +
                        "" +
                        "pilot.click(przycisk);"
                        + "};" +
                        "pilot.timer=setInterval(function(){" +
                        "if(pilot.trzymajLicz++>0&&pilot.trzymaj!=null)" +
                        "pilot.click(pilot.trzymaj);" +
                        "},50);\n"
                        + " var touchpad=new Object();touchpad.czas=new Date(0);touchpad.wcisniete=false;touchpad.mPreviousX=0; touchpad.mPreviousY=0;touchpad.oldX =0;touchpad.oldY=0;touchpad.longClicked = false;touchpad.returnState = true;"
                        + "touchpad.onTouchDown=function(event){" +
                        "touchpad.longClicked=false;touchpad.czas=new Date(0);"
                        + "touchpad.czas=new Date();" +
                        "touchpad.mPreviousX=touchpad.oldX=event.touches[0].screenX;"
                        + "touchpad.mPreviousY=touchpad.oldY=event.touches[0].screenY;"
                        + "touchpad.wcisniete=true;" +
                        "if(event.touches[0].screenX>document.body.clientWidth*0.9)" +
                        "touchpad.typ=TCP_Data.touchedTYPE.SCROLL;" +
                        "else " +
                        "touchpad.typ=TCP_Data.touchedTYPE.NORMAL;" +
                        "return false;"
                        + "};"
                        + "touchpad.onMouseDown=function(event){" +

                        "touchpad.longClicked=false;touchpad.czas=new Date(0);" +
                        "touchpad.czas=new Date();"
                        + "touchpad.mPreviousX=touchpad.oldX=event.screenX;"
                        + "touchpad.mPreviousY=touchpad.oldY=event.screenY;"
                        + "touchpad.wcisniete=true;" +
                        "if(event.screenX>document.body.clientWidth*0.9)" +
                        "touchpad.typ=TCP_Data.touchedTYPE.SCROLL;" +
                        "else " +
                        "touchpad.typ=TCP_Data.touchedTYPE.NORMAL;" +
                        "return false;"
                        + "};"
                        + "touchpad.onTouchUp=function(event){"

                        + "touchpad.wcisniete=false;"
                        +
                        "if(touchpad.longClicked)" +
                        "{" +
                        "data.mouse = TCP_Data.touchedTYPE.UP;" +
                        "touchpad.longClicked=false;" +
                        "}\n" + "if(event.changedTouches[0].screenX>touchpad.oldX-2&&event.changedTouches[0].screenX<touchpad.oldX+2&&event.changedTouches[0].screenY>touchpad.oldY-2&&event.changedTouches[0].screenY<touchpad.oldY+2)"

                        //+ "if(event.screenX>touchpad.oldX-10)"
                        + "{"
                        + "var data=new TCP_Data();" +
                        "data.mouse = TCP_Data.touchedTYPE.LPM;" +
                        "data.touchpadX = 0;data.touchpadY =0;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "}" +
                        "touchpad.longClicked=false;touchpad.czas=new Date(0);" +
                        "return false;"
                        + "};"
                        + "touchpad.onMouseUp=function(event){\n" +
                        "touchpad.czas=new Date(0);"

                        + "touchpad.wcisniete=false;\n"
                        +
                        "if(touchpad.longClicked)" +
                        "{" +
                        "data.mouse = TCP_Data.touchedTYPE.UP;" +
                        "" +
                        "}" +
                        " if(event.screenX>touchpad.oldX-2&&event.screenX<touchpad.oldX+2&&event.screenY>touchpad.oldY-2&&event.screenY<touchpad.oldY+2)"

                        //+ "if(event.screenX>touchpad.oldX-10)"
                        + "{"
                        + "var data=new TCP_Data();" +
                        "data.mouse = TCP_Data.touchedTYPE.LPM;" +
                        "data.touchpadX = 0;data.touchpadY =0;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "}" +
                        "touchpad.longClicked=false;touchpad.czas=new Date(0);"
                        + "};"
                        + "touchpad.onMouseMove=function(event){" +
                        "touchpad.czas=new Date(0);"
                        + "if(touchpad.wcisniete){"
                        + "var dx = event.screenX - this.mPreviousX;var dy = event.screenY - this.mPreviousY;"
                        + "this.mPreviousX=event.screenX;"
                        + "this.mPreviousY=event.screenY;" +
                        "if((new Date())-touchpad.czas>500&&touchpad.czas!=0)" +
                        "{" +
                        //"touchpad.typ=TCP_Data.touchedTYPE.LONG;" +
                        "touchpad.longClicked=true;" +
                        "touchpad.typ=TCP_Data.touchedTYPE.LONG;" +
                        "}"
                        + "var data=new TCP_Data();data.mouse = touchpad.typ;data.touchpadX = dx;data.touchpadY =dy;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "return false;}};"
                        + "touchpad.onTouchMove=function(event){"
                        + "var dx = event.touches[0].screenX - this.mPreviousX;var dy = event.touches[0].screenY - this.mPreviousY;"
                        + "this.mPreviousX=event.touches[0].screenX;"
                        + "this.mPreviousY=event.touches[0].screenY;" +
                        "if((new Date())-touchpad.czas>500&&touchpad.czas!=0)" +
                        "{" +
                        "touchpad.longClicked=true;" +
                        // "touchpad.typ=TCP_Data.touchedTYPE.LONG;" +
                        "touchpad.czas=new Date(0);" +
                        "touchpad.typ=TCP_Data.touchedTYPE.LONG;" +
                        "}"
                        + "var data=new TCP_Data();data.mouse = touchpad.typ;data.touchpadX = dx;data.touchpadY =dy;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "return false;};\n" +
                        "var klawiatura=new Object();" +
                        "klawiatura.i=0;" +
                        "klawiatura.t=function()\n" +
                        "{"
                        + "var txt=document.getElementsByTagName('textarea')[0];" +
                        "for(;klawiatura.i<txt.value.length;klawiatura.i++)\n" +
                        "{" +
                        "var data=new TCP_Data();data.key = txt.value.charCodeAt(klawiatura.i);\n"
                        + "data.type=TCP_Data.typ.KEYBOARD;send(data);\n"
                       + "}" +
                       // "txt.value='';\n" +
                        "};\n"
                        +"klawiatura.timer=setInterval(klawiatura.t,10);\n"
                        + "function send(data){"
                        + "var socket=new XMLHttpRequest();"
                        + "var czas=new Date();"
                        + "data.czas=czas.getTime();"
                        //+ "alert(data);"
                        //+ "alert('?'+JSON.stringify(data));"
                        + "socket.onload=function(){\n"
                        + "if(socket.responseText=='ok')\n"
                        + "{czasPrzesylu=czas;\n" +
                        "jakosc[jakosc.length]=(new Date()).getTime()-data.czas;\n"
                        + "polaczono=true;" +
                        "document.getElementById('stanPol').textContent='Połączono';" +
                        "document.getElementById('jakosc').textContent=Math.ceil(jakoscLicz());"
                        + "}};\n"
                        + "socket.open('get', '?'+JSON.stringify(data), false)\n"
                        + "socket.send();\n"
                        + "}"
                        + "function TCP_Data(){this.touchpadX=0;this.touchpadY=0;this.button=0;this.type=0;this.mouse=0;};"
                        + "TCP_Data.touchedTYPE={NORMAL:0, LONG:1, UP:2, SCROLL:3, LPM:4, PPM:5};"
                        + "TCP_Data.typ={GAMEPAD:0, PILOT:1, KEYBOARD:2, TOUCHPAD:3};"
                        + "TCP_Data.pilotButton={OFF:0, MUSIC:1, MULTIMEDIA:2, PLAYPAUSE:3, PERV:4, NEXT:5, STOP:6, EXIT:7, BACK:8, VOLDOWN:9, VOLUP:10, MUTE:11,UP:12, DOWN:13, RIGHT:14, LEFT:15, RETTURN:16, REWIND:17, FORWARD:18};"
                        + "function kartaPokaz(id){"
                        //+ "document.getElementById('gamepad').style.display="
                        + "document.getElementById('pulpit').style.display=document.getElementById('pilot').style.display=document.getElementById('klawiatura').style.display=document.getElementById('touchpad').style.display='none';"
                        + "document.getElementById('menu').style.top='90%';document.getElementById('menur').style.top='100%';" +
                        "document.getElementById(id).style.display='block';"
                        + "}"
                        + "var timer=setInterval(function(){"
                        + "if((new Date()).getTime()-(czasPrzesylu).getTime()>3000&&polaczono)"
                        + "{"
                        + "polaczono=false;" +
                        "jakosc=[];" +
                        "document.getElementById('jakosc').textContent='Brak Danych';" +
                        "document.getElementById('stanPol').textContent='Rozłączono!';"
                        //+ "alert('Rozłączono!');"
                        + "}"
                        + "if((new Date()).getTime()-czasPrzesylu.getTime()>1000)"
                        + "send(new Object());"
                        + "},250);" +
                        "function mapa(skala)" +
                        "{" +
                        "document.getElementById('przycMapa').innerHTML='" +
                        "<area  shape=\"rect\" coords=\"0,0,'+(skala*160)+','+(skala*160)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.OFF);\"/>" +
                        "<area  shape=\"rect\" coords=\"'+(skala*560)+',0,'+(skala*720)+','+(skala*160)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.MUTE);\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*120)+','+(skala*160)+','+(skala*360)+','+(skala*390)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.PLAYPAUSE);\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*360)+','+(skala*160)+','+(skala*600)+','+(skala*390)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.STOP);\"/>" +
                        "<area  shape=\"rect\" coords=\"0,'+(skala*1040)+','+(skala*240)+','+(skala*1280)+'\" onclick=\"\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*240)+','+(skala*1040)+','+(skala*480)+','+(skala*1280)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.MULTIMEDIA);\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*480)+','+(skala*1040)+','+(skala*720)+','+(skala*1280)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.MUSIC);\"/>" +

                        "<area shape=\"poly\" coords=\"'+(skala*40)+','+(skala*390)+','+(skala*320)+','+(skala*390)+','+(skala*40)+','+(skala*670)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.VOLDOWN); return false;\" onmouseup=\"pilot.trzymaj=null; return false;\" ontouchstart=\"pilot.clickTrzymaj(TCP_Data.pilotButton.VOLDOWN); return false;\" ontouchend=\"pilot.trzymaj=null; return false;\" />" +
                        "<area shape=\"poly\" coords=\"'+(skala*670)+','+(skala*390)+','+(skala*400)+','+(skala*390)+','+(skala*670)+','+(skala*670)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.VOLUP); return false;\" onmouseup=\"pilot.trzymaj=null\"  ontouchstart=\"pilot.clickTrzymaj(TCP_Data.pilotButton.VOLUP); return false;\" ontouchend=\"pilot.trzymaj=null; return false;\" />" +
                        "<area shape=\"poly\" coords=\"'+(skala*40)+','+(skala*1020)+','+(skala*320)+','+(skala*1020)+','+(skala*40)+','+(skala*730)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.PERV,TCP_Data.pilotButton.REWIND); return false;\"  ontouchstart=\"pilot.clickTrzymaj(null,TCP_Data.pilotButton.REWIND); return false;\"  ontouchend=\"pilot.trzymaj=null;if(pilot.trzymajLicz<0)pilot.click(TCP_Data.pilotButton.PERV); return false;\"  onmouseup=\"pilot.trzymaj=null;if(pilot.trzymajLicz<0)pilot.click(TCP_Data.pilotButton.PERV);\" />" +
                        "<area shape=\"poly\" coords=\"'+(skala*670)+','+(skala*1020)+','+(skala*400)+','+(skala*1020)+','+(skala*670)+','+(skala*730)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.NEXT, TCP_Data.pilotButton.FORWARD); return false;\" ontouchstart=\"pilot.clickTrzymaj(null, TCP_Data.pilotButton.FORWARD); return false;\"  ontouchend=\"pilot.trzymaj=null;if(pilot.trzymajLicz<0)pilot.click(TCP_Data.pilotButton.NEXT); return false;\"  onmouseup=\"pilot.trzymaj=null;if(pilot.trzymajLicz<0)pilot.click(TCP_Data.pilotButton.NEXT);\" />" +

                        "<area shape=\"poly\" coords=\"'+(skala*180)+','+(skala*530)+','+(skala*320)+','+(skala*390)+','+(skala*400)+','+(skala*390)+','+(skala*540)+','+(skala*530)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.UP);\" onmouseup=\"pilot.trzymaj=null\"  ontouchstart=\"pilot.clickTrzymaj(TCP_Data.pilotButton.UP); return false;\" ontouchend=\"pilot.trzymaj=null; return false;\" />" +
                        "<area shape=\"poly\" coords=\"'+(skala*540)+','+(skala*870)+','+(skala*670)+','+(skala*730)+','+(skala*670)+','+(skala*670)+','+(skala*540)+','+(skala*530)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.RIGHT);\" onmouseup=\"pilot.trzymaj=null\" ontouchstart=\"pilot.clickTrzymaj(TCP_Data.pilotButton.RIGHT); return false;\" ontouchend=\"pilot.trzymaj=null; return false;\"  />" +
                        "<area shape=\"poly\" coords=\"'+(skala*540)+','+(skala*870)+','+(skala*400)+','+(skala*1010)+','+(skala*320)+','+(skala*1010)+','+(skala*180)+','+(skala*870)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.DOWN);\" onmouseup=\"pilot.trzymaj=null\" ontouchstart=\"pilot.clickTrzymaj(TCP_Data.pilotButton.DOWN); return false;\" ontouchend=\"pilot.trzymaj=null; return false;\"  />" +
                        "<area shape=\"poly\" coords=\"'+(skala*180)+','+(skala*530)+','+(skala*50)+','+(skala*660)+','+(skala*50)+','+(skala*730)+','+(skala*180)+','+(skala*870)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.LEFT);\" onmouseup=\"pilot.trzymaj=null\" ontouchstart=\"pilot.clickTrzymaj(TCP_Data.pilotButton.LEFT); return false;\" ontouchend=\"pilot.trzymaj=null; return false;\"  />" +
                        "<area shape=\"poly\" coords=\"'+(skala*180)+','+(skala*870)+','+(skala*180)+','+(skala*1010)+','+(skala*670)+','+(skala*1010)+','+(skala*670)+','+(skala*870)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.OK);\"/>" +
                        "'" +
                        "}" +
                        "function jakoscLicz()\n" +
                        "{\n" +
                        "if(jakosc.length>4)\n" +
                        "return (jakosc[jakosc.length-5]+jakosc[jakosc.length-4]+jakosc[jakosc.length-3]*2+jakosc[jakosc.length-2]*2+jakosc[jakosc.length-1]*3)/9;\n" +
                        "else if(jakosc.length==4)\n" +
                        "return (jakosc[jakosc.length-4]+jakosc[jakosc.length-3]*2+jakosc[jakosc.length-2]*2+jakosc[jakosc.length-1]*3)/8;\n" +
                        "else if(jakosc.length==3)\n" +
                        "return (jakosc[jakosc.length-3]*2+jakosc[jakosc.length-2]*2+jakosc[jakosc.length-1]*3)/7;\n" +
                        "else if(jakosc.length==2)\n" +
                        "return (jakosc[jakosc.length-2]*2+jakosc[jakosc.length-1]*3)/5;\n" +
                        "else\n" +
                        "return jakosc[jakosc.length-1];\n" +
                        "}" +
                        "" +
                        "var pulpit=new Object();" +
                        "pulpit.width="+(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()+";\n" +
                        "pulpit.height="+(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()+";\n" +
                        "pulpit.jakosc=1;" +
                        "pulpit.laduj=function(thi){\n" +
                        "if(thi.parentNode.clientHeight!=0)" +
                        "if(pulpit.jakosc==0)\n" +
                        "thi.src='/"+Program.ustawienia.haslo+"/pulpit/'+Math.floor(pulpit.x/pulpit.zoom)+'/'+Math.floor(pulpit.y/pulpit.zoom)+'/'+Math.floor(pulpit.width/pulpit.zoom)+'/'+Math.floor(pulpit.height/pulpit.zoom)+'/'+thi.parentNode.clientWidth+'/'+thi.parentNode.clientHeight+'/BMP/'+(new Date()).getTime();\n" +
                        "else \n" +
                        "thi.src='/"+Program.ustawienia.haslo+"/pulpit/'+Math.floor(pulpit.x/pulpit.zoom)+'/'+Math.floor(pulpit.y/pulpit.zoom)+'/'+Math.floor(pulpit.width/pulpit.zoom)+'/'+Math.floor(pulpit.height/pulpit.zoom)+'/'+(Math.floor(thi.parentNode.clientWidth/pulpit.jakosc))+'/'+(Math.floor(thi.parentNode.clientHeight/pulpit.jakosc))+'/JPEG/'+(new Date()).getTime();\n" +
                        "pulpit.xs=pulpit.x;" +
                        "pulpit.ys=pulpit.y;" +
                        "thi.style.width='100%';" +
                        "thi.style.height='100%';" +
                        "thi.style.marginLeft='0';" +
                        "thi.style.marginTop='0';" +
                        "};\n" +
                        "pulpit.zoom=1;" +
                        "pulpit.x=pulpit.y=pulpit.xs=pulpit.ys=0;" +
                        "pulpit.punkty=[];" +
                        "pulpit.punktyP=[];" +
                        "pulpit.move=function(eve){\n" +
                        "if(eve.touches.length>=2)" +
                        "{" +
                        "if(pulpit.punkty.length>=2)\n" +
                        "{" +
                        "pulpit.zoom=pulpit.zoom*Math.sqrt((eve.touches[0].screenX-eve.touches[1].screenX)*(eve.touches[0].screenX-eve.touches[1].screenX)+(eve.touches[0].screenY-eve.touches[1].screenY)*(eve.touches[0].screenY-eve.touches[1].screenY))/Math.sqrt((pulpit.punkty[0].screenX-pulpit.punkty[1].screenX)*(pulpit.punkty[0].screenX-pulpit.punkty[1].screenX)+(pulpit.punkty[0].screenY-pulpit.punkty[1].screenY)*(pulpit.punkty[0].screenY-pulpit.punkty[1].screenY));\n" +
                        "}" +
                        "}\n" +
                        "else if(eve.touches.length==1)\n"   +
                        "{" +
                        "if(pulpit.punkty.length==1)\n" +
                        "{" +
                        "pulpit.x+=pulpit.punkty[0].screenX-eve.touches[0].screenX;\n" +
                        "pulpit.y+=pulpit.punkty[0].screenY-eve.touches[0].screenY;\n" +
                        "document.getElementById(\"pulpit\").children[0].style.marginLeft=(parseInt(document.getElementById(\"pulpit\").children[0].style.marginLeft)+(-pulpit.punkty[0].screenX+eve.touches[0].screenX)/pulpit.height*document.getElementById(\"pulpit\").clientHeight)+'px';" +
                        "document.getElementById(\"pulpit\").children[1].style.marginLeft=(parseInt(document.getElementById(\"pulpit\").children[1].style.marginLeft)+(-pulpit.punkty[0].screenX+eve.touches[0].screenX)/pulpit.height*document.getElementById(\"pulpit\").clientHeight)+'px';" +
                        "document.getElementById(\"pulpit\").children[0].style.marginTop=(parseInt(document.getElementById(\"pulpit\").children[0].style.marginTop)+(-pulpit.punkty[0].screenY+eve.touches[0].screenY)/pulpit.width*document.getElementById(\"pulpit\").clientWidth)+'px';" +
                        "document.getElementById(\"pulpit\").children[1].style.marginTop=(parseInt(document.getElementById(\"pulpit\").children[1].style.marginTop)+(-pulpit.punkty[0].screenY+eve.touches[0].screenY)/pulpit.width*document.getElementById(\"pulpit\").clientWidth)+'px';" +
                        "}" +
                        "}\n" +
                        "};\n" +
                        "pulpit.moveP=function(eve){" +
                        "if(eve.pointerType=='touch')\n" +
                        "{" +
                        "" +
                        "pulpit.moveE(eve)" +
                        "}" +
                        "};\n" +
                        "pulpit.moveM=function(eve){" +
                        "};" +
                        "pulpit.moveE=function(eve){" +
                        "if(pulpit.punkty.length==1)\n" +
                        "{\n" +
                        "pulpit.x+=pulpit.punkty[0].screenX-eve.screenX;\n" +
                        "pulpit.y+=pulpit.punkty[0].screenY-eve.screenY;\n" +
                        "document.getElementById(\"pulpit\").children[0].style.marginLeft=(parseInt(document.getElementById(\"pulpit\").children[0].style.marginLeft)+(-pulpit.punkty[0].screenX+eve.screenX)/pulpit.height*document.getElementById(\"pulpit\").clientHeight)+'px';" +
                        "document.getElementById(\"pulpit\").children[1].style.marginLeft=(parseInt(document.getElementById(\"pulpit\").children[1].style.marginLeft)+(-pulpit.punkty[0].screenX+eve.screenX)/pulpit.height*document.getElementById(\"pulpit\").clientHeight)+'px';" +
                        "document.getElementById(\"pulpit\").children[0].style.marginTop=(parseInt(document.getElementById(\"pulpit\").children[0].style.marginTop)+(-pulpit.punkty[0].screenY+eve.screenY)/pulpit.width*document.getElementById(\"pulpit\").clientWidth)+'px';" +
                        "document.getElementById(\"pulpit\").children[1].style.marginTop=(parseInt(document.getElementById(\"pulpit\").children[1].style.marginTop)+(-pulpit.punkty[0].screenY+eve.screenY)/pulpit.width*document.getElementById(\"pulpit\").clientWidth)+'px';" +
                        "}" +
                        "pulpit.punkty=[{screenX:eve.screenX,screenY:eve.screenY}];" +
                        "};\n" +
                        "pulpit.click=function(eve,thi)\n" +
                        "{" +
                         "var data=new TCP_Data();" +
                        "data.mouse = TCP_Data.touchedTYPE.LPM;" +
                        "data.touchpadX = Math.floor(((eve.clientX-thi.clientLeft)*pulpit.width/thi.clientWidth+pulpit.x)/pulpit.zoom);\n" +
                        "data.touchpadY = Math.floor(((eve.clientY-thi.clientTop)*pulpit.height/thi.clientHeight+pulpit.y)/pulpit.zoom);\n"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);\n"
                        +"}\n" +
                        "document.getElementById('pulpit').addEventListener(\"MSGestureChange\", onDivGestureChange, false);\n" +
                        "document.getElementById('pulpit').addEventListener(\"GestureChange\", onDivGestureChange, false);\n" +
                        "\n" +
                        "      function onDivGestureChange(e) {\n" +
                        "        pulpit.zoom=pulpit.zoom*e.scale;"+
                        "      }\n"
                        + "/*]]>*/</script>"
                        + "</head><body onload=\"mapa(document.getElementById('przyciski').clientHeight/1280)\" onresize=\"mapa(document.getElementById('przyciski').clientHeight/1280)\">"
                        //+ "<div class=\"karta\" id=\"gamepad\">Gamepad wkrodce</div>"
                        + "<div class=\"karta\" id=\"pilot\">"
                        + "<img id=\"przyciski\" src=\"" + przyciskiBase64 + "\" usemap=\"#przycMapa\" />" +
                        "<map id=\"przycMapa\" name=\"przycMapa\">" +
                        "</map>"
                        + "</div>"
                        + "<div class=\"karta\" id=\"klawiatura\"><textarea style=\"width:1px;height:1px;opacity:0;\"></textarea></div>"
                        //Tu były problemy ze zdarzeniami
                        //+ "<div class=\"karta\" style=\"display:block\" ontouchmove=\"return touchpad.onTouchMove(event)\" onmousemove=\"return touchpad.onMouseMove(event)\" ontouchdown=\"touchpad.onTouchDown(event)\" onmousedown=\"touchpad.onTouchDown(event)\" ontouchup=\"touchpad.onTouchUp(event)\" onmouseup=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\" onmouseleave=\"touchpad.onTouchUp(event)\" id=\"touchpad\"></div>"
                        //+ "<div class=\"karta\" style=\"display:block\" onmousemove=\"return touchpad.onMouseMove(event)\" onmousedown=\"touchpad.onMouseDown(event)\" onmouseup=\"touchpad.onMouseUp(event)\" onmouseleave=\"touchpad.onMouseUp(event)\"  id=\"touchpad\"></div>"
                        //+ "<div class=\"karta\" style=\"display:block\" ontouchmove=\"return touchpad.onTouchMove(event)\" ontouchstart=\"touchpad.onTouchDown(event)\" ontouchend=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\"  id=\"touchpad\"></div>"
                        + "<div class=\"karta\" style=\"display:block\" onmousemove=\"return touchpad.onMouseMove(event)\" onmousedown=\"touchpad.onMouseDown(event)\" onmouseup=\"touchpad.onMouseUp(event)\" onmouseleave=\"touchpad.onMouseUp(event)\" ontouchmove=\"return touchpad.onTouchMove(event)\" ontouchstart=\"return touchpad.onTouchDown(event)\" ontouchend=\"return touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\" id=\"touchpad\"></div>"
                        + "<div class=\"karta\" id=\"pulpit\" " +
                        "ontouchmove=\"pulpit.move(event);return false;\" ontouchend=\"pulpit.punkty=[]\" ontouchleave=\"pulpit.punkty=[]\" " +
                        "onMSPointerMove=\"pulpit.moveP(event);return false;\" onMSPointerup=\"pulpit.punkty=[]\" onMSPointerleave=\"pulpit.punkty=[]\" " +
                        "onPointerMove=\"pulpit.moveP(event);return false;\" onPointerup=\"pulpit.punkty=[]\" onPointerleave=\"pulpit.punkty=[]\" " +
                        //"onmousemove=\"pulpit.moveM(event);return false;\" onmouserup=\"pulpit.punkty=[]\" onmouseleave=\"pulpit.punkty=[]\" " +
                        "\">" +
                        "<img src=\"/"+Program.ustawienia.haslo+"/pulpit/0/0/"+ (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()+"/"+(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()+"/"+((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/16)+"/"+((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/16)+"/JPEG/\" onload=\"this.style.zIndex=2;this.parentNode.children[1].style.zIndex=1;pulpit.laduj(this);\" onclick=\"pulpit.click(event,this);return false;\" alt=\"Błąd\" />" +
                        "<img src=\"/"+Program.ustawienia.haslo+"/pulpit/0/0/"+ (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()+"/"+(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()+"/"+((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/16)+"/"+((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/16)+"/JPEG/\" onload=\"this.style.zIndex=2;this.parentNode.children[0].style.zIndex=1;pulpit.laduj(this);\" onclick=\"pulpit.click(event,this);return false;\" alt=\"\" />" +
                        "</div>"
                        + "<ul id=\"menu\">" +
                        //"<li onclick='kartaPokaz(\"gamepad\")'><img title=\"gamepad\" src=\""+gamepadBase64+"\"/></li>" +
                        "<li onclick=\"kartaPokaz(\'pilot\');mapa(document.getElementById('przyciski').clientHeight/1280);\"><img alt=\"pilot\" src=\"" + pilotBase64 + "\"/></li><li onclick='kartaPokaz(\"klawiatura\");document.getElementsByTagName(\"textarea\")[0].focus()'><img alt=\"klawiatura\" src=\"" + klawiaturaBase64 + "\"/></li><li onclick='kartaPokaz(\"touchpad\")'><img alt=\"touchpad\" src=\"" + touchpadBase64 + "\"/></li><li onclick='kartaPokaz(\"pulpit\");pulpit.laduj(document.getElementById(\"pulpit\").children[0]);pulpit.laduj(document.getElementById(\"pulpit\").children[1])'><img alt=\"Pulpit\" src=\"" + touchpadBase64 + "\"/></li><li onclick=\"if(document.getElementById('menu').style.top=='5%'){document.getElementById('menu').style.top='90%';document.getElementById('menur').style.top='100%';}else{document.getElementById('menu').style.top='5%';document.getElementById('menur').style.top='15%';}\"><img style=\"float:right\" alt=\"menu\" src=\"" + menuBase64 + "\"/></li>" +
                        "</ul><div id=\"menur\"><h2>Informacje</h2>" +
                        "<div class=\"podmenu\">" +
                        "POŁĄCZENIE<br/>" +
                        "Stan:<span id=\"stanPol\">Połączono</span><br/>" +
                        "Jakość:<span id=\"jakosc\">Brak Danych</span><br/>" +
                        "Host: " + java.net.InetAddress.getLocalHost().getHostName() + "<br/>" +
                        "CODER<br />" +
                        "-FranQy<br />" +
                        "-Matrix0123456789<br />" +
                        "DESIGNERS<br/>" +
                        "-FranQy<br/>" +
                        "-Wieczur" +
                        "</div><h2>Jakość obrazu</h2><div class=\"podmenu\">" +
                        "<button onclick='pulpit.jakosc=3'>Niska</button>" +
                        "<button onclick='pulpit.jakosc=2'>Średnia</button>" +
                        "<button onclick='pulpit.jakosc=1'>Wysoka</button>" +
                        "<button onclick='pulpit.jakosc=0'>Ultra</button>" +
                        "</div>" +
                        "</div></body></html>";

            } else if (wyj.indexOf("/ ") == 0) {
                String typ;
                if (wyj.contains("mobile") || wyj.contains("touch") || wyj.contains("android"))
                    typ = "number";
                else
                    typ = "text";
                wysylanie = "HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
                        + "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
                        + "<meta name=\"viewport\" content=\"width=240, initial-scale=1, user-scalable=no\" />"
                        + "</head>"
                        + "<body style=\"color:white;background:#26211b;\">"
                        + "<form onsubmit=\"document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\"><label>wpisz kod<input type=\"" + typ + "\"/></label><input type=\"submit\" value=\"ok\"/></form>"
                        + "</body></html>";

            } else {
                String typ;
                if (wyj.contains("mobile") || wyj.contains("touch") || wyj.contains("android"))
                    typ = "number";
                else
                    typ = "text";
                wysylanie = "HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
                        + "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
                        + "<meta name=\"viewport\" content=\"width=240, initial-scale=1, user-scalable=no\" />"
                        + "</head>"
                        + "<body style=\"color:white;background:#26211b;\">"
                        + "<strong>Błędny kod!</strong>"
                        + "<form onsubmit=\"document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\"><label>wpisz kod<input type=\"" + typ + "\"/></label><input type=\"submit\" value=\"ok\"/></form>"
                        + "</body></html>";
            }
            os.write(wysylanie.getBytes());
            os.close();
            is.close();
        }
        //else
        return null;
    }

    static byte id(String wyj) {
        //spradzanie po cookie czy jest już
        byte i = 0;
        if (wyj.contains("Cookie: id=")) {
            try{i = (byte) Integer.parseInt(wyj.substring(wyj.indexOf("Cookie: id=") + 11, wyj.indexOf('\r', wyj.indexOf("Cookie: id="))));
            }
            catch(Exception e){
                for (; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                {
                    if (Polaczenie.polaczeniaHttp[i] == null) {
                        break;
                    }
                }
            }
        } else
            for (; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
            {
                if (Polaczenie.polaczeniaHttp[i] == null) {
                    break;
                }
            }
        if (Polaczenie.polaczeniaHttp[i] == null) {
            Polaczenie.polaczeniaHttp[i] = new HttpPolaczenie();
            Polaczenie.polaczeniaHttp[i].UserAgent = new UserAgent(wyj.substring(wyj.indexOf("User-Agent:") + 11, wyj.indexOf('\r', wyj.indexOf("User-Agent:"))));
        }
        else
        Polaczenie.polaczeniaHttp[i].czas=new Date();
        return i;
    }
}
