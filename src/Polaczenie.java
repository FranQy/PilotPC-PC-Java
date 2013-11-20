import java.awt.Robot;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;



public class Polaczenie {
	ServerSocket socServ;
	Robot robot;
	int port=12345;
	public static boolean nasluchiwanie=false;
	public static PolaczenieWatek[] watki= new PolaczenieWatek[100];
public Polaczenie()
{
	

	try {
		socServ=new ServerSocket(port);
		nasluchiwanie=true;
				System.out.print("Nasłuchiwanie na porcie "+port+"\r\n");
				for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
				{
					if(watki[i]==null){
						watki[i]=new PolaczenieWatek();
						watki[i].socServ=socServ;
						watki[i].start();
						break;
					}
				}

	} catch (BindException e) {
		System.out.print("Błąd, port "+port+"zajęty\r\n");
		//port++;
		/**
		 * bo teraz przy rozlaczaniu to wywala
		 */
	}catch(IOException e)
	{

		System.out.print("Błąd z połączeniem\r\n");	
	
	}
}
}
