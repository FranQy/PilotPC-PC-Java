import java.util.Date;

public class HttpPolaczenie implements PolaczenieInfo {
    public boolean zablokowane = false;
    public UserAgent UserAgent;
    public Okno.Urzadzenie UI = null;

    public Okno.Urzadzenie getUI() {
        return UI;
    }

    public boolean pokazane = false;
    public Date czas = new Date();

    public void rozlacz() {
        zablokowane = true;
    }

    @Override
    public String toString() {
        if (UserAgent.urzadzenie == null)
            return UserAgent.toString();
        else
            return UserAgent.urzadzenie;
    }

    public String opis() {
        String ret = "<html>";
        if (UserAgent.OS != null)
            ret += "System: " + UserAgent.OS;
        if (UserAgent.urzadzenie != null)
            ret += "<br>Model: " + UserAgent.urzadzenie;
        ret += "<br>User-Agent: " + UserAgent + "</html>";
        return ret;
    }
}
