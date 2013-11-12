import java.awt.AWTException;
import java.awt.Robot;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.socketclient.TCP_Data;



public class Polaczenie {
	ServerSocket socServ;
	Robot robot;
	int port=12345;
public Polaczenie()
{

	

	
	try {
		socServ=new ServerSocket(port);

				System.out.print("Nasłuchiwanie na porcie "+port+"\r\n");

		PolaczenieWatek watek=new PolaczenieWatek();
		watek.socServ=socServ;
		watek.start();

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
