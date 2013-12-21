import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Timer;
public class Program {

	/**
	 * @param args
	 */
	static Okno glowneOkno;
	static Polaczenie polaczenia;
	static TypWyswietlania wyswietlanie;
	static Ustawienia ustawienia=Ustawienia.importuj();
	static public String wersja="0.1.21";
	
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
			MenuItem pokaz = new MenuItem("Pokaż"); //tworzymy obiekt menuItem
			popup.add(pokaz);                //dodajemy objekt do menu
			
			pokaz.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
								public void actionPerformed(ActionEvent e) {    
									wyswietlanie=TypWyswietlania.Okno;  
									if(glowneOkno==null)
										glowneOkno=new Okno();
						else
							glowneOkno.show();
							}
							});
				MenuItem zakoncz = new MenuItem("Zakończ"); //tworzymy obiekt menuItem
				popup.add(zakoncz);                //dodajemy objekt do menu
				zakoncz.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
					public void actionPerformed(ActionEvent e) {    // tworzymy funkcję zamykającą naszą aplikację
						while(true)
							{if(!Aktualizacja.trwa)
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
				imgObrazek=   new BufferedImage (64, 64, BufferedImage.TYPE_INT_RGB);
			
				TrayIcon trayIcon = new TrayIcon(imgObrazek, "PilotPC-PC-Java", popup);
				trayIcon.addActionListener(new ActionListener() {         // tworzymy obiekt ActionListener
					public void actionPerformed(ActionEvent e) {    
						wyswietlanie=TypWyswietlania.Okno;  
		if(glowneOkno==null)
						glowneOkno=new Okno();
		else
			glowneOkno.show();
				} 
				});
				try {
					tray.add(trayIcon);   // dodanie naszej ikony do zasobnika systemowego
					}
					catch (AWTException e) {
					javax.swing.JOptionPane.showMessageDialog(null, "Błąd podczas dodawania ikony do zasobnika systemowego.");            // Wyświetl komunikat
					
					}

					trayIcon.displayMessage ("PilotPC "+wersja, "Serwer został uruchomiony", TrayIcon.MessageType.INFO);  // Wyświetlenie dymka powitalnego.
		}

Timer timer1 = new Timer();
Aktualizacja timer1_task = new Aktualizacja();
timer1.schedule (timer1_task, 0, 3600000);
		polaczenia=new Polaczenie();
	}
}


