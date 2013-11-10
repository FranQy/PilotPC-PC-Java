import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class HTTP {
static void polaczenie(InputStream is, Socket soc) throws IOException
{
	byte liczbaNowychLinii=0;
	String wyj="";
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
	if(wyj.indexOf("/")==0)
	{
		OutputStream os =soc.getOutputStream();
		String wysylanie="HTTP/1.1 200 OK\r\nServer: PilotPC\r\nContent-Type: application/xhtml+xml\r\n\r\n"
				+"<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\">	<head>		<title>PilotPC</title></head>"
				+"<style>#menu{position: absolute;bottom: 0;background: gray;margin: 0;left: 0;width: 100%;}#menu li{display:inline;}.karta{position:absolute;left:0;top:0;width:100%;bottom:20px;display:none;}</style>"
				+"<body><div class=\"karta\" id=\"gamepad\"></div><div class=\"karta\" id=\"pilot\"></div><div class=\"karta\" id=\"klawiatura\"></div><div class=\"karta\" style=\"display:block\" ontouchmove=\"alert(event)\" id=\"touchpad\"></div>"
				+"<ul id=\"menu\"><li><img/></li><li><img title=\"gamepad\"/></li><li><img/></li><li><img title=\"touchpad\"/></li></ul>Aplikacja w trakcie pisania :)</body></html>";
		os.write(wysylanie.getBytes());
		os.close();
		is.close();
	}
	//else
			System.out.print(wyj);
}
}
