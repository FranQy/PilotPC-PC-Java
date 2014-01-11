/**
 * Created by Mateusz on 11.01.14.
 */
public class Jezyk {
    public static String[] napisy=new String[10];
    public enum jezyki { Polski, Angielski, Rosyjski };
    public enum n { PilotPCWersja,Pokaz, Zakoncz, BladPodczasDodawaniaIkony, SerwerZostalUruchomiony };
    public static void laduj(jezyki a)
    {
        switch (a)
        {
            case Polski:
                napisy[n.PilotPCWersja.ordinal()]="PilotPC wersja ";
                napisy[n.Pokaz.ordinal()]="Pokarz";
                napisy[n.Zakoncz.ordinal()]="Zakończ";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()]="Błąd podczas dodawania ikony do zasobnika systemowego.";
                napisy[n.SerwerZostalUruchomiony.ordinal()]="Serwer został uruchomiony";
                break;
            case Angielski:
                napisy[n.PilotPCWersja.ordinal()]="PilotPC version ";
                napisy[n.Pokaz.ordinal()]="Show";
                napisy[n.Zakoncz.ordinal()]="Exit";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()]="Error with putting icon to tray";
                napisy[n.SerwerZostalUruchomiony.ordinal()]="Server has started";
                break;
        }
    }
}
