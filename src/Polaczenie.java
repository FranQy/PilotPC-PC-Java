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
			String wyj="";
		InputStream is = soc.getInputStream();
		/*try{

			   ObjectInputStream in = new ObjectInputStream(is);
			   Object o=in.readObject();
		}
		catch(Exception e)*/
		{
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
		if(wyj.indexOf("GET")==0)
		{
			OutputStream os =soc.getOutputStream();
			String wysylanie="HTTP/1.1 200 OK\r\nServer: PilotPC\r\nContent-Type: application/xhtml+xml\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title></head>	<body>Aplikacja w trakcie pisania :)</body></html>";
			os.write(wysylanie.getBytes());
			os.close();
			is.close();
			//soc.close();
		}
		//else
				System.out.print(wyj);}
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
