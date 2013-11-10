import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.example.socketclient.TCP_Data;


public class Pilot {
	
	static {
		 if(System.getenv("PROCESSOR_ARCHITECTURE").contentEquals("AMD64"))
	        System.loadLibrary("libpilotpc-x64");
		 else
		        System.loadLibrary("libpilotpc");
	    }
	   // A native method that receives nothing and returns void
	   private native void click(int i);
	   
		   static Robot robot=null;
	   public Pilot()
	   {
			try {
				robot = new Robot();
			} catch (AWTException e2) {
				e2.printStackTrace();
				return;
			}//klasa do emulowania klawiatury itp.
	   }
	   public void click(TCP_Data data)
	   {
		   switch(data.button)
		   {
		   case OFF:
		   {
			   // TODO zrobic dla linuksa i wykrywanie sytemu
			   try {
				Runtime.getRuntime().exec("shutdown -s -t 0");
		        System.exit(0);
			} catch (IOException e) {
				// TODO Komunikat o bledzie
			}
			   break;
		   }
			case BACK:
			{
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				break;
			}
			case DOWN:
			{
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
				break;
			}
			case EXIT:
			{
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);
				break;
			}
			case LEFT:
			{
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.keyRelease(KeyEvent.VK_LEFT);
				break;
			}
			case MULTIMEDIA:
			{
				// TODO obs³uga linuxa i wykrywanie systemu
				Runtime rt = Runtime.getRuntime();//odpala domyœlny odtwarzacz DVD pod windowsem (domyœlnie Windows Media Player)

			    try {
					Process pr = rt.exec(System.getenv("SystemRoot")+"\\System32\\dvdplay.exe");
				} catch (IOException e) {
					// TODO komunikat o bledzie
					
				}
				
				break;
			}
			case MUSIC:
			{//Jak wy¿ej
				Runtime rt = Runtime.getRuntime();
				 
			    try {
					Process pr = rt.exec(System.getenv("SystemRoot")+"\\System32\\dvdplay.exe");
				} catch (IOException e) {
					// TODO komunikat o bledzie
					
				}
				break;
			}
		   case VOLDOWN:
		   {
			   click(data.button.ordinal());//Korzysta z tego samego enuma co TCP_DATA, bo po co robic dwa osobne
			   break;
		   }
		   case VOLUP:
		   {
			   click(data.button.ordinal());
			   break;
		   }
		   case MUTE:
		   {
			   click(data.button.ordinal());
			   break;
		   }
			case NEXT:
			{
				   click(data.button.ordinal());
				break;
			}
			case PERV:
			{
				   click(data.button.ordinal());
				break;
			}
			case PLAYPAUSE:
			{
				   click(data.button.ordinal());
				break;
			}
			case RETTURN:
			{
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				break;
			}
			case RIGHT:
			{
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				break;
			}
			case STOP:
			{
				   click(data.button.ordinal());
				break;
			}
			case UP:
			{
				robot.keyPress(KeyEvent.VK_UP);
				robot.keyRelease(KeyEvent.VK_UP);
				break;
			}
		   }
		   data.clean();
	   }

}
