/**
 * Created by Mateusz on 11.01.14.
 */
public class Jezyk {
    public static String[] napisy=new String[20];

    public static void przeladuj(jezyki a) {
         laduj(a);
                 if(Program.glowneOkno!=null)
                     Program.glowneOkno.ustawJezyk();
Program.tray(false);

    }
     //Jezyk.napisy[Jezyk.n.PilotPCWersja.ordinal()]
    public enum jezyki { Polski, Angielski, Rosyjski };
    public enum n { PilotPCWersja,Pokaz, Zakoncz, BladPodczasDodawaniaIkony,
        SerwerZostalUruchomiony, OProgramiePilotPc,Programisci,Nieznane,Wersja,
        Rozlaczono, Polaczono,Nazwa,BladZPolaczeniem,KodDoPolaczenia,Rozlacz,TwojeIPTo };
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
                napisy[n.OProgramiePilotPc.ordinal()]="O Programie PilotPC";
                napisy[n.Programisci.ordinal()]="Programiści:";
                napisy[n.Nieznane.ordinal()]="Nieznane";
                napisy[n.Wersja.ordinal()]="wersja";
                napisy[n.Rozlaczono.ordinal()]="Rozłączono";
                napisy[n.Polaczono.ordinal()]="Połączono";
                napisy[n.Nazwa.ordinal()]="Nazwa";
                napisy[n.BladZPolaczeniem.ordinal()]="Błąd z połączeniem";
                napisy[n.KodDoPolaczenia.ordinal()]="Kod do połączenia";
                napisy[n.Rozlacz.ordinal()]="Rozłącz";
                napisy[n.TwojeIPTo.ordinal()]="Twoje IP to";
                napisy[n.BladZPolaczeniem.ordinal()]="Błąd z połączeniem";
                napisy[n.BladZPolaczeniem.ordinal()]="Błąd z połączeniem";
                napisy[n.BladZPolaczeniem.ordinal()]="Błąd z połączeniem";
                napisy[n.BladZPolaczeniem.ordinal()]="Błąd z połączeniem";
                napisy[n.BladZPolaczeniem.ordinal()]="Błąd z połączeniem";
                napisy[n.BladZPolaczeniem.ordinal()]="Błąd z połączeniem";
                break;
            case Angielski:
                napisy[n.PilotPCWersja.ordinal()]="PilotPC version ";
                napisy[n.Pokaz.ordinal()]="Show";
                napisy[n.Zakoncz.ordinal()]="Exit";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()]="Error with putting icon to tray";
                napisy[n.SerwerZostalUruchomiony.ordinal()]="Server has started";
                napisy[n.OProgramiePilotPc.ordinal()]="About PilotPC";
                napisy[n.Programisci.ordinal()]="Programmers:";
                napisy[n.Nieznane.ordinal()]="Unknown";
                napisy[n.Wersja.ordinal()]="version";
                napisy[n.Rozlaczono.ordinal()]="Disconnected";
                napisy[n.Polaczono.ordinal()]="Connected";
                napisy[n.Nazwa.ordinal()]="Name";
                napisy[n.BladZPolaczeniem.ordinal()]="Connecting error";
                napisy[n.KodDoPolaczenia.ordinal()]="Autentication code";
                napisy[n.Rozlacz.ordinal()]="Disconnect";
                napisy[n.TwojeIPTo.ordinal()]="Your IP";
                break;
        }
    }
}
