import org.omg.CORBA.Environment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Timer;

public class Program {
    static Okno glowneOkno;
    static Polaczenie polaczenia;
    static TypWyswietlania wyswietlanie;
    static Ustawienia ustawienia = Ustawienia.importuj();
    static public String wersja = "0.1.27";
    static public Robot robot;
    public static void main(String[] args) throws AWTException {
        // TODO Auto-generated method stub
        robot=new Robot();
        wyswietlanie = TypWyswietlania.Konsola;
        boolean pomoc = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("/o"))
                wyswietlanie = TypWyswietlania.Okno;
            else if (args[i].equals("/?"))
                pomoc = true;

        }
        if (wyswietlanie == TypWyswietlania.Okno) {
            glowneOkno = new Okno();
        }
        if (pomoc) {
            String Tekst = "To jest pomoc.\n\r\n\rParametry:\n\r /?  Wyświetlenie pomocy\n\r /k  Uruchomienie bez okna (tylko konsola)\n\r\r\n";
            System.out.print(Tekst);

            //if(wyświetlanie==TypWyświetlania.Okno)
            //Mess
        }
        else
            System.out.println("PilotPC wersja " + wersja);
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            PopupMenu popup = new PopupMenu();
            MenuItem pokaz = new MenuItem("Pokaż"); //tworzymy obiekt menuItem
            popup.add(pokaz);                //dodajemy objekt do menu

            pokaz.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
                public void actionPerformed(ActionEvent e) {
                    wyswietlanie = TypWyswietlania.Okno;
                    if (glowneOkno == null)
                        glowneOkno = new Okno();
                    else
                        glowneOkno.frame.setVisible(true);
                }
            });
            MenuItem zakoncz = new MenuItem("Zakończ"); //tworzymy obiekt menuItem
            popup.add(zakoncz);                //dodajemy objekt do menu
            zakoncz.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
                public void actionPerformed(ActionEvent e) {    // tworzymy funkcję zamykającą naszą aplikację
                    while (true) {
                        if (!Aktualizacja.trwa)
                            System.exit(0);
                        else
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                    }
                }
            });// dodajemy zdarzenie do pozycji zakończ

            BufferedImage imgObrazek;
            imgObrazek = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);

            TrayIcon trayIcon = new TrayIcon(imgObrazek, "PilotPC-PC-Java", popup);
            trayIcon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                      if(e.getButton()==1)  {
                    wyswietlanie = TypWyswietlania.Okno;
                    if (glowneOkno == null)
                    {
                        glowneOkno = new Okno();
                    }
                    else
                    {
                        glowneOkno.frame.setVisible(true);
                    }   }
                }

                @Override
                public void mousePressed(MouseEvent e) {

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
            })     ;
            trayIcon.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
                public void actionPerformed(ActionEvent e) {
                }
            });
            try {
                tray.add(trayIcon);   // dodanie naszej ikony do zasobnika systemowego
            } catch (AWTException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Błąd podczas dodawania ikony do zasobnika systemowego.");            // Wyświetl komunikat

            }

            trayIcon.displayMessage("PilotPC " + wersja, "Serwer został uruchomiony", TrayIcon.MessageType.INFO);  // Wyświetlenie dymka powitalnego.
        }
         try{
             Biblioteka.sprawdz();
         }
         catch(Throwable e){}
        Timer timer1 = new Timer();
        Aktualizacja timer1_task = new Aktualizacja();
        timer1.schedule(timer1_task, 0, 3600000);
        polaczenia = new Polaczenie();
    }

    static public void autostart() {
        Biblioteka.autostart(true, false, System.getProperty("user.dir"));
    }


}


