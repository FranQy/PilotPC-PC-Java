import com.jaebe.PilotPC.Connect;
import com.jaebe.PilotPC.Ping;
import com.jaebe.PilotPC.TCP_Data;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Pojedynćze urządzenie wyświetlone na liście w oknie wersja dla apki
 */
public class PolaczenieWatek
        extends Thread implements PolaczenieInfo {
    public ServerSocket socServ;
    public boolean gotowe = false;
    Socket soc;
    InputStream is;
    public Okno.Urzadzenie UI = null;
    public long czasMax = (long) 0x7fffffff * (long) 0x7fffffff;

    public Okno.Urzadzenie getUI() {
        return UI;
    }

    public boolean pokazane = false;
    public Connect infoPrzyPolaczeniu = null;

    public void rozlacz() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream());
            Ping dataObject = new Ping();
            dataObject.liczba = -1;
            oos.writeObject(dataObject);
            oos.flush();
            soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {  //klasa do sterowania myszka
        while (true) {
            //boolean nowe=true;

            is = null;
            try {
                //if(nowe)
                {
                    gotowe = true;
                    soc = socServ.accept();
                    byte ileGotowe = 0;
                    for (byte i = 0; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                    {
                        if (Polaczenie.watki[i] != null) {
                            if (!Polaczenie.watki[i].czyPolaczono() && Polaczenie.watki[i].socServ == socServ)
                                ileGotowe++;
                        }
                    }
                    if (ileGotowe < 4)
                        for (byte i = 0; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                        {
                            if (Polaczenie.watki[i] == null) {
                                Polaczenie.watki[i] = new PolaczenieWatek();
                                Polaczenie.watki[i].socServ = socServ;
                                Polaczenie.watki[i].start();
                                break;
                            }
                        }
                }
                //nowe=true;

                try {
                    ObjectInputStream in = new ObjectInputStream(soc.getInputStream());

                    while (true) {

                        try {
                            Object dataObject = in.readObject();

                            if (dataObject.getClass() == Connect.class) {
                                if (Program.debug)
                                    System.out.println("Otrzymano klasę Connect");
                                infoPrzyPolaczeniu = (Connect) dataObject;
                            Connect odpowiedz = new Connect();
                                while (System.currentTimeMillis() < Polaczenie.czasBlokadyHasla)
                                    Thread.sleep(System.currentTimeMillis() - Polaczenie.czasBlokadyHasla);
                                if (infoPrzyPolaczeniu.haslo.compareTo(Program.ustawienia.haslo) == 0)
                                    odpowiedz.status = Connect.Status.ok;
                            else {
                                odpowiedz.status = Connect.Status.zlyKod;
                                    Polaczenie.czasBlokadyHasla = System.currentTimeMillis() + 500;
                                    //in.close();
                                break;
                            }
                            odpowiedz.nazwa = java.net.InetAddress.getLocalHost().getHostName();
                            ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream());
                            oos.writeObject(odpowiedz);
                            oos.flush();
                                if (odpowiedz.status == Connect.Status.zlyKod) {
                                    if (Program.debug)
                                        System.out.println();
                                    oos.close();
                                    in.close();
                                    soc.close();
                                }
                                // dataObject =  in.readObject();
                            System.out.println(Jezyk.napisy[Jezyk.n.Polaczono.ordinal()] + " " + toString());
                        } else if (dataObject.getClass() == Ping.class) {
                            ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream());
                            oos.writeObject(dataObject);
                            czasMax = (new Date()).getTime() + 5000 + ((Ping) dataObject).liczba * 1000;
                            oos.flush();
                            // oos.close();

                        } else {
                            TCP_Data data = (TCP_Data) dataObject;

                            //TCP_Data data = (TCP_Data) in.readObject();
                            wykonuj(data);
                            }
                        } catch (UTFDataFormatException e2) {
                            e2.printStackTrace();

                            is = soc.getInputStream();
                            for (int licz = 0; licz < 16; licz++) {
                                int n = is.read();
                                if (n == -1) {
                                    System.out.println((char) n + n);
                                }
                            }

                        }
                    }
                } catch (StreamCorruptedException e) {

                    is = soc.getInputStream();
                                        /*if(UI!=null)
                                        {
                                                UI.ramka.remove(UI);
                                                UI=null;
                        Okno.potrzebneOdswierzenie=true;
                                        }
                                  this.infoPrzyPolaczeniu=null;
                                        this.pokazane=false;*/
                    try {
                        String wyj = "";
                        byte liczbaNowychLinii = 0;
                        while (true) {
                            int n = is.read();
                            if (n == -1) {

                                break;
                            } else {
                                //robot.keyPress(n+128);
                                if (n == '\n' || n == '\r')
                                    liczbaNowychLinii++;
                                else
                                    liczbaNowychLinii = 0;
                                wyj += (char) n;

                            }

                            if (liczbaNowychLinii == 4) {

                                break;
                            }
                        }
                        if (wyj.charAt(0) == '/') {
                            TCP_Data data = HTTP.polaczenie(is, soc, wyj);
                            if (data != null)
                                wykonuj(data);
                        } else if (wyj.indexOf("tpc") == 0) {
                            OutputStream os = soc.getOutputStream();
                            //os.write("p000000000".getBytes());
                            int haslo = Integer.parseInt(wyj.substring(3, 9));
                            if (haslo == Integer.parseInt(Program.ustawienia.haslo)) {
                                os.write("pe00000000".getBytes());
                                if (Program.glowneOkno == null)
                                    Program.glowneOkno = new Okno();
                                Program.glowneOkno.frame.setVisible(true);
                            } else
                                try {
                                    Integer port = Polaczenie.hasłoIPort.get(haslo);
                                    if (port == null) {

                                        os.write("p000000000".getBytes());
                                        port = Integer.parseInt(wyj.substring(9, wyj.indexOf('\n')));
                                        Polaczenie.hasłoIPort.put(haslo, port);
                                    }
                                    os.write("pe00000000".getBytes());
                                    Socket soc = new Socket("localhost", port);
                                    OutputStream output = soc.getOutputStream();
                                    //InputStream input = soc.getInputStream();
                                    output.write(("pilot" + wyj + "\n\n\n\n").getBytes());
                                    output.close();
                                    soc.close();

                                } catch (Exception e2) {

                                    os.write("p000000000".getBytes());
                                    int port = Integer.parseInt(wyj.substring(9, wyj.indexOf('\n')));
                                    Polaczenie.hasłoIPort.put(haslo, port);

                                }
                        } else {
                            //pinguje
                            OutputStream os = soc.getOutputStream();
                            os.write(wyj.getBytes());
                            os.close();
                            //nowe=false;
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (EOFException e) {
                if (UI != null) {
                    UI.ramka.remove(UI);
                    UI = null;
                }
                this.infoPrzyPolaczeniu = null;
                this.pokazane = false;
                System.out.println(Jezyk.napisy[Jezyk.n.Rozlaczono.ordinal()] + " " + toString());
                Okno.potrzebneOdswierzenie = true;
                if (Program.debug) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                if (UI != null) {
                    UI.ramka.remove(UI);
                    UI = null;
                    Okno.potrzebneOdswierzenie = true;
                }
                this.infoPrzyPolaczeniu = null;
                this.pokazane = false;
                System.out.println(Jezyk.napisy[Jezyk.n.BladRozlaczono.ordinal()] + " " + toString());
                if (Program.debug) {
                    e.printStackTrace();
                }
            }


            byte ileGotowe = 0;
            for (byte i = 0; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
            {
                if (Polaczenie.watki[i] != null) {
                    if (!Polaczenie.watki[i].czyPolaczono() && Polaczenie.watki[i].socServ == socServ)
                        ileGotowe++;
                }
            }
            if (ileGotowe > 5) {
                for (byte i = 0; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                {
                    if (Polaczenie.watki[i] != this) {
                        Polaczenie.watki[i] = null;
                        break;
                    }
                }
                break;
            }
        }
    }

    void wykonuj(TCP_Data data) {
        if (data.ytUrl != null) {
            try {
                if (System.getProperty("os.name").toUpperCase().contains("WIN"))

                    Runtime.getRuntime().exec(data.ytUrl);
                else

                    Runtime.getRuntime().exec("x-www-browser " + data.ytUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (data.type == TCP_Data.typ.TOUCHPAD) {
            switch (data.mouse) {
                case LPM: {
                    if (data.touchpadX != 0 || data.touchpadY != 0)
                        MouseRobot.moveTo(data.touchpadX, data.touchpadY);
                    MouseRobot.LPM();

                    break;
                }
                case PPM: {

                    if (data.touchpadX != 0 || data.touchpadY != 0)
                        MouseRobot.moveTo(data.touchpadX, data.touchpadY);
                    MouseRobot.PPM();
                    break;
                }
                case NORMAL: {
                    MouseRobot.move(data.touchpadX, data.touchpadY); //ruszanie myszka
                    break;
                }
                case LONG: {
                    MouseRobot.move(true, data.touchpadX, data.touchpadY); //ruszanie ze wcisnietym LPM
                    break;
                }
                case UP: {
                    MouseRobot.up(); //podniesienie LPM
                    break;
                }
                case SCROLL: {
                    MouseRobot.scroll(data.touchpadY);
                    break;
                }
            }

        } else if (data.type == TCP_Data.typ.PILOT) {
            for (byte x = 0; x < HTTP.doWykonania.length; x++) {
                if (HTTP.doWykonania[x] == null) {
                    HTTP.doWykonania[x] = data;
                    break;
                }
            }
            Pilot.click(data);
            // System.out.println("pilot");
        } else if (data.type == TCP_Data.typ.KEYBOARD) {
             try{
            if (data.key >= 65 && data.key <= 90) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(data.key);
                Program.robot.keyRelease(data.key);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key >= 97 && data.key <= 122) {
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(data.key - 32);
                Program.robot.keyRelease(data.key - 32);
            } else if (data.key >= 45 && data.key <= 57 || data.key == 32 || data.key == 59 || data.key == 61 || data.key == 91 || data.key == 92 || data.key == 93) {
                Program.robot.keyPress(data.key);
                Program.robot.keyRelease(data.key);

            } else if (data.key == -1) {
                Program.robot.keyPress(KeyEvent.VK_PRINTSCREEN);
                Program.robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
            } else if (data.key == -2) {
                Program.robot.keyPress(KeyEvent.VK_SCROLL_LOCK);
                Program.robot.keyRelease(KeyEvent.VK_SCROLL_LOCK);
            } else if (data.key == -3) {
                Program.robot.keyPress(KeyEvent.VK_PAUSE);
                Program.robot.keyRelease(KeyEvent.VK_PAUSE);
            } else if (data.key == -4) {
                Program.robot.keyPress(KeyEvent.VK_INSERT);
                Program.robot.keyRelease(KeyEvent.VK_INSERT);
            } else if (data.key == -5) {
                Program.robot.keyPress(KeyEvent.VK_HOME);
                Program.robot.keyRelease(KeyEvent.VK_HOME);
            } else if (data.key == -6) {
                Program.robot.keyPress(KeyEvent.VK_PAGE_UP);
                Program.robot.keyRelease(KeyEvent.VK_PAGE_UP);
            } else if (data.key == -7) {
                Program.robot.keyPress(KeyEvent.VK_DELETE);
                Program.robot.keyRelease(KeyEvent.VK_DELETE);
            } else if (data.key == -8) {
                Program.robot.keyPress(KeyEvent.VK_END);
                Program.robot.keyRelease(KeyEvent.VK_END);
            } else if (data.key == -9) {
                Program.robot.keyPress(KeyEvent.VK_PAGE_DOWN);
                Program.robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
            } else if (data.key == -10) {
                Program.robot.keyPress(KeyEvent.VK_UP);
                Program.robot.keyRelease(KeyEvent.VK_UP);
            } else if (data.key == -11) {
                Program.robot.keyPress(KeyEvent.VK_LEFT);
                Program.robot.keyRelease(KeyEvent.VK_LEFT);
            } else if (data.key == -12) {
                Program.robot.keyPress(KeyEvent.VK_DOWN);
                Program.robot.keyRelease(KeyEvent.VK_DOWN);
            } else if (data.key == -13) {
                Program.robot.keyPress(KeyEvent.VK_RIGHT);
                Program.robot.keyRelease(KeyEvent.VK_RIGHT);
            } else if (data.key == -14) {
                Program.robot.keyPress(KeyEvent.VK_WINDOWS);
                Program.robot.keyRelease(KeyEvent.VK_WINDOWS);
            } else if (data.key == -15) {
                Program.robot.keyPress(KeyEvent.VK_CONTEXT_MENU);
                Program.robot.keyRelease(KeyEvent.VK_CONTEXT_MENU);
            } else if (data.key == 8) {
                Program.robot.keyPress(KeyEvent.VK_BACK_SPACE);
                Program.robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            } else if (data.key == 10) {
                Program.robot.keyPress(KeyEvent.VK_ENTER);
                Program.robot.keyRelease(KeyEvent.VK_ENTER);

            } else if (data.key == 27) {
                Program.robot.keyPress(KeyEvent.VK_ESCAPE);
                Program.robot.keyRelease(KeyEvent.VK_ESCAPE);


            } else if (data.key == 38) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_7);
                Program.robot.keyRelease(KeyEvent.VK_7);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            }else if (data.key == 58) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_SEMICOLON);
                Program.robot.keyRelease(KeyEvent.VK_SEMICOLON);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 40) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_9);
                Program.robot.keyRelease(KeyEvent.VK_9);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            }else if (data.key == 41) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_0);
                Program.robot.keyRelease(KeyEvent.VK_0);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
        } else if (data.key == 47) {
            Program.robot.keyPress(KeyEvent.VK_SLASH);
            Program.robot.keyRelease(KeyEvent.VK_SLASH);
        } else if (data.key == 59) {
            Program.robot.keyPress(KeyEvent.VK_SEMICOLON);
            Program.robot.keyRelease(KeyEvent.VK_SEMICOLON);
        } else if (data.key == 45) {
                Program.robot.keyPress(KeyEvent.VK_MINUS);
                Program.robot.keyRelease(KeyEvent.VK_MINUS);
            } else if (data.key == 43) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_EQUALS);
                Program.robot.keyRelease(KeyEvent.VK_EQUALS);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 38) {
                Program.robot.keyPress(KeyEvent.VK_DOLLAR);
                Program.robot.keyRelease(KeyEvent.VK_DOLLAR);
            } else if (data.key == 92) {
                Program.robot.keyPress(KeyEvent.VK_BACK_SLASH);
                Program.robot.keyRelease(KeyEvent.VK_BACK_SLASH);
            } else if (data.key == 37) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_5);
                Program.robot.keyRelease(KeyEvent.VK_5);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 61) {
                Program.robot.keyPress(KeyEvent.VK_EQUALS);
                Program.robot.keyRelease(KeyEvent.VK_EQUALS);
            } else if (data.key == 60) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_COMMA);
                Program.robot.keyRelease(KeyEvent.VK_COMMA);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 62) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(46);
                Program.robot.keyRelease(46);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 95) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_MINUS);
                Program.robot.keyRelease(KeyEvent.VK_MINUS);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 94) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_6);
                Program.robot.keyRelease(KeyEvent.VK_6);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            }  else if (data.key == 46) {
                Program.robot.keyPress(46);
                Program.robot.keyRelease(46);
            } else if (data.key == 63) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_SLASH);
                Program.robot.keyRelease(KeyEvent.VK_SLASH);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 33) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_1);
                Program.robot.keyRelease(KeyEvent.VK_1);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 39) {
                Program.robot.keyPress(KeyEvent.VK_QUOTE);
                Program.robot.keyRelease(KeyEvent.VK_QUOTE);
            } else if (data.key == 34) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_QUOTE);
                Program.robot.keyRelease(KeyEvent.VK_QUOTE);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 42) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_8);
                Program.robot.keyRelease(KeyEvent.VK_8);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 35) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_3);
                Program.robot.keyRelease(KeyEvent.VK_3);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            } else if (data.key == 64) {
                Program.robot.keyPress(KeyEvent.VK_SHIFT);
                Program.robot.keyPress(KeyEvent.VK_2);
                Program.robot.keyRelease(KeyEvent.VK_2);
                Program.robot.keyRelease(KeyEvent.VK_SHIFT);
            }else {
                //windows
                Program.robot.keyPress(KeyEvent.VK_ALT);
                int kod = data.key;
                int dz = (int) Math.pow(10, Math.ceil(Math.log10(Math.abs(kod))));

                while (dz > 0) {
                    Program.robot.keyPress(KeyEvent.VK_NUMPAD0 + (kod / dz) % 10);
                    Program.robot.keyRelease(KeyEvent.VK_NUMPAD0 + (kod / dz) % 10);
                    dz = dz / 10;
                }Program.robot.keyRelease(KeyEvent.VK_ALT);}
                 } catch(IllegalArgumentException e) {}
                finally{
                    Program.robot.keyRelease(KeyEvent.VK_SHIFT);
                }


            }

        data.clean();//czyszczenie zmiennych w TCP_Data
        }


    /**
     * @return Adres IP podłączonego telefonu
     */
    public String getIP() {
        return soc.getRemoteSocketAddress().toString();
    }

    public boolean czyPolaczono() {
        return infoPrzyPolaczeniu != null;

    }

    @Override
    public String toString() {
        if (infoPrzyPolaczeniu != null)
            return infoPrzyPolaczeniu.nazwa;
        else
            return soc.getRemoteSocketAddress().toString();
    }

    public String opis() {
        String ret = "<html>IP:" + soc.getRemoteSocketAddress().toString();

        if (infoPrzyPolaczeniu != null)
            ret += "<br/>" + Jezyk.napisy[Jezyk.n.Nazwa.ordinal()] + ":" + infoPrzyPolaczeniu.nazwa;
        return ret + "</html>";

    }
}