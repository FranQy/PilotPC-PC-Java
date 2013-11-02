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
	Socket soc;
	Robot robot;
	int port=12345;
public Polaczenie()
{
	
	MouseRobot mouse = new MouseRobot();  //klasa do sterowania myszka
	
	while(true)
	{
	try {
		socServ=new ServerSocket(port);

				System.out.print("Nasłuchiwanie na porcie "+port+"\r\n");
		
		while(true){	
			soc=socServ.accept();		
			InputStream is = soc.getInputStream();
		
			  ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
			
			
			  try {
          		        		
          		TCP_Data data;
          		
          		while(true)
          		{
					data = (TCP_Data) in.readObject();
					
						if(data.type == TCP_Data.typ.TOUCHPAD )
						{							
							switch(data.mouse)
							{
							case LPM:
							{
							
								mouse.LPM();
								
								break;
							}
							case PPM:
							{
								
								mouse.PPM();
								break;
							}
							case NORMAL:
							{
								mouse.move(data.touchpadX, data.touchpadY); //ruszanie myszka
								break;
							}
							case LONG:
							{
								// TODO zmiana "algorytmu" ruszania z LPM
								mouse.move(true, data.touchpadX, data.touchpadY); //ruszanie ze wcisnietym LPM
								break;
							}
							case UP:
							{
								mouse.up(); //podniesienie LPM
								break;
							}
							case SCROLL:
							{
								mouse.scroll(data.touchpadY);
								break;
							}
							}
								 								
						}
						data.clean();//czyszczenie zmiennych w TCP_Data
					
          		}
				
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			
			
			
		catch(Exception e)
		{
			HTTP.polaczenie(is, soc);
		}
			System.out.print("Rozłączono \n\r\r\n");}

	} catch (BindException e) {
		System.out.print("Błąd, port "+port+"zajęty\r\n");
		//port++;
		/**
		 * bo teraz przy rozlaczaniu to wywala
		 */
		 try {
				socServ.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}catch(IOException e)
	{

		System.out.print("Błąd z połączeniem\r\n");	
	
	}}
}
}
