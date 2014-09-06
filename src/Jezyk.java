/**
 * Klasa odpowiedzialna za tłumaczenia na różne języki
 */
//Jezyk.napisy[Jezyk.n.PilotPCWersja.ordinal()]
public class Jezyk {
    public static String[] napisy = new String[100];

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
        Polski, Angielski, Rosyjski, Esperanto
    }

    ;

    public enum n {
        PilotPCWersja, Pokaz, Zakoncz, BladPodczasDodawaniaIkony,
        SerwerZostalUruchomiony, OProgramiePilotPc, Programisci, Nieznane, Wersja,
        Rozlaczono, Polaczono, Nazwa, BladZPolaczeniem, KodDoPolaczenia, Rozlacz, TwojeIPTo,
        ZmienKod, Infromacje, StartZSystemem, PodlaczoneUrzadzenia, WylaczWygladzanieMyszy,
        WlaczWygladzanieMyszy, AktualizacjaZostanie, TrwaAktualizowanie, ProgramAktualny, MoznaZaktualizowac,
        TrwaSprawdzanieAKtualizacji, BladZLadowaniemBiblioteki, System, Model,
        DostepnePolecenia, NowyKod, NieMoznaZmienic, NieMoznaPokazac, NieznanePolecenie, BladRozlaczono, Opcje, DodatekDoPrzegladarki, BrakPodlaczonychUrzadzen, DodatekDoPrzegladarkiTak, DodatekDoPrzegladarkiNie, WylaczSerwer, PowiekszQR, AktualizujTeraz, AktualizacjaAutomatyzna, WpiszPomoc,
        TworzenieSieciWiFi, Haslo, Start, Stop, Zaktualizowano, DostepnaNowa, Blad
    }

    ;
    public static final String[] nazwyJezykow = {"Polski", "English", "Русский", "Esperanto"};

    public enum nHTTP {ZostalesRozlaczony, KodBledny, Usun, Informacje, Polaczenie, Stan, Jakosc, Host, Coder, Designers, JakoscObrazu, Niska, Srednia, Wysoka, Ultra, Spacja, Polaczono, BrakDanych, Rozlaczono}

    ;
    public static final String[][] nhttp = {
            {"Zostałeś Rozłączony", "Kod błędny", "Usuń", "Informacje", "POŁĄCZENIE", "Stan", "Jakość", "Host", "CODER", "DESIGNERS", "Jakość obrazu", "Niska", "Średnia", "Wysoka", "Ultra", "Spacja", "Połączono", "Brak danych", "Rozłączono"},
            {"You have been disconected", "Invalid code", "Backspace", "Information", "Connection", "Condition", "Quality", "Host", "CODERS", "DESIGNERS", "Image quality", "Low", "Medium", "High", "Ultra", "Space", "Connected", "No data", "Disconected"},
            {"Вы отсоединили", "Неверный код", "бекспейс", "информации", "ПОДКЛЮЧЕНИЕ", "состояние", "Качество", "Хост", "ПРОГРАММИСТУ", "ДИЗАЙНЕРЫ", "Качество изображения", "низкой", "среднее", "высокое", "ультра", "Пробел", "комбинированный ", "нет данных", "отключен"}
    };

    /**
     * ładuje do tablicy napisy różne teksty w wybranym języku
     *
     * @param a język do załadowania
     */
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
                napisy[n.BladZLadowaniemBiblioteki.ordinal()] = "Błąd z ładowaniem plików! Niektóre elementy programu nie będą działały poprawnie.";

                napisy[n.System.ordinal()] = "System";
                napisy[n.Model.ordinal()] = "Model";
                napisy[n.NowyKod.ordinal()] = "Nowy kod";
                napisy[n.NieMoznaPokazac.ordinal()] = "Nie można pokazać";
                napisy[n.NieMoznaZmienic.ordinal()] = "Nie można zmienić";
                napisy[n.NieznanePolecenie.ordinal()] = "Nieznane polecenie";
                napisy[n.WpiszPomoc.ordinal()] = "napisz: pomoc";
                //napisy[n.PoleceniaKonsola.ordinal()] = "Polecenia:\r\n  zmień kod";
                napisy[n.DostepnePolecenia.ordinal()] = "Dostępne polecenia:\r\n  zmień kod\r\n  pokaż kod\r\n  pokaż okno\r\n  zamknij";
                napisy[n.BladRozlaczono.ordinal()] = "Błąd, rozłączono";

                napisy[n.Opcje.ordinal()] = "Opcje";
                napisy[n.DodatekDoPrzegladarkiTak.ordinal()] = "Dodatek do przeglądarki: tak";
                napisy[n.DodatekDoPrzegladarkiNie.ordinal()] = "Dodatek do przeglądarki: nie";
                napisy[n.BrakPodlaczonychUrzadzen.ordinal()] = "Brak podłączonych urządzeń";
                napisy[n.WylaczSerwer.ordinal()] = "Wyłącz serwer";
                napisy[n.PowiekszQR.ordinal()] = "Powiększ QRCode";
                napisy[n.AktualizujTeraz.ordinal()] = "Aktualizuj teraz";
                napisy[n.AktualizacjaAutomatyzna.ordinal()] = "Aktualizacja automatyzna";

                napisy[n.TworzenieSieciWiFi.ordinal()] = "Tworzenie sieci WiFi";
                napisy[n.Nazwa.ordinal()] = "Nazwa";
                napisy[n.Haslo.ordinal()] = "Hasło";
                napisy[n.Start.ordinal()] = "Start";
                napisy[n.Stop.ordinal()] = "Stop";
                napisy[n.Blad.ordinal()] = "Błąd";
                napisy[n.Zaktualizowano.ordinal()] = "Zaktualizowano! Czy uruchomić ponownie program PilotPC?";
                napisy[n.DostepnaNowa.ordinal()] = "Dostępna jest nowa wersja programu PilotPC. Czy chcesz zaktualizować?";

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

                napisy[n.BladZLadowaniemBiblioteki.ordinal()] = "File loading error!.";

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
                napisy[n.AktualizujTeraz.ordinal()] = "Update now";
                napisy[n.AktualizacjaAutomatyzna.ordinal()] = "Autoupdate";
                napisy[n.TworzenieSieciWiFi.ordinal()] = "Create a WiFi network";
                napisy[n.Nazwa.ordinal()] = "Name";
                napisy[n.Haslo.ordinal()] = "Password";
                napisy[n.Start.ordinal()] = "Start";
                napisy[n.Stop.ordinal()] = "Stop";
                napisy[n.Blad.ordinal()] = "Error";
                napisy[n.Zaktualizowano.ordinal()] = "PilotPC have been updated! Do you want to reset PilotPC?";
                napisy[n.DostepnaNowa.ordinal()] = "Update avalible. Do you want to download update?";
                break;

            case Rosyjski:
                napisy[n.PilotPCWersja.ordinal()] = "PilotPC версия ";
                napisy[n.Pokaz.ordinal()] = "Покажите";
                napisy[n.Zakoncz.ordinal()] = "конец";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()] = "Ошибка добавления иконку в системный трей.";
                napisy[n.SerwerZostalUruchomiony.ordinal()] = "Сервер работает";
                napisy[n.OProgramiePilotPc.ordinal()] = "О программе PilotPC";
                napisy[n.Programisci.ordinal()] = "Разработчики:";
                napisy[n.Nieznane.ordinal()] = "неизвестный";
                napisy[n.Wersja.ordinal()] = "версия";
                napisy[n.Rozlaczono.ordinal()] = "Отсоединен";
                napisy[n.Polaczono.ordinal()] = "подключенный";
                napisy[n.Nazwa.ordinal()] = "название";
                napisy[n.BladZPolaczeniem.ordinal()] = "Ошибка при подключении";
                napisy[n.KodDoPolaczenia.ordinal()] = "Код для подключения";
                napisy[n.Rozlacz.ordinal()] = "отключите";
                napisy[n.TwojeIPTo.ordinal()] = "ваш IP это";
                napisy[n.ZmienKod.ordinal()] = "Изменение кода";
                napisy[n.Infromacje.ordinal()] = "О программе";
                napisy[n.StartZSystemem.ordinal()] = "начать с операционной системой";
                napisy[n.PodlaczoneUrzadzenia.ordinal()] = "Подключенные устройства";
                napisy[n.WlaczWygladzanieMyszy.ordinal()] = "включить гладкой мыши";
                napisy[n.WylaczWygladzanieMyszy.ordinal()] = "отключить гладкой мышь";
                napisy[n.AktualizacjaZostanie.ordinal()] = "Обновление будет установлено после перезагрузки";
                napisy[n.TrwaAktualizowanie.ordinal()] = "получать обновления...";
                napisy[n.TrwaSprawdzanieAKtualizacji.ordinal()] = "роверки обновлений...";
                napisy[n.ProgramAktualny.ordinal()] = "Программа находится в актуальном состоянии";
                napisy[n.MoznaZaktualizowac.ordinal()] = "Вы можете обновить до версии";

                napisy[n.BladZLadowaniemBiblioteki.ordinal()] = "Ошибка с загрузкой! Некоторые элементы программы не будет работать должным образом.";

                napisy[n.System.ordinal()] = "операционная система";
                napisy[n.Model.ordinal()] = "Модель";
                napisy[n.NowyKod.ordinal()] = "новый код";
                napisy[n.NieMoznaPokazac.ordinal()] = "Я не могу показать";
                napisy[n.NieMoznaZmienic.ordinal()] = "Я не могу изменить";
                napisy[n.NieznanePolecenie.ordinal()] = "Неизвестная команда";
                napisy[n.WpiszPomoc.ordinal()] = "напишите: помощь";
                //napisy[n.PoleceniaKonsola.ordinal()] = "Polecenia:\r\n  zmień kod";
                napisy[n.DostepnePolecenia.ordinal()] = "Доступные команды:\r\n  измените код\r\n  покажите код\r\n  покажите окно\r\n  конец";

                napisy[n.Opcje.ordinal()] = "Параметры";
                napisy[n.DodatekDoPrzegladarkiTak.ordinal()] = "Браузер дополнения: да";
                napisy[n.DodatekDoPrzegladarkiNie.ordinal()] = "Браузер дополнения: нет";
                napisy[n.BrakPodlaczonychUrzadzen.ordinal()] = "Нет устройство, подключенное";
                napisy[n.WylaczSerwer.ordinal()] = "Выключите сервер";
                napisy[n.PowiekszQR.ordinal()] = "Увеличий QRCode";
                napisy[n.AktualizujTeraz.ordinal()] = "обновить сейчас";
                napisy[n.AktualizacjaAutomatyzna.ordinal()] = "Автоматическое обновление";
                napisy[n.TworzenieSieciWiFi.ordinal()] = "Создать сеть WiFi";
                napisy[n.Nazwa.ordinal()] = "название";
                napisy[n.Haslo.ordinal()] = "пароль";
                napisy[n.Start.ordinal()] = "начало";
                napisy[n.Stop.ordinal()] = "остановить";
                napisy[n.Blad.ordinal()] = "Błąd";
                napisy[n.Zaktualizowano.ordinal()] = "Zaktualizowano! Czy uruchomić ponownie program PilotPC?";
                napisy[n.DostepnaNowa.ordinal()] = "Dostępna jest nowa wersja programu PilotPC. Czy chcesz zaktualizować?";
                break;

            case Esperanto:
                napisy[n.PilotPCWersja.ordinal()] = "PilotPC versio ";
                napisy[n.Pokaz.ordinal()] = "Vidigu";
                napisy[n.Zakoncz.ordinal()] = "Finu";
                napisy[n.BladPodczasDodawaniaIkony.ordinal()] = "Błąd podczas dodawania ikony do zasobnika systemowego.";
                napisy[n.SerwerZostalUruchomiony.ordinal()] = "La servilo funkcias";
                napisy[n.OProgramiePilotPc.ordinal()] = "Pri la PilotPC";
                napisy[n.Programisci.ordinal()] = "Programistoj:";
                napisy[n.Nieznane.ordinal()] = "Nekonata";
                napisy[n.Wersja.ordinal()] = "versio";
                napisy[n.Rozlaczono.ordinal()] = "Malkonektita";
                napisy[n.Polaczono.ordinal()] = "Konektita";
                napisy[n.Nazwa.ordinal()] = "Nazwa";
                napisy[n.BladZPolaczeniem.ordinal()] = "Eraro dum konektanta";
                napisy[n.KodDoPolaczenia.ordinal()] = "Kodo";
                napisy[n.Rozlacz.ordinal()] = "Malkonektu";
                napisy[n.TwojeIPTo.ordinal()] = "IP";
                napisy[n.ZmienKod.ordinal()] = "ŝanĝi";
                napisy[n.Infromacje.ordinal()] = "Agordojn";
                napisy[n.StartZSystemem.ordinal()] = "Start z systemem";
                napisy[n.PodlaczoneUrzadzenia.ordinal()] = "Podłączone urządzenia";
                napisy[n.WlaczWygladzanieMyszy.ordinal()] = "Włącz wygładzanie myszy";
                napisy[n.WylaczWygladzanieMyszy.ordinal()] = "Wyłącz wygładzanie myszy";
                napisy[n.AktualizacjaZostanie.ordinal()] = "Aktualizacja zostanie zainstalowana po ponownym uruchomieniu";
                napisy[n.TrwaAktualizowanie.ordinal()] = "Pobieranie aktualizacji...";
                napisy[n.TrwaSprawdzanieAKtualizacji.ordinal()] = "Sprawdzanie aktualizacji...";
                napisy[n.ProgramAktualny.ordinal()] = "Program aktualny";
                napisy[n.MoznaZaktualizowac.ordinal()] = "Można zaktualizować do wersji";
                napisy[n.BladZLadowaniemBiblioteki.ordinal()] = "Błąd z ładowaniem plików! Niektóre elementy programu nie będą działały poprawnie.";

                napisy[n.System.ordinal()] = "System";
                napisy[n.Model.ordinal()] = "Model";
                napisy[n.NowyKod.ordinal()] = "Nowy kod";
                napisy[n.NieMoznaPokazac.ordinal()] = "Nie można pokazać";
                napisy[n.NieMoznaZmienic.ordinal()] = "Nie można zmienić";
                napisy[n.NieznanePolecenie.ordinal()] = "Nieznane polecenie";
                napisy[n.WpiszPomoc.ordinal()] = "napisz: pomoc";
                //napisy[n.PoleceniaKonsola.ordinal()] = "Polecenia:\r\n  zmień kod";
                napisy[n.DostepnePolecenia.ordinal()] = "Dostępne polecenia:\r\n  zmień kod\r\n  pokaż kod\r\n  pokaż okno\r\n  zamknij";
                napisy[n.BladRozlaczono.ordinal()] = "Błąd, rozłączono";

                napisy[n.Opcje.ordinal()] = "Opcje";
                napisy[n.DodatekDoPrzegladarkiTak.ordinal()] = "Dodatek do przeglądarki: tak";
                napisy[n.DodatekDoPrzegladarkiNie.ordinal()] = "Dodatek do przeglądarki: nie";
                napisy[n.BrakPodlaczonychUrzadzen.ordinal()] = "Brak podłączonych urządzeń";
                napisy[n.WylaczSerwer.ordinal()] = "Wyłącz serwer";
                napisy[n.PowiekszQR.ordinal()] = "Powiększ QRCode";
                napisy[n.AktualizujTeraz.ordinal()] = "Aktualizuj teraz";
                napisy[n.AktualizacjaAutomatyzna.ordinal()] = "Aktualizacja automatyzna";

                napisy[n.TworzenieSieciWiFi.ordinal()] = "Tworzenie sieci WiFi";
                napisy[n.Nazwa.ordinal()] = "Nazwa";
                napisy[n.Haslo.ordinal()] = "Hasło";
                napisy[n.Start.ordinal()] = "Start";
                napisy[n.Stop.ordinal()] = "Stop";
                napisy[n.Blad.ordinal()] = "Błąd";
                napisy[n.Zaktualizowano.ordinal()] = "Zaktualizowano! Czy uruchomić ponownie program PilotPC?";
                napisy[n.DostepnaNowa.ordinal()] = "Dostępna jest nowa wersja programu PilotPC. Czy chcesz zaktualizować?";

                break;
        }
    }
}
