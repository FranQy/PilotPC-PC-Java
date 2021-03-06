import com.jaebe.PilotPC.TCP_Data;
import com.jaebe.PilotPC.TCP_Data.pilotButton;
import com.jaebe.PilotPC.TCP_Data.touchedTYPE;
import com.jaebe.PilotPC.TCP_Data.typ;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * Obsługa protokołu HTTP
 */
public class HTTP {
    public static TCP_Data[] doWykonania = new TCP_Data[16];
    public static int liczdodatki = 0;

    public static TCP_Data polaczenie(InputStream is, Socket soc, String wyj) throws IOException {

        try { //zapobieganie atakom BruceForce
            while (System.currentTimeMillis() < Polaczenie.czasBlokadyHasla)
                Thread.sleep(Polaczenie.czasBlokadyHasla - System.currentTimeMillis());
        } catch (InterruptedException e) {
            Debugowanie.Błąd(e);
        }
        Jezyk.jezyki lang = Program.ustawienia.jezyk; //ustawienie języka
        int AcceptLanguage = wyj.indexOf("Accept-Language");
        int[] jezykiHttp = {wyj.indexOf("pl", AcceptLanguage), wyj.indexOf("en", AcceptLanguage), wyj.indexOf("ru", AcceptLanguage)};
        int jezykHttp = max(jezykiHttp);
        if (jezykHttp != -1)
            lang = Jezyk.jezyki.values()[jezykHttp];

        if (wyj.indexOf("/") == 0 && wyj.indexOf("?") > 0) {

            OutputStream os = soc.getOutputStream();

            byte i = id(wyj, false);
            String wysylanie;
            TCP_Data ret = new TCP_Data();
            if (Polaczenie.polaczeniaHttp[i].zablokowane)
                wysylanie = "HTTP/1.1 403	Forbidden\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.ZostalesRozlaczony.ordinal()];
            else if (wyj.indexOf("/" + Program.ustawienia.haslo) == 0) {//jeśli hasło poprawne
                wysylanie = "HTTP/1.1 200 OK\r\nSet-Cookie: id=" + i + "; path=/\r\n\r\nok";
                String klasaString = wyj.substring(wyj.indexOf("?") + 1, wyj.indexOf(" HTTP"));
                klasaString = klasaString.replaceAll("%22", "\"");
                JSONObject klasaObjekt = new JSONObject(klasaString);

                try {
                    ret.touchpadX = klasaObjekt.getInt("touchpadX");
                    ret.touchpadY = klasaObjekt.getInt("touchpadY");
                    ret.type = typ.values()[klasaObjekt.getInt("type")];
                    ret.mouse = touchedTYPE.values()[klasaObjekt.getInt("mouse")];
                    try {
                        ret.key = klasaObjekt.getInt("key");
                    } catch (Exception e) {
                    }

                    ret.button = pilotButton.values()[klasaObjekt.getInt("button")];

                } catch (Exception e) {
                    ret = null;
                }

            } else {

                wysylanie = "HTTP/1.1 403	Forbidden\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.KodBledny.ordinal()];

                Polaczenie.czasBlokadyHasla = System.currentTimeMillis() + 500;
            }
            os.write(wysylanie.getBytes());
            os.close();
            is.close();

            return ret;
        } else if (wyj.indexOf("/") == 0) {
            OutputStream os = soc.getOutputStream();
            byte i = id(wyj, wyj.indexOf("/dodatek") == 0);
            String wysylanie;
            if (Polaczenie.polaczeniaHttp[i].zablokowane)
                wysylanie = "HTTP/1.1 403	Forbidden\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.ZostalesRozlaczony.ordinal()];
            else if (wyj.indexOf("/log") == 0) {
                System.out.println(wyj.substring(4, wyj.indexOf('\n') - 4));

                wysylanie = "HTTP/1.1 200 OK\r\n\r\n\r\n";
            } else if (wyj.indexOf("/dodatek") == 0) {
                if (liczdodatki > 4) {
                    is.close();
                    os.close();
                    soc.close();
                    return null;
                }
                liczdodatki++;
                if (soc.getInetAddress().isLoopbackAddress()) {
                    wysylanie = "HTTP/1.1 200 OK\n" +
                            "Server: PilotPC\n" +
                            "Set-Cookie: id=" + i + "; path=/\n" +
                            "Content-Type: application/json; charset=UTF-8\n" +
                            "\n" +
                            "{\"polecenia\":[";
                    boolean przecinek = false;
                    for (byte x = 0; x < doWykonania.length; x++) {
                        if (doWykonania[x] != null) {
                            if (doWykonania[x].type == typ.PILOT && doWykonania[x].button != null) {
                                if (przecinek)
                                    wysylanie += ",";
                                wysylanie += "{\"akcja\":" + doWykonania[x].button.ordinal() + "}";
                                przecinek = true;
                            }
                            doWykonania[x] = null;
                        }

                    }
                    wysylanie += "]}";
                } else {
                    wysylanie = "HTTP/1.1 403 Forbidden\n" +
                            "Server: PilotPC\n" +
                            "Content-Type: application/json; charset=UTF-8\n" +
                            "Set-Cookie: id=" + i + "; path=/\n" +
                            "\n" +
                            "{[\"Połączenie tylko przez localhost\"]}";
                }
                liczdodatki--;
            } else if (wyj.indexOf("/" + Program.ustawienia.haslo + "/pulpit/") == 0) {
                wysylanie = Pulpit.HTTP(wyj, i, os);
            } else if (wyj.indexOf("/" + Program.ustawienia.haslo) == 0) {
                String gamepadBase64;   //ładuje obrazki i zamienia na Base64
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
                String plusBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/plus.png");
                    byte[] plusBytes = new byte[2000];
                    input.read(plusBytes);
                    plusBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(plusBytes);
                } catch (IOException e) {
                    plusBase64 = "";
                }
                String minusBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/minus.png");
                    byte[] minusBytes = new byte[2000];
                    input.read(minusBytes);
                    minusBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(minusBytes);
                } catch (IOException e) {
                    minusBase64 = "";
                }

                String pulpitBase64;
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream input = classLoader.getResourceAsStream(
                            "resources/pulpit.png");
                    byte[] pulpitBytes = new byte[10000];
                    input.read(pulpitBytes);
                    pulpitBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(pulpitBytes);
                } catch (IOException e) {
                    pulpitBase64 = "";
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
                //String overflow = "auto";
              //  if (wyj.toLowerCase().contains("android")||wyj.toLowerCase().contains("phone")||wyj.toLowerCase().contains("mobile"))
                String overflow = "scroll";
                wysylanie = "HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
                        + "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
                        + "<meta name=\"viewport\" content=\"width=220px, initial-scale=1, user-scalable=no\" />"
                        + "<style>"
                      //  + "*{-ms-touch-action: none;touch-action: none;}"
                        +"a img{border:none;}"
                        + "body{font-family:\"Segoe UI Light\",\"Segoe UI\",arial;overflow:" + overflow + ";background:#26211b;color:white;font-style:non-sherif;}"
                        + "#menu{z-index:10;transition:all 300ms;-webkit-transition:all 300ms;height:10%;position: absolute;top: 90%;background: #2e2e2e;margin: 0;left: 0;width: 100%;text-align: center;margin:0;padding:0;}"
                        + "#menu li{display:inline;}" +
                        ".karta{position:absolute;left:0;top:0;width:100%;bottom:10%;display:none;}"
                        + "#menu li img{height:100%;padding:0 2%;}" +
                        "#menur{z-index:10;background:#2e2e2e;text-align:left;height:85%;width:100%;position:absolute;top:100%;transition:all 300ms;-webkit-transition:all 300ms;left:0;padding-left:5px;}" +
                        "#menur h2{margin:0;}" +
                        "#menur .podmenu{background:#5a555a;padding:5px 0 5px 5px;margin:5px 0 5px -5px;}"
                        + "#pilot #przyciski{height:100%;margin: auto; display: block;}" +
                        "#pulpit img{width:100%;height:100%;position:absolute;}" +
                        "#zoom{position:absolute;bottom:12%;right:2%;width:20% !important;height:10% !important;min-width:1.6cm;min-height:0.8cm;z-index:5;}" +
                        "#powieksz, #pomniejsz{width:50% !important;height:100% !important;position:static !important;}" +
                        "#klawiatura div{text-align:center; color:white;}" +
                        "#klawiatura button{border:none;width:10%;height:20%;\n" +
                        "vertical-align: bottom;}" +
                        "#klawiatura .nieparz,#klawiatura .nieparz button{background:#555;}" +
                        "#klawiatura .parz,#klawiatura .parz button{background:#2e2e2e;}" +
                        "#klawiatura button.gr{width:15%;}" +
                        "#klawiatura button.spacja{background:#888888;width:40%}" +
                        "#klawiatura button.spacjaGrube{width:55%}" +
                        "#klawiatura button.nor{color:#bfbfbf;}" +
                        "#klawiatura button.gr{color:#bfbfbf;}" +
                        "#klawiaturaCyfry,#klawiaturaLiteryAlt, #klawiaturaSpecjalne{display:none;}" +

                        "</style>"
                        + "<script>/*<![CDATA[*/\n" +
                        "function windowScrollY(){" +
                        "if(window.scrollY!=undefined)\n" +
                        "\t\t\t\t\treturn window.scrollY\n" +
                        "\t\t\t\telse\n" +
                        "\t\t\t\t\treturn document.documentElement.scrollTop" +
                        "" +
                        "" +
                        "}" +
                        "" +
                        "var wstecz=0;\n" +
                        "var aktywny=true;" +
                        "var hash=setInterval(function(){\n" +
                        "if(wstecz>0&&windowScrollY()==0&&location.hash!='') \n" +
                        "{" +
                        "location.hash='';" +
                        "wstecz=0;" +
                        "}\n" + "" +
                        "else if(wstecz==0&&windowScrollY()>0&&location.hash=='') \n" +
                        "{" +
                        "location.hash='ustawienia';" +
                        "wstecz=1;" +
                        "}\n" +
                        "else if(wstecz>0&&windowScrollY()>0&&location.hash=='')\n" +
                        "{wstecz=0;" +
                        "window.scroll(0,0);" +
                        "}" +

                        "else if(wstecz==0&&location.hash=='#ustawienia')\n" +
                        "{" +
                        "window.scroll(0,document.body.clientHeight*0.85);" +
                        "wstecz=1;" +
                        "}" +
                        "}" +
                        "" +
                        ",100);\n"
                        + "var czasPrzesylu=new Date();"
                        + "var czasWysylu=new Date();"
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
                        + "send(data);" +
                        "if(window.navigator.vibrate)" +
                        " window.navigator.vibrate(100);"
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
                        + " var touchpad=new Object();touchpad.czas=new Date(0);touchpad.wcisniete=false;touchpad.mPreviousX=0; touchpad.mPreviousY=0;touchpad.oldX =0;touchpad.oldY=0;touchpad.returnState = true;"
                        + "touchpad.onTouchDown=function(event){" +
                        "setInterval(function(){if((new Date())-touchpad.czas>450){" +

                        "var data=new TCP_Data();" +
                        "data.mouse = TCP_Data.touchedTYPE.LONG;" +
                        "data.touchpadX = 0;data.touchpadY =0;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);" +
                        "}},500);"
                        + "touchpad.czas=new Date();" +
                        "touchpad.mPreviousX=touchpad.oldX=event.touches[0].screenX;"
                        + "touchpad.mPreviousY=touchpad.oldY=event.touches[0].screenY;"
                        + "touchpad.wcisniete=true;\n" +
                        //"document.getElementById('aaa').innerHTML=event.touches[0].screenX+'>'+document.body.clientWidth*0.9;" +
                        "if(event.touches[0].screenX>document.body.clientWidth*0.9)\n" +
                        "touchpad.typ=TCP_Data.touchedTYPE.SCROLL;\n" +
                        "else " +
                        "touchpad.typ=TCP_Data.touchedTYPE.NORMAL;\n" +
                        "return false;"
                        + "};"
                        + "touchpad.onMouseDown=function(event){" +

                        "touchpad.czas=new Date();"
                        + "touchpad.mPreviousX=touchpad.oldX=event.screenX;"
                        + "touchpad.mPreviousY=touchpad.oldY=event.screenY;"
                        + "touchpad.wcisniete=true;\n" +
                        "if(event.screenX>document.body.clientWidth*0.9)\n" +
                        "touchpad.typ=TCP_Data.touchedTYPE.SCROLL;\n" +
                        "else " +
                        "touchpad.typ=TCP_Data.touchedTYPE.NORMAL;\n" +
                        "return false;"
                        + "};"
                        + "touchpad.onTouchUp=function(event){"

                        + "touchpad.wcisniete=false;"

                        + "if(event.changedTouches[0].screenX>touchpad.oldX-2&&event.changedTouches[0].screenX<touchpad.oldX+2&&event.changedTouches[0].screenY>touchpad.oldY-2&&event.changedTouches[0].screenY<touchpad.oldY+2)"

                        //+ "if(event.screenX>touchpad.oldX-10)"
                        + "{"
                        + "var data=new TCP_Data();" +
                        "data.mouse = TCP_Data.touchedTYPE.LPM;" +
                        "data.touchpadX = 0;data.touchpadY =0;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "}" +
                        "return false;"
                        + "};"
                        + "touchpad.onMouseUp=function(event){\n" +
                        "touchpad.czas=new Date();"

                        + "touchpad.wcisniete=false;\n"
                        +
                        " if(event.screenX>touchpad.oldX-2&&event.screenX<touchpad.oldX+2&&event.screenY>touchpad.oldY-2&&event.screenY<touchpad.oldY+2)"

                        //+ "if(event.screenX>touchpad.oldX-10)"
                        + "{"
                        + "var data=new TCP_Data();" +
                        "data.mouse = TCP_Data.touchedTYPE.LPM;" +
                        "data.touchpadX = 0;data.touchpadY =0;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "}\n" +
                        "else " +
                        "{" +
                        "var data=new TCP_Data();" +
                        "data.mouse = TCP_Data.touchedTYPE.UP;" +
                        "data.touchpadX = 0;data.touchpadY =0;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "}"
                        + "};"
                        + "touchpad.onMouseMove=function(event){" +
                        "touchpad.czas=new Date();"
                        + "if(touchpad.wcisniete){"
                        + "var dx = event.screenX - this.mPreviousX;var dy = event.screenY - this.mPreviousY;"
                        + "this.mPreviousX=event.screenX;"
                        + "this.mPreviousY=event.screenY;" +
                        "if((new Date())-touchpad.czas>500&&touchpad.czas!=0)" +
                        "{" +
                        //"touchpad.typ=TCP_Data.touchedTYPE.LONG;" +
                        "}"
                        + "var data=new TCP_Data();data.mouse = touchpad.typ;data.touchpadX = dx;data.touchpadY =dy;"
                        +
                        "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "return false;}};"
                        + "touchpad.onTouchMove=function(event){"
                        + "var dx = event.touches[0].screenX - this.mPreviousX;var dy = event.touches[0].screenY - this.mPreviousY;"
                        + "this.mPreviousX=event.touches[0].screenX;"
                        + "this.mPreviousY=event.touches[0].screenY;"
                        + "var data=new TCP_Data();data.mouse = touchpad.typ;data.touchpadX = dx;data.touchpadY =dy;"
                        + "data.type=TCP_Data.typ.TOUCHPAD;send(data);"
                        + "return false;};\n" +
                        "var klawiatura=new Object();" +
                        "klawiatura.i=0;" +
                        "klawiatura.shiftTeraz=false;" +
                        "klawiatura.shift=0;" +
                        "klawiatura.t=function()\n" +
                        "{"
                        + "var txt=document.getElementsByTagName('textarea')[0];" +
                        "for(;txt!=null&&klawiatura.i<txt.value.length;klawiatura.i++)\n" +
                        "{" +
                        "var data=new TCP_Data();data.key = txt.value.charCodeAt(klawiatura.i);\n"
                        + "data.type=TCP_Data.typ.KEYBOARD;send(data);\n"
                        + "}" +
                        // "txt.value='';\n" +
                        "};\n"
                        + "klawiatura.timer=setInterval(klawiatura.t,10);\n" +
                        "klawiatura.char=function(key){" +
                        "" +
                        "var data=new TCP_Data();" +
                        // "if(klawiatura.shiftTeraz||klawiatura.shift!=0)" +
                        //"data.key = key.charCodeAt(0)-32;\n" +
                        // "else " +
                        "data.key = key.charCodeAt(0);\n" +
                        //"if(klawiatura.shift!=2)" +
                        //"klawiatura.shift=0;" +
                        //"document.getElementsByClassName('shift')[0].className='gr shift shift'+klawiatura.shift;" +
                        "data.type=TCP_Data.typ.KEYBOARD;send(data);\n" +
                        "};\n" +
                        "klawiatura.num=function(key){" +
                        "" +
                        "var data=new TCP_Data();data.key = key;\n"
                        + "data.type=TCP_Data.typ.KEYBOARD;send(data);\n" +
                        "};\n"
                        + "function send(data){"
                        //+"document.getElementById('aaa').innerHTML=data.mouse;"

                        + "if(aktywny){var socket=new XMLHttpRequest();"
                        + "var czas=new Date();"
                        + "data.czas=czas.getTime();"
                        //+ "alert(data);"
                        //+ "alert('?'+JSON.stringify(data));"
                        + "socket.onload=function(){\n"
                        + "if(socket.responseText=='ok')\n"
                        + "{czasPrzesylu=czas;\n" +
                        "jakosc[jakosc.length]=(new Date()).getTime()-data.czas;\n"
                        + "polaczono=true;" +
                        "document.getElementById('stanPol').textContent='" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Polaczono.ordinal()] + "';" +
                        "var jakli=Math.ceil(jakoscLicz());" +
                        "if(jakli<15)" +
                        "document.getElementById('jakosc').textContent='idealna';"
                        + "else if(jakli<33)" +
                        "document.getElementById('jakosc').textContent='Bardzo dobra';"
                        + "else if(jakli<66)" +
                        "document.getElementById('jakosc').textContent='dobra';"
                        + "else if(jakli<200)" +
                        "document.getElementById('jakosc').textContent='średnia';"
                        + "else if(jakli<500)" +
                        "document.getElementById('jakosc').textContent='słaba';"
                        + "else " +
                        "document.getElementById('jakosc').textContent='bardzo słaba';" +
                        "document.getElementById('jakosc').attributes.getNamedItem('title').value=jakli+'ms';"
                        + "}" +
                        "else " +
                        "{" +
                        "aktywny=false;" +
                        "alert(socket.responseText);" +
                        "\n" +
                        "}" +
                        "};\n"
                        + "socket.open('get', '?'+JSON.stringify(data), true)\n"
                        + "czasWysylu=new Date();" +
                        "socket.send();\n"
                        + "}}\n"
                        + "function TCP_Data(){this.touchpadX=0;this.touchpadY=0;this.button=0;this.type=0;this.mouse=0;};"
                        + "TCP_Data.touchedTYPE={NORMAL:0, LONG:1, UP:2, SCROLL:3, LPM:4, PPM:5};"
                        + "TCP_Data.typ={GAMEPAD:0, PILOT:1, KEYBOARD:2, TOUCHPAD:3};"
                        + "TCP_Data.pilotButton={OFF:0, MUSIC:1, MULTIMEDIA:2, PLAYPAUSE:3, PERV:4, NEXT:5, STOP:6, EXIT:7, BACK:8, VOLDOWN:9, VOLUP:10, MUTE:11,UP:12, DOWN:13, RIGHT:14, LEFT:15, RETTURN:16, REWIND:17, FORWARD:18};"
                        + "TCP_Data.pilotButton={OFF:0, MUSIC:1, PLAYPAUSE:2, PERV:3, NEXT:4, STOP:5, EXIT:6, BACK:7, VOLDOWN:8, VOLUP:9, MUTE:10," +
                        "        UP:11, DOWN:12, RIGHT:13, LEFT:14, RETTURN:15," +
                        "        REWIND:16," +
                        "        FORWARD:17, PHOTO:18, VIDEO:19};"
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
                        "document.getElementById('jakosc').textContent='" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.BrakDanych.ordinal()] + "';" +
                        "document.getElementById('stanPol').textContent='" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Rozlaczono.ordinal()] + "!';"
                        //+ "alert('Rozłączono!');"
                        + "}"
                        + "if((new Date()).getTime()-czasPrzesylu.getTime()>1000&&(new Date()).getTime()-czasWysylu.getTime()>1000)"
                        + "" +
                        "send(new Object());"
                        + "},250);" +
                        "function mapa(skala)" +
                        "{" +
                        "document.getElementById('przycMapa').innerHTML='" +
                        "<area  shape=\"rect\" coords=\"0,0,'+(skala*160)+','+(skala*160)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.OFF);\"/>" +
                        "<area  shape=\"rect\" coords=\"'+(skala*560)+',0,'+(skala*720)+','+(skala*160)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.MUTE);\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*120)+','+(skala*160)+','+(skala*360)+','+(skala*390)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.PLAYPAUSE);\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*360)+','+(skala*160)+','+(skala*600)+','+(skala*390)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.STOP);\"/>" +
                        "<area  shape=\"rect\" coords=\"0,'+(skala*1040)+','+(skala*240)+','+(skala*1280)+'\" onclick=\"\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*0)+','+(skala*1040)+','+(skala*240)+','+(skala*1280)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.PHOTO);\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*240)+','+(skala*1040)+','+(skala*480)+','+(skala*1280)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.VIDEO);\"/>" +
                        "<area shape=\"rect\" coords=\"'+(skala*480)+','+(skala*1040)+','+(skala*720)+','+(skala*1280)+'\" onclick=\"pilot.click(TCP_Data.pilotButton.MUSIC);\"/>" +

                        "<area shape=\"poly\" coords=\"'+(skala*40)+','+(skala*390)+','+(skala*320)+','+(skala*390)+','+(skala*40)+','+(skala*670)+'\" onmousedown=\"pilot.clickTrzymaj(TCP_Data.pilotButton.VOLDOWN); return false;\" onmouseup=\"pilot.trzymaj=null; return false;\" ontouchstart=\"pilot.clickTrzymaj(TCP_Data.pilotButton.VOLDOWN); return false;\" ontouchend=\"pilot.trzymaj=null; return false;\" onclick=\" return false;\" onmouseclick=\" return false;\" />" +
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
                        "pulpit.width=" + (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() + ";\n" +
                        "pulpit.height=" + (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + ";\n" +
                        "pulpit.jakosc=1;" +
                        "pulpit.laduj=function(thi){\n" +
                        "" +
                        "if(pulpit.zoom<0.5)" +
                        "pulpit.zoom=0.5;" +
                        "var proporcje=pulpit.width*thi.parentNode.clientHeight/pulpit.height/thi.parentNode.clientWidth;" +
                        "if(proporcje>1)" +
                        "{" +
                        "pulpit.zoomX=pulpit.zoom;" +
                        "pulpit.zoomY=pulpit.zoom/proporcje;" +
                        "}\n" +
                        "else" +
                        "{" +
                        "pulpit.zoomY=pulpit.zoom;" +
                        "pulpit.zoomX=pulpit.zoom*proporcje;" +
                        "}" +
                        "if(thi.parentNode.clientHeight!=0)" +
                        "if(pulpit.jakosc==0)\n" +
                        "{" +
                        "var wymWid=thi.parentNode.clientWidth;\n" +
                        "if(wymWid>Math.floor(pulpit.width/pulpit.zoomX))\n wymWid=Math.floor(pulpit.width/pulpit.zoomX);\n" +
                        "var wymHei=thi.parentNode.clientHeight;\n" +
                        "if(wymHei>Math.floor(pulpit.height/pulpit.zoomY))\n wymHei=Math.floor(pulpit.height/pulpit.zoomY);\n" +
                        "thi.src='/" + Program.ustawienia.haslo + "/pulpit/'+Math.floor(pulpit.x/pulpit.zoomX*pulpit.width/thi.parentNode.clientWidth)+'/'+Math.floor(pulpit.y/pulpit.zoomY*pulpit.height/thi.parentNode.clientHeight)+'/'+Math.floor(pulpit.width/pulpit.zoomX)+'/'+Math.floor(pulpit.height/pulpit.zoomY)+'/'+wymWid+'/'+wymHei+'/BMP/'+(new Date()).getTime();\n" +
                        "}" +
                        "else \n" +
                        "{\n" +
                        "var wymWid=(Math.floor(thi.parentNode.clientWidth/pulpit.jakosc));\n" +
                        "if(wymWid>Math.floor(pulpit.width/pulpit.zoomX))\n wymWid=Math.floor(pulpit.width/pulpit.zoomX);\n" +
                        "var wymHei=(Math.floor(thi.parentNode.clientHeight/pulpit.jakosc));\n" +
                        "if(wymHei>Math.floor(pulpit.height/pulpit.zoomY))\n wymHei=Math.floor(pulpit.height/pulpit.zoomY);\n" +
                        "thi.src='/" + Program.ustawienia.haslo + "/pulpit/'+Math.floor(pulpit.x/pulpit.zoomX*pulpit.width/thi.parentNode.clientWidth)+'/'+Math.floor(pulpit.y/pulpit.zoomY*pulpit.height/thi.parentNode.clientHeight)+'/'+Math.floor(pulpit.width/pulpit.zoomX)+'/'+Math.floor(pulpit.height/pulpit.zoomY)+'/'+wymWid+'/'+wymHei+'/JPEG/'+(new Date()).getTime();\n" +
                        "}" +
                        //"thi.style.width=(pulpit.zoom/pulpit.zoomS*100)+'%';" +
                        //"thi.style.height=(pulpit.zoom/pulpit.zoomS*100)+'%';" +
                        "thi.style.height=thi.style.height='100%';" +
                        "thi.style.marginLeft=((pulpit.xs-(pulpit.x)))+'px';" +
                        "thi.style.marginTop=((pulpit.ys-pulpit.y))+'px';" +
                        "pulpit.zoomS=pulpit.zoom;\n" +
                        "pulpit.ml=((pulpit.xs-pulpit.x));\r\n" +
                        "pulpit.mt=((pulpit.ys-pulpit.y));\n" +
                        //"thi.style.marginLeft='0px';" +
                        //"thi.style.marginTop='0px';" +
                        "pulpit.xs=pulpit.x;" +
                        "pulpit.ys=pulpit.y;" +
                        "};\n" +
                        "pulpit.zoom=1;" +
                        "pulpit.x=pulpit.y=pulpit.xs=pulpit.ys=pulpit.mt=pulpit.ml=0;" +
                        "pulpit.punkty=[];" +
                        "pulpit.punktyP=[];" +
                        "pulpit.move=function(eve){\n" +
                        "if(eve.touches.length>=2)" +
                        "{" +
                        "if(pulpit.punkty.length>=2)\n" +
                        "{" +
                        "var mnoznik=Math.sqrt((eve.touches[0].screenX-eve.touches[1].screenX)*(eve.touches[0].screenX-eve.touches[1].screenX)+(eve.touches[0].screenY-eve.touches[1].screenY)*(eve.touches[0].screenY-eve.touches[1].screenY))/Math.sqrt((pulpit.punkty[0].screenX-pulpit.punkty[1].screenX)*(pulpit.punkty[0].screenX-pulpit.punkty[1].screenX)+(pulpit.punkty[0].screenY-pulpit.punkty[1].screenY)*(pulpit.punkty[0].screenY-pulpit.punkty[1].screenY));\n" +
                        "pulpit.zoom=pulpit.zoom*mnoznik;" +
                        "document.getElementById(\"pulpit\").children[0].style.width=mnoznik*parseFloat(document.getElementById(\"pulpit\").children[0].style.width)+'%';" +
                        "document.getElementById(\"pulpit\").children[0].style.height=mnoznik*parseFloat(document.getElementById(\"pulpit\").children[0].style.width)+'%';" +
                        "}" +
                        "}\n" +
                        "else if(eve.touches.length==1)\n" +
                        "{" +
                        "if(pulpit.punkty.length==1)\n" +
                        "{" +
                        "pulpit.x+=pulpit.punkty[0].screenX-eve.touches[0].screenX;\n" +
                        "pulpit.y+=pulpit.punkty[0].screenY-eve.touches[0].screenY;\n" +
                        //"document.getElementById(\"pulpit\").children[0].style.marginLeft=(parseFloat(document.getElementById(\"pulpit\").children[0].style.marginLeft)+(-pulpit.punkty[0].screenX+eve.touches[0].screenX)/pulpit.height*document.getElementById(\"pulpit\").clientHeight)+'px';" +
                        //"document.getElementById(\"pulpit\").children[0].style.marginTop=(parseFloat(document.getElementById(\"pulpit\").children[0].style.marginTop)+(-pulpit.punkty[0].screenY+eve.touches[0].screenY)/pulpit.width*document.getElementById(\"pulpit\").clientWidth)+'px';" +

                        "document.getElementById(\"pulpit\").children[0].style.marginLeft=((pulpit.xs-pulpit.x+pulpit.ml))+'px';" +
                        "document.getElementById(\"pulpit\").children[0].style.marginTop=((pulpit.ys-pulpit.y+pulpit.mt))+'px';" +
                        "}" +
                        "}\n" +
                        "pulpit.punkty=eve.touches;" +
                        "};\n"
                        + "pulpit.moveM=function(eve){\n" +
                        "if(pulpit.punktXs-eve.screenX>-5&&pulpit.punktXs-eve.screenX<5&&pulpit.punktYs-eve.screenY>-5&&pulpit.punktYs-eve.screenY<5)" +
                        "clearTimeout(pulpit.timeout);" +
                        "if(pulpit.punktX!=undefined)\n" +
                        "{" +
                        "pulpit.x+=pulpit.punktX-eve.screenX;\n" +
                        "pulpit.y+=pulpit.punktY-eve.screenY;\n" +
                        // "document.getElementById(\"pulpit\").children[0].style.marginLeft=(parseFloat(document.getElementById(\"pulpit\").children[0].style.marginLeft)+(-pulpit.punktX+eve.screenX)/pulpit.height*document.getElementById(\"pulpit\").clientHeight)+'px';\n" +
                        // "document.getElementById(\"pulpit\").children[0].style.marginTop=(parseFloat(document.getElementById(\"pulpit\").children[0].style.marginTop)+(-pulpit.punktY+eve.screenY)/pulpit.width*document.getElementById(\"pulpit\").clientWidth)+'px';\n" +
                        "document.getElementById(\"pulpit\").children[0].style.marginLeft=((pulpit.xs-pulpit.x+pulpit.ml))+'px';" +
                        "document.getElementById(\"pulpit\").children[0].style.marginTop=((pulpit.ys-pulpit.y+pulpit.mt))+'px';" +
                        "pulpit.punktX=eve.screenX;\n" +
                        "pulpit.punktY=eve.screenY;\n" +
                        "}" +
                        "};\n" +

                        "pulpit.clickD=function(eve,thi)\n" +
                        "{" +
                        "pulpit.timeout=setTimeout(\"pulpit.punktXs=-1000;" +
                        "var dataA=new TCP_Data();" +
                        "dataA.mouse = TCP_Data.touchedTYPE.PPM;" +
                        "dataA.touchpadX = Math.floor(((\"+eve.clientX+\"-document.getElementById('pulpit').clientLeft)*pulpit.width/document.getElementById('pulpit').clientWidth)/pulpit.zoomX+(pulpit.x/pulpit.zoomX*pulpit.width/thi.parentNode.clientWidth));" +
                        "dataA.touchpadY = Math.floor(((\"+eve.clientY+\"-document.getElementById('pulpit').clientTop)*pulpit.height/document.getElementById('pulpit').clientHeight)/pulpit.zoomY+(pulpit.y/pulpit.zoomY*pulpit.height/(thi.parentNode.clientHeight-document.getElementById('menu').clientHeight)));"
                        + "dataA.type=TCP_Data.typ.TOUCHPAD;send(dataA);" +

                        "\",500);" +
                        "};" +
                        "pulpit.clickU=function(eve,thi)\n" +
                        "{" +
                        "clearTimeout(pulpit.timeout);\n" +
                        "if(pulpit.punktXs-eve.screenX>-5 &&pulpit.punktXs-eve.screenX<5 &&pulpit.punktYs-eve.screenY>-5&& pulpit.punktYs-eve.screenY<5 )" +
                        "{\n" +
                        "var data=new TCP_Data();" +
                        "data.mouse = TCP_Data.touchedTYPE.LPM;\n" +
                        "data.touchpadX = Math.floor(((eve.clientX-thi.clientLeft)*pulpit.width/thi.clientWidth)/pulpit.zoomX+(pulpit.x/pulpit.zoomX*pulpit.width/(thi.parentNode.clientWidth)));\n" +
                        "data.touchpadY = Math.floor(((eve.clientY-thi.clientTop)*pulpit.height/thi.clientHeight)/pulpit.zoomY+(pulpit.y/pulpit.zoomY*pulpit.height/(thi.parentNode.clientHeight-document.getElementById('menu').clientHeight)));\n"
                        + " var x=new XMLHttpRequest();x.open('get','/log'+thi.parentNode.clientHeight+' '+document.body.clientHeight );x.send(null);" +
                        "data.type=TCP_Data.typ.TOUCHPAD;send(data);\n"
                        + "}}\n" +
                        "if(document.getElementById('pulpit')!=null){\n" +
                        "document.getElementById('pulpit').addEventListener(\"MSGestureChange\", onDivGestureChange, false);\n" +
                        "document.getElementById('pulpit').addEventListener(\"GestureChange\", onDivGestureChange, false);\n" +
                        "}\n" +
                        "      function onDivGestureChange(e) {\n" +
                        "        pulpit.zoom=pulpit.zoom*e.scale;" +
                        "document.getElementById(\"pulpit\").children[0].style.width=e.scale*parseFloat(document.getElementById(\"pulpit\").children[0].style.width)+'%';" +
                        "document.getElementById(\"pulpit\").children[0].style.height=e.scale*parseFloat(document.getElementById(\"pulpit\").children[0].style.width)+'%';\n" +

                        "      }\n"

                        + "/*]]>*/</script>"
                        + "</head><body onload=\"mapa(document.getElementById('przyciski').clientHeight/1280);var but=document.getElementById('klawiatura').getElementsByTagName('button');hei=(document.body.clientHeight/5-document.getElementById('menu').clientHeight/5)+'px';for(var i=0;i<but.length;i++)but[i].style.height=hei;\" onresize=\"mapa(document.getElementById('przyciski').clientHeight/1280); var but=document.getElementById('klawiatura').getElementsByTagName('button');hei=(document.body.clientHeight/5-document.getElementById('menu').clientHeight/5)+'px';for(var i=0;i<but.length;i++)but[i].style.height=hei;" +
                        // " var x=new XMLHttpRequest();x.open('get','/log'+hei );x.send(null);" +
                        "\" onkeypress=\"console.log(event);" +
                        "var data=new TCP_Data();" +
                        "data.key = event.charCode;\n" +
                        "data.type=TCP_Data.typ.KEYBOARD;send(data);\n" + "return false;\">"
                        //+ "<div class=\"karta\" id=\"gamepad\">Gamepad wkrodce</div>"
                        //+ "<div id=\"aaa\" style=\"color:white;position:fixed;left:0;top:0;\">test</div>"
                        + "<div class=\"karta\" style=\"display:block\" id=\"pilot\">"
                        + "<img id=\"przyciski\" src=\"" + przyciskiBase64 + "\" usemap=\"#przycMapa\" onclick=\"return false;\"  ontouch=\"return false;\"  onmouseclick=\"return false;\" />" +
                        "<map id=\"przycMapa\" name=\"przycMapa\">" +
                        "</map>"
                        + "</div>"
                        +
                        "<div class=\"karta\" id=\"klawiatura\">" +
                        "<DIV class=\"nieparz\">" +
                        "<button onclick=\"klawiatura.char('1')\" class=\"nor\">1</button>" +
                        "<button onclick=\"klawiatura.char('2')\" class=\"nor\">2</button>" +
                        "<button onclick=\"klawiatura.char('3')\" class=\"nor\">3</button>" +
                        "<button onclick=\"klawiatura.char('4')\" class=\"nor\">4</button>" +
                        "<button onclick=\"klawiatura.char('5')\" class=\"nor\">5</button>" +
                        "<button onclick=\"klawiatura.char('6')\" class=\"nor\">6</button>" +
                        "<button onclick=\"klawiatura.char('7')\" class=\"nor\">7</button>" +
                        "<button onclick=\"klawiatura.char('8')\" class=\"nor\">8</button>" +
                        "<button onclick=\"klawiatura.char('9')\" class=\"nor\">9</button>" +
                        "<button onclick=\"klawiatura.char('0')\" class=\"nor\">0</button>" +
                        "</DIV>" + "<div id=\"klawiaturaLiteryM\" style=\"display:block\"><DIV class=\"parz\">" +
                        "<button onclick=\"klawiatura.char('Q')\" class=\"nor\">q</button>" +
                        "<button onclick=\"klawiatura.char('W')\" class=\"nor\">w</button>" +
                        "<button onclick=\"klawiatura.char('E')\" class=\"nor\">e</button>" +
                        "<button onclick=\"klawiatura.char('R')\" class=\"nor\">r</button>" +
                        "<button onclick=\"klawiatura.char('T')\" class=\"nor\">t</button>" +
                        "<button onclick=\"klawiatura.char('Y')\" class=\"nor\">y</button>" +
                        "<button onclick=\"klawiatura.char('U')\" class=\"nor\">u</button>" +
                        "<button onclick=\"klawiatura.char('I')\" class=\"nor\">i</button>" +
                        "<button onclick=\"klawiatura.char('O')\" class=\"nor\">o</button>" +
                        "<button onclick=\"klawiatura.char('P')\" class=\"nor\">p</button>" +
                        "</DIV><DIV class=\"nieparz\">" +
                        "<button onclick=\"klawiatura.char('A')\" class=\"nor\">a</button>" +
                        "<button onclick=\"klawiatura.char('S')\" class=\"nor\">s</button>" +
                        "<button onclick=\"klawiatura.char('D')\" class=\"nor\">d</button>" +
                        "<button onclick=\"klawiatura.char('F')\" class=\"nor\">f</button>" +
                        "<button onclick=\"klawiatura.char('G')\" class=\"nor\">g</button>" +
                        "<button onclick=\"klawiatura.char('H')\" class=\"nor\">h</button>" +
                        "<button onclick=\"klawiatura.char('J')\" class=\"nor\">j</button>" +
                        "<button onclick=\"klawiatura.char('K')\" class=\"nor\">k</button>" +
                        "<button onclick=\"klawiatura.char('L')\" class=\"nor\">l</button>" +
                        "</DIV><DIV class=\"parz\">" +
                        "<button onclick=\"klawiatura.char('Z')\" class=\"nor\">z</button>" +
                        "<button onclick=\"klawiatura.char('X')\" class=\"nor\">x</button>" +
                        "<button onclick=\"klawiatura.char('C')\" class=\"nor\">c</button>" +
                        "<button onclick=\"klawiatura.char('V')\" class=\"nor\">v</button>" +
                        "<button onclick=\"klawiatura.char('B')\" class=\"nor\">b</button>" +
                        "<button onclick=\"klawiatura.char('N')\" class=\"nor\">n</button>" +
                        "<button onclick=\"klawiatura.char('M')\" class=\"nor\">m</button>" +
                        "</div></div>" + "<div id=\"klawiaturaLiteryD\" style=\"display:none\"><DIV class=\"parz\">" +
                        "<button onclick=\"klawiatura.char('q')\" class=\"nor\">Q</button>" +
                        "<button onclick=\"klawiatura.char('w')\" class=\"nor\">W</button>" +
                        "<button onclick=\"klawiatura.char('e')\" class=\"nor\">E</button>" +
                        "<button onclick=\"klawiatura.char('r')\" class=\"nor\">R</button>" +
                        "<button onclick=\"klawiatura.char('t')\" class=\"nor\">T</button>" +
                        "<button onclick=\"klawiatura.char('y')\" class=\"nor\">Y</button>" +
                        "<button onclick=\"klawiatura.char('u')\" class=\"nor\">U</button>" +
                        "<button onclick=\"klawiatura.char('i')\" class=\"nor\">I</button>" +
                        "<button onclick=\"klawiatura.char('o')\" class=\"nor\">O</button>" +
                        "<button onclick=\"klawiatura.char('p')\" class=\"nor\">P</button>" +
                        "</DIV><DIV class=\"nieparz\">" +
                        "<button onclick=\"klawiatura.char('a')\" class=\"nor\">A</button>" +
                        "<button onclick=\"klawiatura.char('s')\" class=\"nor\">S</button>" +
                        "<button onclick=\"klawiatura.char('d')\" class=\"nor\">D</button>" +
                        "<button onclick=\"klawiatura.char('f')\" class=\"nor\">F</button>" +
                        "<button onclick=\"klawiatura.char('g')\" class=\"nor\">G</button>" +
                        "<button onclick=\"klawiatura.char('h')\" class=\"nor\">H</button>" +
                        "<button onclick=\"klawiatura.char('j')\" class=\"nor\">J</button>" +
                        "<button onclick=\"klawiatura.char('k')\" class=\"nor\">K</button>" +
                        "<button onclick=\"klawiatura.char('l')\" class=\"nor\">L</button>" +
                        "</DIV><DIV class=\"parz\">" +
                        "<button onclick=\"klawiatura.char('z')\" class=\"nor\">Z</button>" +
                        "<button onclick=\"klawiatura.char('x')\" class=\"nor\">X</button>" +
                        "<button onclick=\"klawiatura.char('c')\" class=\"nor\">C</button>" +
                        "<button onclick=\"klawiatura.char('v')\" class=\"nor\">V</button>" +
                        "<button onclick=\"klawiatura.char('b')\" class=\"nor\">B</button>" +
                        "<button onclick=\"klawiatura.char('n')\" class=\"nor\">N</button>" +
                        "<button onclick=\"klawiatura.char('m')\" class=\"nor\">M</button>" +
                        "</div></div>" +
                        "<div id=\"klawiaturaZnaki\" style=\"display:none\"><DIV class=\"parz\">" +
                        "<button onclick=\"klawiatura.char('&')\" class=\"nor\">&amp;</button>" +
                        "<button onclick=\"klawiatura.char('/')\" class=\"nor\">/</button>" +
                        "<button onclick=\"klawiatura.char(':')\" class=\"nor\">:</button>" +
                        "<button onclick=\"klawiatura.char(';')\" class=\"nor\">;</button>" +
                        "<button onclick=\"klawiatura.char('(')\" class=\"nor\">(</button>" +
                        "<button onclick=\"klawiatura.char(')')\" class=\"nor\">)</button>" +
                        "<button onclick=\"klawiatura.char('-')\" class=\"nor\">-</button>" +
                        "<button onclick=\"klawiatura.char('+')\" class=\"nor\">+</button>" +
                        "<button onclick=\"klawiatura.char('$')\" class=\"nor\">$</button>" +
                        "<button onclick=\"klawiatura.char('\\\\')\" class=\"nor\">\\</button>" +
                        "</DIV><DIV class=\"nieparz\">" +
                        "<button onclick=\"klawiatura.char('%')\" class=\"nor\">%</button>" +
                        "<button onclick=\"klawiatura.char('=')\" class=\"nor\">=</button>" +
                        "<button onclick=\"klawiatura.char('<')\" class=\"nor\">&lt;</button>" +
                        "<button onclick=\"klawiatura.char('>')\" class=\"nor\">&gt;</button>" +
                        "<button onclick=\"klawiatura.char('[')\" class=\"nor\">[</button>" +
                        "<button onclick=\"klawiatura.char(']')\" class=\"nor\">]</button>" +
                        "<button onclick=\"klawiatura.char('_')\" class=\"nor\">_</button>" +
                        "<button onclick=\"klawiatura.char('^')\" class=\"nor\">^</button>" +
                        "<button onclick=\"klawiatura.char('~')\" class=\"nor\">~</button>" +
                        "</DIV><DIV class=\"parz\">" +
                        "<button onclick=\"klawiatura.char('.')\" class=\"nor\">.</button>" +
                        "<button onclick=\"klawiatura.char(',')\" class=\"nor\">,</button>" +
                        "<button onclick=\"klawiatura.char('?')\" class=\"nor\">?</button>" +
                        "<button onclick=\"klawiatura.char('!')\" class=\"nor\">!</button>" +
                        "<button onclick=\"klawiatura.char('\\'')\" class=\"nor\">'</button>" +
                        "<button onclick=\"klawiatura.num(34)\" class=\"nor\">\"</button>" +
                        "<button onclick=\"klawiatura.char('*')\" class=\"nor\">*</button>" +
                        "<button onclick=\"klawiatura.char('#')\" class=\"nor\">#</button>" +
                        "<button onclick=\"klawiatura.char('@')\" class=\"nor\">@</button>" +
                        "</DIV></DIV>" +
                        "<button id=\"klShift\" style=\"background:#2e2e2e\" onclick=\"if(document.getElementById('klawiaturaLiteryD').style.display=='block'){document.getElementById('klawiaturaLiteryM').style.display='block';document.getElementById('klawiaturaLiteryD').style.display='none';document.getElementById('klawiaturaZnaki').style.display='none';}else{document.getElementById('klawiaturaLiteryM').style.display='none';document.getElementById('klawiaturaLiteryD').style.display='block';document.getElementById('klawiaturaZnaki').style.display='none';}\" class=\"gr\">A/a</button>" +
                        "<button id=\"klZnaki\" style=\"background:#555\" onclick=\"if(document.getElementById('klawiaturaZnaki').style.display=='block'){document.getElementById('klawiaturaLiteryM').style.display='block';document.getElementById('klawiaturaLiteryD').style.display='none';document.getElementById('klawiaturaZnaki').style.display='none';}else{document.getElementById('klawiaturaLiteryM').style.display='none';document.getElementById('klawiaturaLiteryD').style.display='none';document.getElementById('klawiaturaZnaki').style.display='block';}\" class=\"gr\">.?!</button>" +
                        "<button onclick=\"klawiatura.char(' ')\" class=\"spacja\"> </button>" +
                        //"<button id=\"klAlt\" onclick=\"if(document.getElementById('klawiaturaLitery').style.display=='none'){document.getElementById('klawiaturaLitery').style.display='block';document.getElementById('klawiaturaLiteryAlt').style.display='none';} else {document.getElementById('klawiaturaLitery').style.display='none';document.getElementById('klawiaturaLiteryAlt').style.display='block';}document.getElementById('klawiaturaCyfry').style.display='none';document.getElementById('klawiaturaSpecjalne').style.display='none';\" class=\"gr\">Alt</button>" +
                        "<button onclick=\"klawiatura.num(8)\" style=\"background:#555\" class=\"gr\">&larr;</button>" +
                        "<button onclick=\"klawiatura.num(10)\" style=\"background:#2e2e2e\" class=\"gr\">&crarr;</button>" +
                        "</DIV>" +
                        "<textarea style=\"width:1px;height:1px;opacity:0;\"></textarea></div>"
                        //Tu były problemy ze zdarzeniami
                        //+ "<div class=\"karta\" style=\"display:block\" ontouchmove=\"return touchpad.onTouchMove(event)\" onmousemove=\"return touchpad.onMouseMove(event)\" ontouchdown=\"touchpad.onTouchDown(event)\" onmousedown=\"touchpad.onTouchDown(event)\" ontouchup=\"touchpad.onTouchUp(event)\" onmouseup=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\" onmouseleave=\"touchpad.onTouchUp(event)\" id=\"touchpad\"></div>"
                        //+ "<div class=\"karta\" style=\"display:block\" onmousemove=\"return touchpad.onMouseMove(event)\" onmousedown=\"touchpad.onMouseDown(event)\" onmouseup=\"touchpad.onMouseUp(event)\" onmouseleave=\"touchpad.onMouseUp(event)\"  id=\"touchpad\"></div>"
                        //+ "<div class=\"karta\" style=\"display:block\" ontouchmove=\"return touchpad.onTouchMove(event)\" ontouchstart=\"touchpad.onTouchDown(event)\" ontouchend=\"touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\"  id=\"touchpad\"></div>"
                        + "<div class=\"karta\" onmousemove=\"return touchpad.onMouseMove(event)\" onmousedown=\"touchpad.onMouseDown(event)\" onmouseup=\"touchpad.onMouseUp(event)\" onmouseleave=\"touchpad.onMouseUp(event)\" ontouchmove=\"return touchpad.onTouchMove(event)\" ontouchstart=\"return touchpad.onTouchDown(event)\" ontouchend=\"return touchpad.onTouchUp(event)\" ontouchleave=\"touchpad.onTouchUp(event)\" id=\"touchpad\"></div>"
                        + "<div class=\"karta\" id=\"pulpit\" " +
                        //"onMSPointerMove=\"pulpit.moveP(event);return false;\" onMSPointerup=\"pulpit.punkty=[]\" onMSPointerleave=\"pulpit.punkty=[]\" " +
                        //"onPointerMove=\"pulpit.moveP(event);return false;\" onPointerup=\"pulpit.punkty=[]\" onPointerleave=\"pulpit.punkty=[]\" " +
                        "ontouchmove=\"pulpit.move(event);return false;\" ontouchend=\"pulpit.punkty=[]\" ontouchleave=\"pulpit.punkty=[]\" " +
                        "onmousemove=\"pulpit.moveM(event);return false;\" onmousedown=\"if(event.target.className!='zoom'){pulpit.punktXs=pulpit.punktX=event.screenX;pulpit.punktYs=pulpit.punktY=event.screenY;pulpit.clickD(event,this);return false;}\" onmouseup=\"if(event.target.className!='zoom'){pulpit.punktX=undefined;pulpit.clickU(event,this);}\" onmouseleave=\"pulpit.punktX=undefined;\" " +
                        "\">" +
                        "<img " +
                        " src=\"/" + Program.ustawienia.haslo + "/pulpit/0/0/" + (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() + "/" + (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() + "/" + ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 16) + "/" + ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 16) + "/JPEG/\" style=\"width:100%;height:100%\" onload=\"this.style.zIndex=2;pulpit.laduj(this);\" alt=\"Błąd\" />" +
                        //"<img " +
                        // " src=\"/"+Program.ustawienia.haslo+"/pulpit/0/0/"+ (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()+"/"+(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()+"/"+((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/16)+"/"+((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/16)+"/JPEG/\" onload=\"this.style.zIndex=2;this.parentNode.children[0].style.zIndex=1;pulpit.laduj(this);\" alt=\"\" />" +
                        "<div id=\"zoom\"><img class=\"zoom\" id=\"powieksz\" src=\"" + plusBase64 + "\" onclick=\"pulpit.zoom=pulpit.zoom*1.5;pulpit.x=pulpit.x*1.5;pulpit.y=pulpit.y*1.5;" +
                        /*"document.getElementById(\'pulpit\').children[0].style.height=document.getElementById(\'pulpit\').children[0].style.width=parseFloat(document.getElementById(\'pulpit\').children[0].style.width)*1.5+'%';" +
                        "document.getElementById('pulpit').children[0].style.marginLeft=parseFloat(document.getElementById('pulpit').children[0].style.marginLeft)+(pulpit.x*pulpit.zoom/pulpit.zoomS)-pulpit.x;" +
                        "document.getElementById('pulpit').children[0].style.marginTop=parseFloat(document.getElementById('pulpit').children[0].style.marginTop)+(pulpit.y*pulpit.zoom/pulpit.zoomS)-pulpit.y;" +
                        */"\">" +
                        "<img class=\"zoom\" id=\"pomniejsz\" src=\"" + minusBase64 + "\" onclick=\"pulpit.zoom=pulpit.zoom/1.5;pulpit.x=pulpit.x/1.5;pulpit.y=pulpit.y/1.5;" +
                        /*"document.getElementById(\'pulpit\').children[0].style.height=document.getElementById(\'pulpit\').children[0].style.width=parseFloat(document.getElementById(\'pulpit\').children[0].style.width)*0.6666666+'%';" +
                        "document.getElementById('pulpit').children[0].style.marginLeft=parseFloat(document.getElementById('pulpit').children[0].style.marginLeft)+(pulpit.x*pulpit.zoom/pulpit.zoomS)-pulpit.x;" +
                        "document.getElementById('pulpit').children[0].style.marginTop=parseFloat(document.getElementById('pulpit').children[0].style.marginTop)+(pulpit.y*pulpit.zoom/pulpit.zoomS)-pulpit.y;" +
                        */"\"></div>" +
                        "</div>"
                        + "<ul id=\"menu\">" +
                        //"<li onclick='kartaPokaz(\"gamepad\")'><img title=\"gamepad\" src=\""+gamepadBase64+"\"/></li>" +
                        "<li onclick=\"kartaPokaz(\'pilot\');mapa(document.getElementById('przyciski').clientHeight/1280);\"><img alt=\"pilot\" src=\"" + pilotBase64 + "\"/></li><li onclick='kartaPokaz(\"klawiatura\");'><img alt=\"klawiatura\" src=\"" + klawiaturaBase64 + "\"/></li><li onclick='kartaPokaz(\"touchpad\")'><img alt=\"touchpad\" src=\"" + touchpadBase64 + "\"/></li><li onclick='kartaPokaz(\"pulpit\");pulpit.laduj(document.getElementById(\"pulpit\").children[0]);'><img alt=\"Pulpit\" src=\"" + pulpitBase64 + "\"/></li><li onclick=\"" +
                        //"if(document.getElementById('menu').style.top=='5%'){document.getElementById('menu').style.top='90%';document.getElementById('menur').style.top='100%';}else{document.getElementById('menu').style.top='5%';document.getElementById('menur').style.top='15%';}" +
                        "if(windowScrollY()>0){window.scroll(0,0);location.hash=''} else {window.scroll(0,document.body.clientHeight*0.85);location.hash='#ustawienia'}" +
                        "\"><img style=\"float:right\" alt=\"menu\" src=\"" + menuBase64 + "\"/></li>" +
                        "</ul><div id=\"menur\"><h2>" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Informacje.ordinal()] + "</h2>" +
                        "<div class=\"podmenu\">" +
                        Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Polaczenie.ordinal()] + "<br/>" +
                        Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Stan.ordinal()] + ":<span id=\"stanPol\">" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Polaczono.ordinal()] + "</span><br/>" +
                        Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Jakosc.ordinal()] + ":<span id=\"jakosc\" title=\"" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.BrakDanych.ordinal()] + "\">" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.BrakDanych.ordinal()] + "</span><br/>" +

                        Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Host.ordinal()] + ": " + java.net.InetAddress.getLocalHost().getHostName() + "<br/>" +
                        Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Coder.ordinal()] + "<br />" +
                        "-FranQy<br />" +
                        "-Matrix0123456789<br />" +
                        Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Designers.ordinal()] + "<br/>" +
                        "-FranQy<br/>" +
                        "-Wieczur" +
                        "</div><h2>" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.JakoscObrazu.ordinal()] + "</h2><div class=\"podmenu\">" +
                        "<button onclick='pulpit.jakosc=3'>" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Niska.ordinal()] + "</button>" +
                        "<button onclick='pulpit.jakosc=2'>" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Srednia.ordinal()] + "</button>" +
                        "<button onclick='pulpit.jakosc=1'>" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Wysoka.ordinal()] + "</button>" +
                        "<button onclick='pulpit.jakosc=0'>" + Jezyk.nhttp[lang.ordinal()][Jezyk.nHTTP.Ultra.ordinal()] + "</button>" +
                        "<h2>Motyw</h2><div class=\"podmenu\"><label><input type=\"radio\" name=\"motyw\" onclick=\"document.body.style.background='#28211b';\" checked>Ciemny motyw</label><label><input type=\"radio\" name=\"motyw\" onclick=\"document.body.style.background='#e2dd21';\">Jasny motyw</label></div></div>" +
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
                        + "<body style=\" min-height:100%;color:white;background:#26211b;\" onload=\"document.getElementById('txt').focus()\" onclick=\"document.getElementById('txt').focus()\">"
                        + "<form onsubmit=\"document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\" style=\"text-align:center;\"><h1>PilotPC</h1><label><h2>wpisz kod</h2><input id=\"txt\" type=\"number\" style=\"width: 100%;\" onkeyup=\"if(this.value.length==6)document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\"/></label><input type=\"submit\" value=\"ok\" style=\"width: 100%; height:20%;\"/></form>"
                        + "</body></html>";

            } else {
                String typ;
                if (wyj.contains("mobile") || wyj.contains("touch") || wyj.contains("android"))
                    typ = "number";
                else
                    typ = "text";
                String kodWpisany = wyj.substring(1, wyj.indexOf(' '));
                try {
                    int port2 = Polaczenie.hasłoIPort.get(Integer.parseInt(kodWpisany));
                    String host;
                    try {
                        int hostPos = wyj.indexOf("Host:");
                        int pos2 = wyj.indexOf("\r", hostPos);
                        if (wyj.indexOf(":", hostPos + 6) < pos2)
                            pos2 = wyj.indexOf(":", hostPos + 6);
                        host = wyj.substring(hostPos + 6, pos2);
                        wysylanie = "HTTP/1.1 302 Found\r\nLocation:http://" + host + ":" + port2 + "/" + kodWpisany + "\r\n\r\n";

                    } catch (Throwable e3) {

                        Polaczenie.czasBlokadyHasla = System.currentTimeMillis() + 500;
                        wysylanie = "HTTP/1.1 500 Internal Server Error\r\nServer: PilotPC\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
                                + "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
                                + "<meta name=\"viewport\" content=\"width=240, initial-scale=1, user-scalable=no\" />"
                                + "<script>" +
                                "var kod=" + wyj.substring(1, wyj.indexOf(' ')) + ";\n" +
                                "</script>" +
                                "</head>"
                                + "<body style=\" min-height:100%;color:white;background:#26211b;\" onload=\"document.getElementById('txt').focus()\" onclick=\"document.getElementById('txt').focus()\">"
                                + "<form onsubmit=\"document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\" style=\"text-align:center;\"><h1>PilotPC</h1>" +
                                "" +
                                "<label><h2 style=\"color:white;text-shadow:0 0 5px red;\">Błąd!</h2><input id=\"txt\" type=\"" + typ + "\" style=\"width: 100%;\" onkeyup=\"if(this.value.length==6&&this.value!=kod)document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\" value=\"" + kodWpisany + "\"/></label><input type=\"submit\" value=\"ok\" style=\"width: 100%; height:20%;\"/></form>"
                                + "</body></html>";
                    }
                } catch (Exception e) {


                    Polaczenie.czasBlokadyHasla = System.currentTimeMillis() + 500;
                    wysylanie = "HTTP/1.1 200 OK\r\nServer: PilotPC\r\nSet-Cookie: id=" + i + "; path=/\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n"
                            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title>"
                            + "<meta name=\"viewport\" content=\"width=240, initial-scale=1, user-scalable=no\" />"
                            + "<script>" +
                            "var kod=" + wyj.substring(1, wyj.indexOf(' ')) + ";\n" +
                            "</script>" +
                            "</head>"
                            + "<body style=\" min-height:100%;color:white;background:#26211b;\" onload=\"document.getElementById('txt').focus()\" onclick=\"document.getElementById('txt').focus()\">"
                            + "<form onsubmit=\"document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\" style=\"text-align:center;\"><h1>PilotPC</h1>" +
                            "" +
                            "<label><h2 style=\"color:white;text-shadow:0 0 5px red;\">Błędny kod! Wpisz ponownie</h2><input id=\"txt\" type=\"" + typ + "\" style=\"width: 100%;\" onkeyup=\"if(this.value.length==6&&this.value!=kod)document.location.pathname='/'+document.getElementsByTagName('input')[0].value;return false;\" value=\"" + kodWpisany + "\"/></label><input type=\"submit\" value=\"ok\" style=\"width: 100%; height:20%;\"/></form>"
                            + "</body></html>";
                }
            }
            try {
                os.write(wysylanie.getBytes());
            } catch (java.net.SocketException socEx) {
            }
            os.close();
            is.close();
            soc.close();
        }
        //else
        return null;
    }

    static byte id(String wyj, boolean dodatekCzyJest) {
        //spradzanie po cookie czy jest już
        byte i = 0;
        if (dodatekCzyJest)
            i = 110;
        else if (wyj.contains("Cookie: id=")) {
            try {
                i = (byte) Integer.parseInt(wyj.substring(wyj.indexOf("Cookie: id=") + 11, wyj.indexOf('\r', wyj.indexOf("Cookie: id="))));
                //if (Polaczenie.polaczeniaHttp[i] == null) {


                // }
            } catch (Exception e) {
                for (; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                {
                    if (Polaczenie.polaczeniaHttp[i] == null) {
                        break;
                    }
                }
            }
        } else {
            long terazCzas = (new Date()).getTime();
            for (; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
            {

                if (Polaczenie.polaczeniaHttp[i] == null) {
                    break;
                } else if (!Polaczenie.polaczeniaHttp[i].pokazane && terazCzas - Polaczenie.polaczeniaHttp[i].czas.getTime() > 60000) {
                    synchronized (Polaczenie.polaczeniaHttp) {
                        Polaczenie.polaczeniaHttp[i] = null;
                    }
                    break;
                }
            }
        }
        synchronized (Polaczenie.polaczeniaHttp) {
            if (Polaczenie.polaczeniaHttp[i] == null) {
                Polaczenie.polaczeniaHttp[i] = new HttpPolaczenie();

                Polaczenie.polaczeniaHttp[i].UserAgent = new UserAgent(wyj.substring(wyj.indexOf("User-Agent:") + 11, wyj.indexOf('\r', wyj.indexOf("User-Agent:"))));
                if (wyj.indexOf("/dodatek") == 0)
                    Polaczenie.polaczeniaHttp[i].UserAgent.urzadzenie = "Dodatek do przeglądarki";
            } else
                Polaczenie.polaczeniaHttp[i].czas = new Date();
        }
        return i;
    }

    static int max(int[] wej) {
        int ret = -1;
        int tera = Integer.MAX_VALUE;
        for (int i = 0; i < wej.length; i++) {
            if (wej[i] < tera && wej[i] != -1) {
                tera = wej[i];
                ret = i;
            }
        }
        return ret;

    }
}
