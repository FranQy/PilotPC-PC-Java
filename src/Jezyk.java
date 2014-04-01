/**
 * Created by Mateusz on 11.01.14.
 */
public class Jezyk {
    public static String[] napisy = new String[22];

    public static void przeladuj(jezyki a) {
         laduj(a);
        if (Program.glowneOkno != null)
            Program.glowneOkno.ustawJezyk();
        if (Opcje.okno != null)
            Opcje.okno.ustawJezyk();
        Program.tray(false);

    }
     //Jezyk.napisy[Jezyk.n.PilotPCWersja.ordinal()]
    public enum jezyki { Polski, Angielski, Rosyjski };
    public enum n { PilotPCWersja,Pokaz, Zakoncz, BladPodczasDodawaniaIkony,
        SerwerZostalUruchomiony, OProgramiePilotPc,Programisci,Nieznane,Wersja,
        Rozlaczono, Polaczono,Nazwa,BladZPolaczeniem,KodDoPolaczenia,Rozlacz,TwojeIPTo,
        ZmienKod, Infromacje, StartZSystemem, PodlaczoneUrzadzenia, WylaczWygladzanieMyszy, WlaczWygladzanieMyszy
    }

    ;

    public enum nHTTP{ZostalesRozlaczony,KodBledny,Usun,Informacje,Polaczenie,Stan,Jakosc,Host,Coder,Designers, JakoscObrazu, Niska,Srednia,Wysoka,Ultra, Spacja, Polaczono, BrakDanych, Rozlaczono};
    public static String[][] nhttp={
            {"Zostałeś Rozłączony", "Kod błędny", "Usuń", "Informacje", "POŁĄCZENIE", "Stan","Jakość","Host", "CODER", "DESIGNERS","Jakość obrazu","Niska","Średnia","Wysoka","Ultra","Spacja","Połączono", "Brak danych", "Rozłączono"} ,
            {"You have been disconected", "Invalid code", "Backspace", "Information", "Connection", "Condition","Quality","Host", "CODERS", "DESIGNERS","Image quality","Low","Medium","High","Ultra","Space","Connected", "No data", "Disconected"} ,
            {"Вы отсоединили", "Неверный код", "бекспейс", "информации", "ПОДКЛЮЧЕНИЕ", "состояние","Качество","Хост", "ПРОГРАММИСТУ", "ДИЗАЙНЕРЫ","Качество изображения","низкой","среднее","высокое","ультра","Пробел","комбинированный ","нет данных","отключен" }
    };
    public static void laduj(jezyki a)
    {
        switch (a)
        {
            case Polski:
                napisy[n.PilotPCWersja.ordinal()]="PilotPC wersja ";
                napisy[n.Pokaz.ordinal()]="Pokaż";
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
                napisy[n.ZmienKod.ordinal()]="Zmień kod";
                napisy[n.Infromacje.ordinal()] = "Opcje";
                napisy[n.StartZSystemem.ordinal()]="Start z systemem";
                napisy[n.PodlaczoneUrzadzenia.ordinal()]="Podłączone urządzenia";
                napisy[n.WlaczWygladzanieMyszy.ordinal()] = "Włącz wygładzanie myszy";
                napisy[n.WylaczWygladzanieMyszy.ordinal()] = "Wyłącz wygładzanie myszy";
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
                napisy[n.KodDoPolaczenia.ordinal()] = "Authentication code";
                napisy[n.Rozlacz.ordinal()]="Disconnect";
                napisy[n.TwojeIPTo.ordinal()]="Your IP";
                napisy[n.ZmienKod.ordinal()]="New code";
                napisy[n.Infromacje.ordinal()] = "Settings";
                napisy[n.StartZSystemem.ordinal()]="Start with system boot";
                napisy[n.PodlaczoneUrzadzenia.ordinal()]="Connected devices";
                napisy[n.WlaczWygladzanieMyszy.ordinal()] = "Turn on smooth mouse";
                napisy[n.WylaczWygladzanieMyszy.ordinal()] = "Turn off smooth mouse";
                break;

            case Rosyjski:
                napisy[n.PilotPCWersja.ordinal()]="PilotPC версия ";
                napisy[n.Pokaz.ordinal()]="Покажите";
                napisy[n.Zakoncz.ordinal()]="Выключи";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()]="Błąd podczas dodawania ikony do zasobnika systemowego.";
                napisy[n.SerwerZostalUruchomiony.ordinal()]="Serwer został uruchomiony";
                napisy[n.OProgramiePilotPc.ordinal()]="О программе PilotPC";
                napisy[n.Programisci.ordinal()]="Разработчики:";
                napisy[n.Nieznane.ordinal()]="неизвестный";
                napisy[n.Wersja.ordinal()]="версия";
                napisy[n.Rozlaczono.ordinal()]="Отсоединен";
                napisy[n.Polaczono.ordinal()]="Połączono";
                napisy[n.Nazwa.ordinal()]="название";
                napisy[n.BladZPolaczeniem.ordinal()]="Błąd z połączeniem";
                napisy[n.KodDoPolaczenia.ordinal()]="Kod do połączenia";
                napisy[n.Rozlacz.ordinal()]="Rozłącz";
                napisy[n.TwojeIPTo.ordinal()]="ваш IP это";
                napisy[n.ZmienKod.ordinal()]="Изменение кода";
                napisy[n.Infromacje.ordinal()]="О программе";
                napisy[n.StartZSystemem.ordinal()]="Start z systemem";
                napisy[n.PodlaczoneUrzadzenia.ordinal()]="Подключенные устройства";
                napisy[n.WlaczWygladzanieMyszy.ordinal()] = "Włącz wygładzanie myszy";
                napisy[n.WylaczWygladzanieMyszy.ordinal()] = "Wyłącz wygładzanie myszy";
                break;
        }
    }
}
