import java.util.Date;

/**
 * Pojedynćze urządzenie wyświetlone na liście w oknie wersja dla http
 */
public class HttpPolaczenie implements PolaczenieInfo {
    public boolean zablokowane = false;
    /**
     * Kod wysłany przez przeglądarkę zawiera informacje m.in. o przeglądarce i systemie operacyjnym, a w przypadku telefonu równierz o modelu
     */
    public UserAgent UserAgent;
    public Okno.Urzadzenie UI = null;

    public Okno.Urzadzenie getUI() {
        return UI;
    }

    /**
     * Czy urządzenie jest w tej chwili wyświetlone w oknie serwera
     */
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
            ret += Jezyk.napisy[Jezyk.n.System.ordinal()] + ": " + UserAgent.OS;
        if (UserAgent.urzadzenie != null)
            ret += Jezyk.napisy[Jezyk.n.Model.ordinal()] + "<br>: " + UserAgent.urzadzenie;
        ret += "<br>User-Agent: " + UserAgent + "</html>";
        return ret;
    }
}
