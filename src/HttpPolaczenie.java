
public class HttpPolaczenie implements PolaczenieInfo {
public boolean zablokowane=false;
public UserAgent UserAgent;
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
	if(UserAgent.urzadzenie==null)
	return UserAgent.toString();
	else
		return UserAgent.urzadzenie;
}
public String opis()
{
String ret="";
if(UserAgent.OS!=null)
ret+="System: "+UserAgent.OS;
if(UserAgent.urzadzenie!=null)
ret+="\r\nModel: "+UserAgent.urzadzenie;
ret+="\n\rUser-Agent: "+UserAgent;
	return ret;
}
}
