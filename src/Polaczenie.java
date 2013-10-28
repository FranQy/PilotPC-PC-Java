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


public class Polaczenie {
	ServerSocket socServ;
	Socket soc;
	Robot robot;
	int port=12345;
public Polaczenie()
{
	try {
		robot = new Robot();
	} catch (AWTException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	while(true)
	{
	try {
		socServ=new ServerSocket(port);

		System.out.print("Nasłuchiwanie na porcie "+port+"\r\n");
		while(true){
		soc=socServ.accept();
		InputStream is = soc.getInputStream();
		try{

			   ObjectInputStream in = new ObjectInputStream(is);
			   Object o=in.readObject();
		}
		catch(Exception e){
		while (true) {
int n = is.read();
		if (n == -1){

			break;
		}
		else
		{
			//robot.keyPress(n+128);
			

				System.out.print((char)n);
		}}}
			System.out.print("Rozłączono \n\r\r\n");}

	} catch (BindException e) {
		System.out.print("Błąd, port "+port+"zajęty\r\n");
		port++;
	}catch(IOException e)
	{

		System.out.print("Błąd z połączeniem\r\n");	
	
	}}
}
}
