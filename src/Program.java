import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.net.Socket;
public class Program {

	/**
	 * @param args
	 */
	static Okno główneOkno;
	static Połączenie połączenia;
	static TypWyświetlania wyświetlanie;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		wyświetlanie=TypWyświetlania.Konsola;
		boolean pomoc=false;
		for(int i=0;i<args.length;i++)
		{
			if(args[i].equalsIgnoreCase("/o"))	
				wyświetlanie=TypWyświetlania.Okno;
			else if(args[i].equals("/?"))	
					pomoc=true;
			
		}
		if(wyświetlanie==TypWyświetlania.Okno)
		{
główneOkno=new Okno();
główneOkno.show();
		}
		if(pomoc){
			String Tekst="To jest pomoc.\n\r\n\rParametry:\n\r /?  Wyświetlenie pomocy\n\r /k  Uruchomienie bez okna (tylko konsola)\n\r\r\n";
			System.out.print(Tekst);;
			//if(wyświetlanie==TypWyświetlania.Okno)
				//Mess
		}
		
		połączenia=new Połączenie();

	}
}


