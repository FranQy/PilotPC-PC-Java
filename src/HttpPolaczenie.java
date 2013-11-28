
public class HttpPolaczenie implements PolaczenieInfo {
public String UserAgent;
public Okno.Urzadzenie UI=null;
public Okno.Urzadzenie getUI(){return UI;}
public boolean pokazane=false;

@Override
public String toString()
{
	return UserAgent;
}
public String opis()
{

	return "User-Agent: "+UserAgent+"\r\n";
}
}
