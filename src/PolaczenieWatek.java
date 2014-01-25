import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    		               boolean nowe=true;

    		is=null;
			  try {
                  if(nowe)
                  {
          		   gotowe=true;
			soc=socServ.accept();
                  byte ileGotowe=0;
                  for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                  {
                      if(Polaczenie.watki[i]!=null){
                          if(!Polaczenie.watki[i].czyPolaczono()&&Polaczenie.watki[i].socServ==socServ)
                              ileGotowe++;
                      }
                  }
                  if(ileGotowe<4)
			for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
			{
				if(Polaczenie.watki[i]==null){
					Polaczenie.watki[i]=new PolaczenieWatek();
					Polaczenie.watki[i].socServ=socServ;
					Polaczenie.watki[i].start();
					break;
				}
			}       }
                       nowe=true;
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
                      System.out.println(Jezyk.napisy[Jezyk.n.Polaczono.ordinal()]+" "+toString());
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

					/*if(UI!=null)
					{
						UI.ramka.remove(UI);
						UI=null;
                        Okno.potrzebneOdswierzenie=true;
					}
				  this.infoPrzyPolaczeniu=null;
					this.pokazane=false;*/
				  try {
					  String wyj="";
						byte liczbaNowychLinii=0;
						while (true) {
					int n = is.read();
						if (n == -1){

							break;
						}
						else
						{
							//robot.keyPress(n+128);
							if(n=='\n'||n=='\r')
								liczbaNowychLinii++;
							else
								liczbaNowychLinii=0;
							wyj+=(char)n;

						}

						if (liczbaNowychLinii==4){

							break;
						}}
						if(wyj.charAt(0)=='/')
						{
						TCP_Data data= HTTP.polaczenie(is, soc, wyj);
						if(data!=null)
							wykonuj(data);
						}
						else
						{
							//pinguje
							OutputStream os =soc.getOutputStream();
							os.write(wyj.getBytes());
							os.close();
                            nowe=false;
						}
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
				  System.out.println(Jezyk.napisy[Jezyk.n.Rozlaczono.ordinal()]+" "+toString());
                  Okno.potrzebneOdswierzenie=true;

				  
			  }catch(Exception e)
			  {
					if(UI!=null)
					{
						UI.ramka.remove(UI);
						UI=null;
                        Okno.potrzebneOdswierzenie=true;
					}
				  this.infoPrzyPolaczeniu=null;
					this.pokazane=false;
					System.out.println("Błąd, rozłączono "+toString());
				  
			  }






            byte ileGotowe=0;
            for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
            {
                if(Polaczenie.watki[i]!=null){
                    if(!Polaczenie.watki[i].czyPolaczono()&&Polaczenie.watki[i].socServ==socServ)
                        ileGotowe++;
                }
            }
            if(ileGotowe>5)
            {
                for(byte i=0;i<100;i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                {
                    if(Polaczenie.watki[i]!=this){
                        Polaczenie.watki[i]=null;
                        break;
                    }
                }
                break;
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
				    if(data.touchpadX!=0||data.touchpadY!=0)
                        MouseRobot.moveTo(data.touchpadX, data.touchpadY);
					MouseRobot.LPM();
					
					break;
				}
				case PPM:
				{

                    if(data.touchpadX!=0||data.touchpadY!=0)
                        MouseRobot.moveTo(data.touchpadX, data.touchpadY);
					MouseRobot.PPM();
					break;
				}
				case NORMAL:
				{
                    MouseRobot.move(data.touchpadX, data.touchpadY); //ruszanie myszka
					break;
				}
				case LONG:
				{
                    MouseRobot.move(true, data.touchpadX, data.touchpadY); //ruszanie ze wcisnietym LPM
					break;
				}
				case UP:
				{
                    MouseRobot.up(); //podniesienie LPM
					break;
				}
				case SCROLL:
				{
                    MouseRobot.scroll(data.touchpadY);
					break;
				}
				}
					 								
			}
            else if(data.type == TCP_Data.typ.PILOT )
            {
                for (byte x = 0; x < HTTP.doWykonania.length; x++) {
                    if (HTTP.doWykonania[x] == null) {
                        HTTP.doWykonania[x]=data;
                        break;
                    }
                }
                Pilot.click(data);
               // System.out.println("pilot");
            }else if(data.type == TCP_Data.typ.KEYBOARD )
            {

                    if(data.key>=65&&data.key<=90)
                    {
                        Program.robot.keyPress(KeyEvent.VK_SHIFT);
                        Program.robot.keyPress(data.key);
                        Program.robot.keyRelease(data.key);
                        Program.robot.keyRelease(KeyEvent.VK_SHIFT);
                    }
                    else if(data.key>=97&&data.key<=122)
                    {
                        Program.robot.keyRelease(KeyEvent.VK_SHIFT);
                        Program.robot.keyPress(data.key-32);
                        Program.robot.keyRelease(data.key-32);
                    }
                    else  if(data.key>=45&&data.key<57||data.key==32||data.key==59||data.key==61||data.key==91||data.key==92||data.key==93)
                    {
                        Program.robot.keyPress(data.key);
                        Program.robot.keyRelease(data.key);

                    }
                    else
                    {
                        Program.robot.keyPress(KeyEvent.VK_ALT);
                        int kod=data.key;
                        int dz=1000000000;
                        while(dz>0)
                        {
                            Program.robot.keyPress(KeyEvent.VK_NUMPAD0+(kod/dz)%10);
                            Program.robot.keyRelease(KeyEvent.VK_NUMPAD0+(kod/dz)%10);
                            dz=dz/10;
                        }
                        Program.robot.keyRelease(KeyEvent.VK_ALT);

                    }

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
    		String ret="<html>IP:"+soc.getRemoteSocketAddress().toString();

    		if(infoPrzyPolaczeniu!=null)
    		ret+="<br/>"+Jezyk.napisy[Jezyk.n.Nazwa.ordinal()]+":"+infoPrzyPolaczeniu.nazwa;
    		return ret+"</html>";
    		
    	}
}