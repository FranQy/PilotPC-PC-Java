import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.socketclient.TCP_Data;

public class PolaczenieWatek
    extends Thread {
	public ServerSocket socServ;
	public boolean gotowe=false;
	Socket soc;
    	Pilot pilot = new Pilot();
    	MouseRobot mouse = new MouseRobot();
    public void run() {  //klasa do sterowania myszka
    	while(true){	
    		
		
			
    		InputStream is=null;
			  try {
          		   gotowe=true;;     	
			soc=socServ.accept();	

			for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
			{
				if(Polaczenie.watki[i]==null){
					Polaczenie.watki[i]=new PolaczenieWatek();
					Polaczenie.watki[i].socServ=socServ;
					Polaczenie.watki[i].start();
					break;
				}
			}
          		is = soc.getInputStream();
			try{
			  ObjectInputStream in = new ObjectInputStream(soc.getInputStream());	
			  while(true)
        		{
					
				  TCP_Data data = (TCP_Data) in.readObject();
					wykonuj(data);
        		}
			}
			  catch(Exception e)
				{
					try {
						TCP_Data data=HTTP.polaczenie(is, soc);
						if(data!=null)
							wykonuj(data);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			  }catch(Exception e)
			  {
					System.out.print("Błąd, rozłączono. \n\r\r\n");
				  
			  }
					
				 
		
			
			
			
		
    	}}
    	void wykonuj(TCP_Data data)
    	{
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
			else if(data.type == TCP_Data.typ.PILOT )
			{
				pilot.click(data);
				System.out.println("pilot");
			}
			data.clean();//czyszczenie zmiennych w TCP_Data
    	}
}