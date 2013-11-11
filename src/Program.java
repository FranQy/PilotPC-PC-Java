import java.io.Console;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;
import java.util.Random;
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
		polaczenia=new Polaczenie();
	}
}


