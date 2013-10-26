import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class Połączenie {
	ServerSocket socServ;
	Socket soc;
	int port=12345;
public Połączenie()
{
	while(true)
	{
	try {
		socServ=new ServerSocket(port);

		System.out.print("Nasłuchiwanie na porcie "+port+"\r\n");
		while(true){
		soc=socServ.accept();
		InputStream is = soc.getInputStream();
		while (true) {
		int n = is.read();
		if (n == -1){

			break;
		}
		else
		System.out.print((char)n);
		}
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
