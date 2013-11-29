
public class HttpPolaczenie implements PolaczenieInfo {
public boolean zablokowane=false;
public String UserAgent;
public Okno.Urzadzenie UI=null;
public Okno.Urzadzenie getUI(){return UI;}
public boolean pokazane=false;
public void rozlacz()
{
	zablokowane=true;
}
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
