import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.example.socketclient.Connect;
import com.example.socketclient.TCP_Data;

public class PolaczenieWatek
        extends Thread  implements PolaczenieInfo{
    public ServerSocket socServ;
    public boolean gotowe=false;
    Socket soc;
    InputStream is;
    public Okno.Urzadzenie UI=null;
    public Okno.Urzadzenie getUI(){return UI;}
    public boolean pokazane=false;
    public Connect infoPrzyPolaczeniu;
    public void rozlacz()
    {
        try {
            soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {  //klasa do sterowania myszka
        while(true){
            //boolean nowe=true;

            is=null;
            try {
                //if(nowe)
                {
                    gotowe=true;
                    soc=socServ.accept();
                    byte ileGotowe=0;
                    for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                    {
                        if(Polaczenie.watki[i]!=null){
                            if(!Polaczenie.watki[i].czyPolaczono()&&Polaczenie.watki[i].socServ==socServ)
                                ileGotowe++;
                        }
                    }
                    if(ileGotowe<4)
                        for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                        {
                            if(Polaczenie.watki[i]==null){
                                Polaczenie.watki[i]=new PolaczenieWatek();
                                Polaczenie.watki[i].socServ=socServ;
                                Polaczenie.watki[i].start();
                                break;
                            }
                        }       }
                //nowe=true;
                is = soc.getInputStream();
                if(getInt(is)==4){//sprawdza czy jest to pilot czy coś innego np. http

                    TCP_Data data=new TCP_Data();
                    switch(getInt(is))
                    {
                        case 2:
                            data.type= TCP_Data.typ.PILOT;
                            break;
                        case 3:
                            data.type= TCP_Data.typ.KEYBOARD;
                            break;
                        case 4:
                            data.type= TCP_Data.typ.TOUCHPAD;
                            data.touchpadX=getInt(is);
                            data.touchpadY=getInt(is);
                            switch (getInt(is))
                            {
                                case 0:
                                    data.mouse= TCP_Data.touchedTYPE.NORMAL;
                                case 1:
                                    data.mouse= TCP_Data.touchedTYPE.LONG;
                                case 2:
                                    data.mouse= TCP_Data.touchedTYPE.UP;
                                case 3:
                                    data.mouse= TCP_Data.touchedTYPE.SCROLL;
                                case 4:
                                    data.mouse= TCP_Data.touchedTYPE.LPM;
                                case 5:
                                    data.mouse= TCP_Data.touchedTYPE.PPM;
                            }
                            break;

                    }

                    wykonuj(data);


                }
                else
                {

                                        /*if(UI!=null)
                                        {
                                                UI.ramka.remove(UI);
                                                UI=null;
                        Okno.potrzebneOdswierzenie=true;
                                        }
                                  this.infoPrzyPolaczeniu=null;
                                        this.pokazane=false;*/
                    try {
                        String wyj="";
                        byte liczbaNowychLinii=0;
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
                        if(wyj.charAt(0)=='/')
                        {
                            TCP_Data data= HTTP.polaczenie(is, soc, wyj);
                            if(data!=null)
                                wykonuj(data);
                        }
                        else
                        {
                            //pinguje
                            OutputStream os =soc.getOutputStream();
                            os.write(wyj.getBytes());
                            os.close();
                            //nowe=false;
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }catch(EOFException e)
            {
                if(UI!=null)
                {
                    UI.ramka.remove(UI);
                    UI=null;
                }
                this.infoPrzyPolaczeniu=null;
                this.pokazane=false;
                System.out.println(Jezyk.napisy[Jezyk.n.Rozlaczono.ordinal()]+" "+toString());
                Okno.potrzebneOdswierzenie=true;


            }catch(Exception e)
            {
                if(UI!=null)
                {
                    UI.ramka.remove(UI);
                    UI=null;
                    Okno.potrzebneOdswierzenie=true;
                }
                this.infoPrzyPolaczeniu=null;
                this.pokazane=false;
                System.out.println("Błąd, rozłączono "+toString());

            }






            byte ileGotowe=0;
            for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
            {
                if(Polaczenie.watki[i]!=null){
                    if(!Polaczenie.watki[i].czyPolaczono()&&Polaczenie.watki[i].socServ==socServ)
                        ileGotowe++;
                }
            }
            if(ileGotowe>5)
            {
                for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                {
                    if(Polaczenie.watki[i]!=this){
                        Polaczenie.watki[i]=null;
                        break;
                    }
                }
                break;
            }
        }}
    void wykonuj(TCP_Data data)
    {
        if(data.type == TCP_Data.typ.TOUCHPAD )
        {
            switch(data.mouse)
            {
                case LPM:
                {
                    if(data.touchpadX!=0||data.touchpadY!=0)
                        MouseRobot.moveTo(data.touchpadX, data.touchpadY);
                    MouseRobot.LPM();

                    break;
                }
                case PPM:
                {

                    if(data.touchpadX!=0||data.touchpadY!=0)
                        MouseRobot.moveTo(data.touchpadX, data.touchpadY);
                    MouseRobot.PPM();
                    break;
                }
                case NORMAL:
                {
                    MouseRobot.move(data.touchpadX, data.touchpadY); //ruszanie myszka
                    break;
                }
                case LONG:
                {
                    MouseRobot.move(true, data.touchpadX, data.touchpadY); //ruszanie ze wcisnietym LPM
                    break;
                }
                case UP:
                {
                    MouseRobot.up(); //podniesienie LPM
                    break;
                }
                case SCROLL:
                {
                    MouseRobot.scroll(data.touchpadY);
                    break;
                }
            }

        }
        else if(data.type == TCP_Data.typ.PILOT )
        {
            for (byte x = 0; x < HTTP.doWykonania.length; x++) {
                if (HTTP.doWykonania[x] == null) {
                    HTTP.doWykonania[x]=data;
                    break;
                }
            }
            Pilot.click(data);
            // System.out.println("pilot");
        }else if(data.type == TCP_Data.typ.KEYBOARD )
        {

            if(data.key>=65&&data.key<=90)
            {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(data.key);
                Program.robot.keyRelease(data.key);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            else if(data.key>=97&&data.key<=122)
            {
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(data.key-32);
                Program.robot.keyRelease(data.key-32);
            }
            else  if(data.key>=45&&data.key<57||data.key==32||data.key==59||data.key==61||data.key==91||data.key==92||data.key==93)
            {
                Program.robot.keyPress(data.key);
                Program.robot.keyRelease(data.key);

            }
            else  if(data.key==-1)
            {
                Program.robot.keyPress(KeyEvent.VK_PRINTSCREEN);
                Program.robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
            }
            else  if(data.key==-2)
            {
                Program.robot.keyPress(KeyEvent.VK_SCROLL_LOCK);
                Program.robot.keyRelease(KeyEvent.VK_SCROLL_LOCK);
            }
            else  if(data.key==-3)
            {
                Program.robot.keyPress(KeyEvent.VK_PAUSE);
                Program.robot.keyRelease(KeyEvent.VK_PAUSE);
            }
            else  if(data.key==-4)
            {
                Program.robot.keyPress(KeyEvent.VK_INSERT);
                Program.robot.keyRelease(KeyEvent.VK_INSERT);
            }
            else  if(data.key==-5)
            {
                Program.robot.keyPress(KeyEvent.VK_HOME);
                Program.robot.keyRelease(KeyEvent.VK_HOME);
            }
            else  if(data.key==-6)
            {
                Program.robot.keyPress(KeyEvent.VK_PAGE_UP);
                Program.robot.keyRelease(KeyEvent.VK_PAGE_UP);
            }
            else  if(data.key==-7)
            {
                Program.robot.keyPress(KeyEvent.VK_DELETE);
                Program.robot.keyRelease(KeyEvent.VK_DELETE);
            }
            else  if(data.key==-8)
            {
                Program.robot.keyPress(KeyEvent.VK_END);
                Program.robot.keyRelease(KeyEvent.VK_END);
            }
            else  if(data.key==-9)
            {
                Program.robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                Program.robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
            }
            else  if(data.key==-10)
            {
                Program.robot.keyPress(KeyEvent.VK_UP);
                Program.robot.keyRelease(KeyEvent.VK_UP);
            }
            else  if(data.key==-11)
            {
                Program.robot.keyPress(KeyEvent.VK_LEFT);
                Program.robot.keyRelease(KeyEvent.VK_LEFT);
            }
            else  if(data.key==-12)
            {
                Program.robot.keyPress(KeyEvent.VK_DOWN);
                Program.robot.keyRelease(KeyEvent.VK_DOWN);
            }
            else  if(data.key==-13)
            {
                Program.robot.keyPress(KeyEvent.VK_RIGHT);
                Program.robot.keyRelease(KeyEvent.VK_RIGHT);
            }
            else  if(data.key==-14)
            {
                Program.robot.keyPress(KeyEvent.VK_WINDOWS);
                Program.robot.keyRelease(KeyEvent.VK_WINDOWS);
            }
            else  if(data.key==-15)
            {
                Program.robot.keyPress(KeyEvent.VK_CONTEXT_MENU);
                Program.robot.keyRelease(KeyEvent.VK_CONTEXT_MENU);
            }
            else  if(data.key==8)
            {
                Program.robot.keyPress(KeyEvent.VK_BACK_SPACE);
                Program.robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            }
            else  if(data.key==10)
            {
                Program.robot.keyPress(KeyEvent.VK_ENTER);
                Program.robot.keyRelease(KeyEvent.VK_ENTER);

            }
            else  if(data.key==27)
            {
                Program.robot.keyPress(KeyEvent.VK_ESCAPE);
                Program.robot.keyRelease(KeyEvent.VK_ESCAPE);

            }
            else
            {
                Program.robot.keyPress(KeyEvent.VK_ALT);
                int kod=data.key;
                int dz=1000000000;
                while(dz>0)
                {
                    Program.robot.keyPress(KeyEvent.VK_NUMPAD0+(kod/dz)%10);
                    Program.robot.keyRelease(KeyEvent.VK_NUMPAD0+(kod/dz)%10);
                    dz=dz/10;
                }
                Program.robot.keyRelease(KeyEvent.VK_ALT);

            }

        }
        data.clean();//czyszczenie zmiennych w TCP_Data
    }
    /**
     *
     * @return Adres IP podłączonego telefonu
     */
    public String getIP()
    {
        return soc.getRemoteSocketAddress().toString();
    }
    public boolean czyPolaczono()
    {
        if(is!=null)
            return true;

            return false;
    }
    @Override
    public String toString()
    {
        if(infoPrzyPolaczeniu!=null)
            return infoPrzyPolaczeniu.nazwa;
        else
            return soc.getRemoteSocketAddress().toString();
    }
    public String opis()
    {
        String ret="<html>IP:"+soc.getRemoteSocketAddress().toString();

        if(infoPrzyPolaczeniu!=null)
            ret+="<br/>"+Jezyk.napisy[Jezyk.n.Nazwa.ordinal()]+":"+infoPrzyPolaczeniu.nazwa;
        return ret+"</html>";

    }
    static int getInt(InputStream is) throws IOException {
        int a= is.read();
        if(a<128)
        return a*16777216+is.read()*65536+is.read()*256+is.read();
        else

            return -((a-128)*16777216+is.read()*65536+is.read()*256+is.read());
    }
    static void setInt(OutputStream os, int l) throws IOException {
        if(l>0)
        {
            os.write((l/16777216)%128);
            os.write((l/65536)%256);
            os.write((l/256)%256);
            os.write(l%256);
        }
        else
        {
            l=-l;
            os.write((l/16777216)%128+128);
            os.write((l/65536)%256);
            os.write((l/256)%256);
            os.write(l%256);
        }
    }
}