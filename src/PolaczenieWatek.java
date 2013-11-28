import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.socketclient.Connect;
import com.example.socketclient.TCP_Data;

public class PolaczenieWatek
    extends Thread  implements PolaczenieInfo{
	public ServerSocket socServ;
	public boolean gotowe=false;
	Socket soc;
    	Pilot pilot = new Pilot();
    	MouseRobot mouse = new MouseRobot();
    	InputStream is;
    	public Okno.Urzadzenie UI=null;
    	public Okno.Urzadzenie getUI(){return UI;}
    	public boolean pokazane=false;
    			public Connect infoPrzyPolaczeniu;
    			public void rozlacz()
    			{
    				try {
						soc.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    public void run() {  //klasa do sterowania myszka
    	while(true){	
    		
		
			
    		is=null;
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

				  Object dataObject =  in.readObject();
				  if(dataObject.getClass()==Connect.class)
				  { 
					  infoPrzyPolaczeniu=(Connect)dataObject;
					  Connect odpowiedz=new Connect();
					  if(infoPrzyPolaczeniu.haslo.length()==0)
					  odpowiedz.status=Connect.Status.ok;
					  else if(infoPrzyPolaczeniu.haslo.compareTo(Program.ustawienia.haslo)==0)
						  odpowiedz.status=Connect.Status.ok;
					  else
					  {	  
						  odpowiedz.status=Connect.Status.zlyKod;
						  is.close();
						  break;
					  }
					  odpowiedz.nazwa=java.net.InetAddress.getLocalHost().getHostName();
ObjectOutputStream oos= new ObjectOutputStream(soc.getOutputStream());
oos.writeObject(odpowiedz);
oos.flush();
				 // dataObject =  in.readObject();
				  }
				  else
				  {
				  TCP_Data data=(TCP_Data) dataObject;
					  
				  //TCP_Data data = (TCP_Data) in.readObject();
					wykonuj(data);
				  }
        		}
			}
			  catch(StreamCorruptedException e)
				{

					if(UI!=null)
					{
						UI.ramka.remove(UI);
						UI=null;
					}
				  this.infoPrzyPolaczeniu=null;
					this.pokazane=false;
				  try {
						TCP_Data data=HTTP.polaczenie(is, soc);
						if(data!=null)
							wykonuj(data);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			  }catch(EOFException e)
			  {
					if(UI!=null)
					{
						UI.ramka.remove(UI);
						UI=null;
					}
				  this.infoPrzyPolaczeniu=null;
					this.pokazane=false;
				  System.out.print("Rozłączono. \n\r\r\n");
					
				  
			  }catch(Exception e)
			  {
					if(UI!=null)
					{
						UI.ramka.remove(UI);
						UI=null;
					}
				  this.infoPrzyPolaczeniu=null;
					this.pokazane=false;
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
    	/**
    	 * 
    	 * @return Adres IP podłączonego telefonu
    	 */
    	public String getIP()
    	{
    		return soc.getRemoteSocketAddress().toString();
    	}
    	public boolean czyPolaczono()
    	{
    		if(is!=null)
    		return true;
    		else
    			return false;
    	}
    	@Override
    	public String toString()
    	{
    		if(infoPrzyPolaczeniu!=null)
    		return infoPrzyPolaczeniu.nazwa;
    		else
    			return soc.getRemoteSocketAddress().toString();
    	}
    	public String opis()
    	{
    		String ret="IP:"+soc.getRemoteSocketAddress().toString();

    		if(infoPrzyPolaczeniu!=null)
    		ret+="\r\nNazwa:"+infoPrzyPolaczeniu.nazwa;
    		return ret;
    		
    	}
}