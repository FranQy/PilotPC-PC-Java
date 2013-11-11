import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class Program {

	/**
	 * @param args
	 */
	static Okno glowneOkno;
	static Polaczenie polaczenia;
	static TypWyswietlania wyswietlanie;
	static Ustawienia ustawienia=Ustawienia.importuj();
	/**
	 * Numer urzywany przy nawiązywaniu połączenia do uwierzytelniania
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		wyswietlanie=TypWyswietlania.Konsola;
		boolean pomoc=false;
		for(int i=0;i<args.length;i++)
		{
			if(args[i].equalsIgnoreCase("/o"))	
				wyswietlanie=TypWyswietlania.Okno;
			else if(args[i].equals("/?"))	
					pomoc=true;
			
		}
		if(wyswietlanie==TypWyswietlania.Okno)
		{
glowneOkno=new Okno();
glowneOkno.show();
		}
		if(pomoc){
			String Tekst="To jest pomoc.\n\r\n\rParametry:\n\r /?  Wyświetlenie pomocy\n\r /k  Uruchomienie bez okna (tylko konsola)\n\r\r\n";
			System.out.print(Tekst);;
			//if(wyświetlanie==TypWyświetlania.Okno)
				//Mess
		}
		if (SystemTray.isSupported()){ 
			SystemTray tray = SystemTray.getSystemTray();
			PopupMenu popup = new PopupMenu();
			MenuItem zakoncz = new MenuItem("Zakończ"); //tworzymy obiekt menuItem
			popup.add(zakoncz);                //dodajemy objekt do menu
			ActionListener exitListener = new ActionListener() {         // tworzymy obiekt ActionListener
				public void actionPerformed(ActionEvent e) {    // tworzymy funkcję zamykającą naszą aplikację
				System.out.println("Kończę działanie programu...");
				System.exit(0);    
				}
				};

				zakoncz.addActionListener(exitListener);  // dodajemy zdarzenie do pozycji zakończ
				BufferedImage imgObrazek;
				imgObrazek=   new BufferedImage (16, 16, BufferedImage.TYPE_INT_RGB);
			
				TrayIcon trayIcon = new TrayIcon(imgObrazek, "Program w tray'u", popup);
				try {
					tray.add(trayIcon);   // dodanie naszej ikony do zasobnika systemowego
					}
					catch (AWTException e) {
					javax.swing.JOptionPane.showMessageDialog(null, "Błąd podczas dodawania ikony do zasobnika systemowego." +
					"\nAplikacja zostanie zamknięta.");            // Wyświetl komunikat
					System.exit(-1);        // Zamknij aplikację z błędem
					}

					trayIcon.displayMessage ("PilotPC", "Serwer został uruchomiony", TrayIcon.MessageType.INFO);  // Wyświetlenie dymka powitalnego.
		}
		polaczenia=new Polaczenie();
	}
}


