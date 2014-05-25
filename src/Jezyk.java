/**
 * Created by Mateusz on 11.01.14.
 */
//Jezyk.napisy[Jezyk.n.PilotPCWersja.ordinal()]
public class Jezyk {
    public static String[] napisy = new String[50];

    public static void przeladuj(jezyki a) {
        laduj(a);
        if (Program.glowneOkno != null)
            Program.glowneOkno.ustawJezyk();
        if (Opcje.okno != null)
            Opcje.okno.ustawJezyk();
        Program.tray(false);

    }

    //Jezyk.napisy[Jezyk.n.PilotPCWersja.ordinal()]
    public enum jezyki {
        Polski, Angielski, Rosyjski
    }

    ;

    public enum n {
        PilotPCWersja, Pokaz, Zakoncz, BladPodczasDodawaniaIkony,
        SerwerZostalUruchomiony, OProgramiePilotPc, Programisci, Nieznane, Wersja,
        Rozlaczono, Polaczono, Nazwa, BladZPolaczeniem, KodDoPolaczenia, Rozlacz, TwojeIPTo,
        ZmienKod, Infromacje, StartZSystemem, PodlaczoneUrzadzenia, WylaczWygladzanieMyszy,
        WlaczWygladzanieMyszy, AktualizacjaZostanie, TrwaAktualizowanie, ProgramAktualny, MoznaZaktualizowac,
        TrwaSprawdzanieAKtualizacji, BladZLadowaniemBiblioteki, System, Model,
        DostepnePolecenia, NowyKod, NieMoznaZmienic, NieMoznaPokazac, NieznanePolecenie, BladRozlaczono, Opcje, DodatekDoPrzegladarki, BrakPodlaczonychUrzadzen, DodatekDoPrzegladarkiTak, DodatekDoPrzegladarkiNie, WylaczSerwer, PowiekszQR, WpiszPomoc
    }

    ;
    public static final String[] nazwyJezykow = {"Polski", "English", "R"};

    public enum nHTTP {ZostalesRozlaczony, KodBledny, Usun, Informacje, Polaczenie, Stan, Jakosc, Host, Coder, Designers, JakoscObrazu, Niska, Srednia, Wysoka, Ultra, Spacja, Polaczono, BrakDanych, Rozlaczono}

    ;
    public static final String[][] nhttp = {
            {"Zostałeś Rozłączony", "Kod błędny", "Usuń", "Informacje", "POŁĄCZENIE", "Stan", "Jakość", "Host", "CODER", "DESIGNERS", "Jakość obrazu", "Niska", "Średnia", "Wysoka", "Ultra", "Spacja", "Połączono", "Brak danych", "Rozłączono"},
            {"You have been disconected", "Invalid code", "Backspace", "Information", "Connection", "Condition", "Quality", "Host", "CODERS", "DESIGNERS", "Image quality", "Low", "Medium", "High", "Ultra", "Space", "Connected", "No data", "Disconected"},
            {"Вы отсоединили", "Неверный код", "бекспейс", "информации", "ПОДКЛЮЧЕНИЕ", "состояние", "Качество", "Хост", "ПРОГРАММИСТУ", "ДИЗАЙНЕРЫ", "Качество изображения", "низкой", "среднее", "высокое", "ультра", "Пробел", "комбинированный ", "нет данных", "отключен"}
    };

    public static void laduj(jezyki a) {
        switch (a) {
            case Polski:
                napisy[n.PilotPCWersja.ordinal()] = "PilotPC wersja ";
                napisy[n.Pokaz.ordinal()] = "Pokaż";
                napisy[n.Zakoncz.ordinal()] = "Zakończ";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()] = "Błąd podczas dodawania ikony do zasobnika systemowego.";
                napisy[n.SerwerZostalUruchomiony.ordinal()] = "Serwer został uruchomiony";
                napisy[n.OProgramiePilotPc.ordinal()] = "O Programie PilotPC";
                napisy[n.Programisci.ordinal()] = "Programiści:";
                napisy[n.Nieznane.ordinal()] = "Nieznane";
                napisy[n.Wersja.ordinal()] = "wersja";
                napisy[n.Rozlaczono.ordinal()] = "Rozłączono";
                napisy[n.Polaczono.ordinal()] = "Połączono";
                napisy[n.Nazwa.ordinal()] = "Nazwa";
                napisy[n.BladZPolaczeniem.ordinal()] = "Błąd z połączeniem";
                napisy[n.KodDoPolaczenia.ordinal()] = "Kod";
                napisy[n.Rozlacz.ordinal()] = "Rozłącz";
                napisy[n.TwojeIPTo.ordinal()] = "IP";
                napisy[n.ZmienKod.ordinal()] = "Zmień";
                napisy[n.Infromacje.ordinal()] = "Opcje";
                napisy[n.StartZSystemem.ordinal()] = "Start z systemem";
                napisy[n.PodlaczoneUrzadzenia.ordinal()] = "Podłączone urządzenia";
                napisy[n.WlaczWygladzanieMyszy.ordinal()] = "Włącz wygładzanie myszy";
                napisy[n.WylaczWygladzanieMyszy.ordinal()] = "Wyłącz wygładzanie myszy";
                napisy[n.AktualizacjaZostanie.ordinal()] = "Aktualizacja zostanie zainstalowana po ponownym uruchomieniu";
                napisy[n.TrwaAktualizowanie.ordinal()] = "Pobieranie aktualizacji...";
                napisy[n.TrwaSprawdzanieAKtualizacji.ordinal()] = "Sprawdzanie aktualizacji...";
                napisy[n.ProgramAktualny.ordinal()] = "Program aktualny";
                napisy[n.MoznaZaktualizowac.ordinal()] = "Można zaktualizować do wersji";
                napisy[n.BladZLadowaniemBiblioteki.ordinal()] = "Błąd z ładowaniem biblioteki! Niektóre elementy programu nie będą działały poprawnie.";

                napisy[n.System.ordinal()] = "System";
                napisy[n.Model.ordinal()] = "Model";
                napisy[n.NowyKod.ordinal()] = "Nowy kod";
                napisy[n.NieMoznaPokazac.ordinal()] = "Nie można pokazać";
                napisy[n.NieMoznaZmienic.ordinal()] = "Nie można zmienić";
                napisy[n.NieznanePolecenie.ordinal()] = "Nieznane polecenie";
                napisy[n.WpiszPomoc.ordinal()] = "wpisz pomoc";
                //napisy[n.PoleceniaKonsola.ordinal()] = "Polecenia:\r\n  zmień kod";
                napisy[n.DostepnePolecenia.ordinal()] = "Dostępne polecenia:\r\n  zmień kod\r\n  pokarz kod\r\n  pokarz okno\r\n  zamknij";
                napisy[n.BladRozlaczono.ordinal()] = "Błąd, rozłączono";

                napisy[n.Opcje.ordinal()] = "Opcje";
                napisy[n.DodatekDoPrzegladarkiTak.ordinal()] = "Dodatek do przeglądraki: tak";
                napisy[n.DodatekDoPrzegladarkiNie.ordinal()] = "Dodatek do przeglądraki: nie";
                napisy[n.BrakPodlaczonychUrzadzen.ordinal()] = "Brak podłączonych urządzeń";
                napisy[n.WylaczSerwer.ordinal()] = "Wyłącz serwer";
                napisy[n.PowiekszQR.ordinal()] = "Powiększ QRCode";

                break;
            case Angielski:
                napisy[n.PilotPCWersja.ordinal()] = "PilotPC version ";
                napisy[n.Pokaz.ordinal()] = "Show";
                napisy[n.Zakoncz.ordinal()] = "Exit";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()] = "Error with putting icon to tray";
                napisy[n.SerwerZostalUruchomiony.ordinal()] = "Server has started";
                napisy[n.OProgramiePilotPc.ordinal()] = "About PilotPC";
                napisy[n.Programisci.ordinal()] = "Programmers:";
                napisy[n.Nieznane.ordinal()] = "Unknown";
                napisy[n.Wersja.ordinal()] = "version";
                napisy[n.Rozlaczono.ordinal()] = "Disconnected";
                napisy[n.Polaczono.ordinal()] = "Connected";
                napisy[n.Nazwa.ordinal()] = "Name";
                napisy[n.BladZPolaczeniem.ordinal()] = "Connecting error";
                napisy[n.KodDoPolaczenia.ordinal()] = "Authentication code";
                napisy[n.Rozlacz.ordinal()] = "Disconnect";
                napisy[n.TwojeIPTo.ordinal()] = "Your IP";
                napisy[n.ZmienKod.ordinal()] = "New code";
                napisy[n.Infromacje.ordinal()] = "Settings";
                napisy[n.StartZSystemem.ordinal()] = "Start with system boot";
                napisy[n.PodlaczoneUrzadzenia.ordinal()] = "Connected devices";
                napisy[n.WlaczWygladzanieMyszy.ordinal()] = "Turn on smooth mouse";
                napisy[n.WylaczWygladzanieMyszy.ordinal()] = "Turn off smooth mouse";
                napisy[n.AktualizacjaZostanie.ordinal()] = "Update will be installed after application reboot";
                napisy[n.TrwaAktualizowanie.ordinal()] = "Update is being downloaded...";
                napisy[n.TrwaSprawdzanieAKtualizacji.ordinal()] = "Checking update...";
                napisy[n.ProgramAktualny.ordinal()] = "Program is up to date";
                napisy[n.MoznaZaktualizowac.ordinal()] = "Update avalibe";

                napisy[n.BladZLadowaniemBiblioteki.ordinal()] = "Library loading error!.";

                napisy[n.System.ordinal()] = "System";
                napisy[n.Model.ordinal()] = "Model";
                napisy[n.NowyKod.ordinal()] = "New code";
                napisy[n.NieMoznaPokazac.ordinal()] = "You cannot show";
                napisy[n.NieMoznaZmienic.ordinal()] = "You cannot change";
                napisy[n.NieznanePolecenie.ordinal()] = "Unknown command";
                napisy[n.WpiszPomoc.ordinal()] = "type help";
                //napisy[n.PoleceniaKonsola.ordinal()] = "Polecenia:\r\n  zmień kod";
                napisy[n.DostepnePolecenia.ordinal()] = "Avaible commands:\r\n  change code\r\n  show code\r\n  show window\r\n  exit";

                napisy[n.Opcje.ordinal()] = "Options";
                napisy[n.DodatekDoPrzegladarkiTak.ordinal()] = "Browser addon: yes";
                napisy[n.DodatekDoPrzegladarkiNie.ordinal()] = "Browser addon: no";
                napisy[n.BrakPodlaczonychUrzadzen.ordinal()] = "No conected devices";
                napisy[n.WylaczSerwer.ordinal()] = "Close server";
                napisy[n.PowiekszQR.ordinal()] = "Zoom QRCode";
                break;

            case Rosyjski:
                napisy[n.PilotPCWersja.ordinal()] = "PilotPC версия ";
                napisy[n.Pokaz.ordinal()] = "Покажите";
                napisy[n.Zakoncz.ordinal()] = "Выключи";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()] = "Błąd podczas dodawania ikony do zasobnika systemowego.";
                napisy[n.SerwerZostalUruchomiony.ordinal()] = "Serwer został uruchomiony";
                napisy[n.OProgramiePilotPc.ordinal()] = "О программе PilotPC";
                napisy[n.Programisci.ordinal()] = "Разработчики:";
                napisy[n.Nieznane.ordinal()] = "неизвестный";
                napisy[n.Wersja.ordinal()] = "версия";
                napisy[n.Rozlaczono.ordinal()] = "Отсоединен";
                napisy[n.Polaczono.ordinal()] = "Połączono";
                napisy[n.Nazwa.ordinal()] = "название";
                napisy[n.BladZPolaczeniem.ordinal()] = "Błąd z połączeniem";
                napisy[n.KodDoPolaczenia.ordinal()] = "Kod do połączenia";
                napisy[n.Rozlacz.ordinal()] = "Rozłącz";
                napisy[n.TwojeIPTo.ordinal()] = "ваш IP это";
                napisy[n.ZmienKod.ordinal()] = "Изменение кода";
                napisy[n.Infromacje.ordinal()] = "О программе";
                napisy[n.StartZSystemem.ordinal()] = "Start z systemem";
                napisy[n.PodlaczoneUrzadzenia.ordinal()] = "Подключенные устройства";
                napisy[n.WlaczWygladzanieMyszy.ordinal()] = "Włącz wygładzanie myszy";
                napisy[n.WylaczWygladzanieMyszy.ordinal()] = "Wyłącz wygładzanie myszy";
                napisy[n.AktualizacjaZostanie.ordinal()] = "Aktualizacja zostanie zainstalowana po ponownym uruchomieniu";
                napisy[n.TrwaAktualizowanie.ordinal()] = "Pobieranie aktualizacji...";
                napisy[n.TrwaSprawdzanieAKtualizacji.ordinal()] = "Sprawdzanie aktualizacji...";
                napisy[n.ProgramAktualny.ordinal()] = "Program aktualny";
                napisy[n.MoznaZaktualizowac.ordinal()] = "Można zaktualizować do wersji";

                napisy[n.BladZLadowaniemBiblioteki.ordinal()] = "Błąd z ładowaniem biblioteki! Niektóre elementy programu nie będą działały poprawnie.";

                napisy[n.System.ordinal()] = "System";
                napisy[n.Model.ordinal()] = "Model";
                napisy[n.NowyKod.ordinal()] = "Nowy kod";
                napisy[n.NieMoznaPokazac.ordinal()] = "Nie można pokazać";
                napisy[n.NieMoznaZmienic.ordinal()] = "Nie można zmienić";
                napisy[n.NieznanePolecenie.ordinal()] = "Nieznane polecenie";
                napisy[n.WpiszPomoc.ordinal()] = "wpisz pomoc";
                //napisy[n.PoleceniaKonsola.ordinal()] = "Polecenia:\r\n  zmień kod";
                napisy[n.DostepnePolecenia.ordinal()] = "Dostępne polecenia:\r\n  zmień kod\r\n  pokarz kod\r\n  pokarz okno\r\n  zamknij";

                napisy[n.Opcje.ordinal()] = "Opcje";
                napisy[n.DodatekDoPrzegladarkiTak.ordinal()] = "Dodatek do przeglądraki: tak";
                napisy[n.DodatekDoPrzegladarkiNie.ordinal()] = "Dodatek do przeglądraki: nie";
                napisy[n.BrakPodlaczonychUrzadzen.ordinal()] = "Brak podłączonych urządzeń";
                napisy[n.WylaczSerwer.ordinal()] = "Wyłącz serwer";
                napisy[n.PowiekszQR.ordinal()] = "Powiększ QRCode";
                break;
        }
    }
}
