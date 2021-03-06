import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Timer;

/**
 * Główna klasa
 */
public class Program {
    static Okno glowneOkno;
    static Polaczenie polaczenia;
    static BufferedImage imgObrazek = null;
    static TypWyswietlania wyswietlanie;
    public static Ustawienia ustawienia = Ustawienia.importuj();
    static public final String wersja = "0.7.10";
    static public Robot robot;
    static boolean debug = false;
    static public boolean ikonaZasobnik = true;
    static public boolean ikonaProgram = true;
    static public TrayIcon trayIcon;
    static public boolean nadpisywanie = false;

    public static void main(String[] args) throws AWTException, InterruptedException {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equalsIgnoreCase("-a") || args[i].equalsIgnoreCase("/a") || args[i].equalsIgnoreCase("--a")) {
                    try {
                        Aktualizacja a = new Aktualizacja();
                        a.run();
                    } finally {
                        return;
                    }
                }
            }
            try {
                while (Polaczenie.socServ == null) {
                    if (Polaczenie.port == 1024 * 64)
                        //TODO Błąd, wszytskie porty zajęte
                        break;
                    try {
                        Polaczenie.socServ = new ServerSocket(Polaczenie.port);
                    } catch (IOException e) {

                        Polaczenie.port++;
                    }
                    if (Polaczenie.port > 8753) {
                        // if (Polaczenie.port == 8753) //łączy się z innym serwerem
                        {
                            try {
                                Socket soc = new Socket("localhost", 8753);
                                OutputStream output = soc.getOutputStream();
                                InputStream input = soc.getInputStream();
                                output.write(("pilotpc" + ustawienia.haslo + Polaczenie.port + "\n\n\n\n").getBytes());
                                // output.close();
                                //int a= input.read();
                                byte[] bufor = new byte[10];
                                input.read(bufor);
                                if (bufor[0] == 'p')//pilotpc
                                {
                                    if (bufor[1] == 'e')//exit
                                    {
                                        while (true) {
                                            if (!Aktualizacja.trwa)
                                                System.exit(0);
                                            else
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e1) {
                                                    Debugowanie.Błąd(e1);
                                                }
                                        }
                                    }

                                }
                                output.close();
                                input.close();
                                soc.close();
                            } catch (UnknownHostException e1) {
                                Debugowanie.Błąd(e1);
                            } catch (IOException e1) {
                                Debugowanie.Błąd(e1);
                            }
                        }
                    }
                }

                Biblioteka.load();
                if (ustawienia.jezyk == null) {
                    ustawienia.jezyk = Jezyk.jezyki.Polski;
                    ustawienia.eksportuj();
                }
                robot = new Robot();
                wyswietlanie = TypWyswietlania.Konsola;
                boolean pomoc = false;
                Jezyk.jezyki lang = ustawienia.jezyk;
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equalsIgnoreCase("/o") || args[i].equalsIgnoreCase("-o"))
                        wyswietlanie = TypWyswietlania.Okno;
                    else if (args[i].equals("/?"))
                        pomoc = true;
                    else if (args[i].equals("/n"))
                        nadpisywanie = true;
                    else if (args[i].equals("/npi") || args[i].equals("-npi") || args[i].equals("/noprogramicon") || args[i].equals("-noprogramicon"))
                        ikonaProgram = false;
                    else if (args[i].equals("/nti") || args[i].equals("-nti") || args[i].equals("/notrayicon") || args[i].equals("-notrayicon"))
                        ikonaZasobnik = false;
                    else if (args[i].equals("/noupdate") || args[i].equals("-noupdate"))
                        Aktualizacja.możnaAktualizować = false;
                    else if (args[i].equals("/b") || args[i].equals("-b") || args[i].equals("-beta") || args[i].equals("/beta"))
                        Aktualizacja.katalog = "beta/";
                    else if (args[i].equals("/l") || args[i].equals("-l") || args[i].equals("-lang") || args[i].equals("/lang")) {
                        i++;
                        if (args[i].equalsIgnoreCase("pl"))
                            lang = Jezyk.jezyki.Polski;
                        else if (args[i].equalsIgnoreCase("en"))
                            lang = Jezyk.jezyki.Angielski;
                        else if (args[i].equalsIgnoreCase("ru"))
                            lang = Jezyk.jezyki.Rosyjski;
                    }

                }
                Jezyk.laduj(lang);
                if (!Konsola.polecenie(args, true))
                    return;
                if (wyswietlanie == TypWyswietlania.Okno) {
                    glowneOkno = new Okno();
                }
                if (pomoc) {
                    String Tekst = "To jest pomoc.\n\r\n\rParametry:\n\r /?  Wyświetlenie pomocy\n\r /k  Uruchomienie bez okna (tylko konsola)\n\r\r\n";
                    System.out.print(Tekst);

                    //if(wyświetlanie==TypWyświetlania.Okno)
                    //Mess
                } else
                    System.out.println(Jezyk.napisy[Jezyk.n.PilotPCWersja.ordinal()] + wersja + "\r\nKod: " + ustawienia.haslo);

                (new Konsola()).start();
                tray(true);

                try {
                    while (true) {
                        DatagramSocket socket = null;
                        for (int i = 8753; i < 20000; i++) {
                            try {
                                socket = new DatagramSocket(i);
                                break;
                            } catch (BindException e) {
                            }
                        }
                        if (socket != null) {
                            socket.setBroadcast(true);

                            String host = "aaaa" + java.net.InetAddress.getLocalHost().getHostName();

                            DatagramPacket pakiet = new DatagramPacket(host.getBytes(), host.length(), InetAddress.getByName("255.255.255.255"), 8753);
                            socket.connect(InetAddress.getByName("255.255.255.255"), 8753);
                            socket.send(pakiet);
                            socket.close();
                        }
                        Thread.sleep(1000);
                    }
                } catch (IOException e) {

                    Debugowanie.Błąd(e);
                }
            } catch (Throwable e) {

                Debugowanie.Błąd(e);
            }
        } catch (Throwable e) {
            Debugowanie.Błąd(e);
        }
    }

    public static void tray(boolean nowy) {
        if (!ikonaZasobnik)
            return;
        if (SystemTray.isSupported()) {
            final SystemTray tray = SystemTray.getSystemTray();
            final JPopupMenu popup = new JPopupMenu();
            JMenuItem pokaz = new JMenuItem(Jezyk.napisy[Jezyk.n.Pokaz.ordinal()]); //tworzymy obiekt menuItem
            popup.add(pokaz);                //dodajemy objekt do menu

            pokaz.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
                public void actionPerformed(ActionEvent e) {
                    wyswietlanie = TypWyswietlania.Okno;
                    popup.setVisible(false);
                    if (glowneOkno == null)
                        glowneOkno = new Okno();

                    glowneOkno.frame.setVisible(true);
                    // glowneOkno.frame.setUndecorated(false);
                }
            });
            JMenuItem opcje = new JMenuItem(Jezyk.napisy[Jezyk.n.Infromacje.ordinal()]); //tworzymy obiekt menuItem
            popup.add(opcje);                //dodajemy objekt do menu

            opcje.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
                public void actionPerformed(ActionEvent e) {
                    popup.setVisible(false);
                    Opcje.pokarz();
                }
            });
            JMenuItem zakoncz = new JMenuItem(Jezyk.napisy[Jezyk.n.Zakoncz.ordinal()]); //tworzymy obiekt menuItem
            popup.add(zakoncz);                //dodajemy objekt do menu
            zakoncz.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
                public void actionPerformed(ActionEvent e) {    // tworzymy funkcję zamykającą naszą aplikację
                    popup.setVisible(false);
                    while (true) {
                        if (!Aktualizacja.trwa)
                            System.exit(0);
                        else
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e1) {
                                Debugowanie.Błąd(e1);
                            }
                    }
                }
            });// dodajemy zdarzenie do pozycji zakończ

            imgObrazek = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                //InputStream input = classLoader.getResourceAsStream(
                // "resources/ikona.png");
                imgObrazek = ImageIO.read(classLoader.getResourceAsStream(
                        "resources/ikona.png"));
            } catch (IOException e) {
            }
            try {
                if (!nowy)
                    tray.remove(trayIcon);
            } catch (NullPointerException e) {
            }

            popup.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // popup.setLocation(0,0);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    popup.setVisible(false);

                }
            });
            popup.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    popup.setVisible(false);
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });

            trayIcon = new TrayIcon(imgObrazek.getScaledInstance((int) tray.getTrayIconSize().getWidth(), (int) tray.getTrayIconSize().getHeight(), Image.SCALE_SMOOTH), "PilotPC", null);

            trayIcon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == 1) {
                        wyswietlanie = TypWyswietlania.Okno;
                        if (glowneOkno == null) {
                            glowneOkno = new Okno();
                        }
                        //glowneOkno.frame.setUndecorated(true);
                        glowneOkno.frame.setVisible(true);

                    } else {

                        popup.setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y - 50);
                        popup.setVisible(true);
                        popup.setFocusable(true);
                        popup.grabFocus();
                        popup.requestFocus();
                    }

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            try {
                trayIcon.setToolTip("Jaebe PilotPC");
                tray.add(trayIcon);   // dodanie naszej ikony do zasobnika systemowego

            } catch (AWTException e) {
                Debugowanie.Błąd(e);
                System.out.println(Jezyk.napisy[Jezyk.n.BladPodczasDodawaniaIkony.ordinal()]);            // Wyświetl komunikat

            }
            // if(nowy)
            // trayIcon.displayMessage("PilotPC " + wersja, Jezyk.napisy[Jezyk.n.SerwerZostalUruchomiony.ordinal()], TrayIcon.MessageType.INFO);  // Wyświetlenie dymka powitalnego.
        }

        try {
            Biblioteka.sprawdz();
        } catch (Throwable e) {
        }
        Timer timer1 = new Timer();
        Aktualizacja timer1_task = new Aktualizacja();
        timer1.schedule(timer1_task, 0, 3600000);
        Timer timer2 = new Timer();
        OdśmiecanieHttpPołączenia timer2_task = new OdśmiecanieHttpPołączenia();
        timer2.schedule(timer2_task, 5000, 5000);
        polaczenia = new Polaczenie();
    }

    static public void autostart() {
        boolean dodaj = true;
        try {
            dodaj = !Biblioteka.CzyAutostart();
        } catch (Throwable e) {

        }
        Biblioteka.autostart(dodaj, false, System.getProperty("user.dir"));
    }


}


