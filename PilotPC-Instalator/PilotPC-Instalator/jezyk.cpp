#include "jezyk.h"

LPWSTR jezyk::nazwyJezykow[3];
int jezyk::nazwyJezykowLen[3];
jezyk::jezyk()
{
}
void jezyk::nazwyLoad()
{
	jezyk::nazwyJezykow[0] = L"Polski";
	jezyk::nazwyJezykow[1] = L"English";
	jezyk::nazwyJezykow[2] = L"русский";
	jezyk::nazwyJezykowLen[0] = 6;
	jezyk::nazwyJezykowLen[1] = 7;
	jezyk::nazwyJezykowLen[2] = 7;


}

jezyk::~jezyk()
{
}
wchar_t* jezyk::napisy[30];
void jezyk::laduj(jezyki a)
{
	switch (a)
	{
	case jezyk::Polski:
		napisy[n::Kod] = L"PL";
		napisy[n::Instaluj] = L"Instaluj";
		napisy[n::Odinstaluj] = (L"Odinstaluj");
		napisy[n::WybierzFolder] = (L"Wybierz folder");
		napisy[n::DlaObecnegoUzytkownika] = (L"Dla obecnego użytkownika");
		napisy[n::DlaWszystkichUzytkownikow] = (L"Dla wszystkich użytkowników");
		napisy[n::UruchamiajAutomatyczneiPrzyStarcieSystemu] = (L"Uruchamiaj automatycznie przy starcie systemu");
		napisy[n::SkrotNaPulpicie] = (L"Skrót na pulpicie");
		napisy[n::SkrotWMenuStart] = (L"Skrót w Menu Start");
		napisy[n::ProgramJestJuzZainstalowany] = (L"Program jest już zainstalowany.");
		napisy[n::InstalujPonownie] = (L"Napraw");
		napisy[n::PilotPCInstalator] = (L"PilotPC - Instalator");
		napisy[n::Zainstalowano] = (L"Zainstalowano");
		napisy[n::BladPodczasInstalacji] = (L"Błąd podczas instalacji");
		napisy[n::BladZapisuDoPliku] = (L"Błąd zapisu do pliku");
		napisy[n::NieMoznaUtworzycPliku] = L"Nie można utworzyć pliku.";
		napisy[n::Usunieto] = L"Usunięto";
		napisy[n::WymaganeUprawneiniaAdministratora] = L"Wymagane uprawnienia administratora";
		napisy[n::Zaakceptuj] = L"Zaakceptuj";
		napisy[n::Licencja] = L"Program zawiera biblioteki JSON.org oraz ZXing\r\n\r\nInstalując, zgadzasz się na ich licencje.";
		napisy[n::PotrzebnaJava] = L"Do działania programu wymagana jest instalacja Javy.";
		napisy[n::JavaTak] = L"Zainstaluj Javę i kontynuuj";
		napisy[n::JavaNie] = L"Anuluj";
		napisy[n::Wybierz] = L"Wybierz";
		napisy[n::BladPolaczeniaZSerwerrem] = L"Błąd połączenia z serwerem. Prawdopodobnie nie masz internetu.";
		napisy[n::NieMoznaUtworzycFolderu] = L"Nie można utworzyć folderu ";
		napisy[n::SterujKomputeremZdalnie] = L"Steruj komputerem zdalnie";
		napisy[n::OdinstalujPilotPC] = L"Odinstaluj Jaebe PilotPC";
		napisy[n::Zakoncz] = L"Zakończ";
		break;
	case jezyk::Angielski:
		napisy[n::Kod] = L"EN";
		napisy[n::Instaluj] = L"Install";
		napisy[n::Odinstaluj] = (L"Uninstall");
		napisy[n::WybierzFolder] = (L"Choose folder");
		napisy[n::DlaObecnegoUzytkownika] = (L"For active user");
		napisy[n::DlaWszystkichUzytkownikow] = (L"For all users");
		napisy[n::UruchamiajAutomatyczneiPrzyStarcieSystemu] = (L"Automatically start at system startup");
		napisy[n::SkrotNaPulpicie] = (L"Desktop shortcut");
		napisy[n::SkrotWMenuStart] = (L"Menu start shortcut");
		napisy[n::ProgramJestJuzZainstalowany] = (L"Program already installed");
		napisy[n::InstalujPonownie] = (L"Reinstall");
		napisy[n::PilotPCInstalator] = (L"PilotPC - Installer");
		napisy[n::Zainstalowano] = (L"Instalation done");
		napisy[n::BladPodczasInstalacji] = (L"Installation error");
		napisy[n::BladZapisuDoPliku] = (L"File saving error");
		napisy[n::NieMoznaUtworzycPliku] = L"Creating file error";
		napisy[n::Usunieto] = L"Removing done";
		napisy[n::WymaganeUprawneiniaAdministratora] = L"Access denied";
		napisy[n::Zaakceptuj] = L"Accept";
		napisy[n::Licencja] = L"Program contains libraries: JSON.org and ZXing\r\n\r\nIf you install PilotPC, you will accept their licence terms.";
		napisy[n::PotrzebnaJava] = L"This program need Java";
		napisy[n::JavaTak] = L"Install Java and continue";
		napisy[n::JavaNie] = L"Cancel";
		napisy[n::Wybierz] = L"Choose";
		napisy[n::BladPolaczeniaZSerwerrem] = L"Connection server error. Probably no internet connection.";
		napisy[n::NieMoznaUtworzycFolderu] = L"Creating folder error ";
		napisy[n::SterujKomputeremZdalnie] = L"Remote control";
		napisy[n::OdinstalujPilotPC] = L"Uninstall Jaebe PilotPC";
		napisy[n::Zakoncz] = L"Exit";
		break;
	case jezyk::Rosyjski:
		napisy[n::Kod] = L"RU";
		napisy[n::Instaluj] = L"Установите";
		napisy[n::Odinstaluj] = (L"Удалить");
		napisy[n::WybierzFolder] = (L"Выберите папку");
		napisy[n::DlaObecnegoUzytkownika] = (L"Для текущего пользователя");
		napisy[n::DlaWszystkichUzytkownikow] = (L"Для всех пользователей");
		napisy[n::UruchamiajAutomatyczneiPrzyStarcieSystemu] = (L"Автоматический запуск при загрузке");
		napisy[n::SkrotNaPulpicie] = (L"Ярлык на рабочем столе");
		napisy[n::SkrotWMenuStart] = (L"Ярлык в меню Пуск");
		napisy[n::ProgramJestJuzZainstalowany] = (L"Уже установлена программа.");
		napisy[n::InstalujPonownie] = (L"Установите снова");
		napisy[n::PilotPCInstalator] = (L"PilotPC - Инсталлятор");
		napisy[n::Zainstalowano] = (L"Установленная");
		napisy[n::BladPodczasInstalacji] = (L"Ошибка при установке");
		napisy[n::BladZapisuDoPliku] = (L"Ошибка записи в файл");
		napisy[n::NieMoznaUtworzycPliku] = L"Невозможно создать файл.";
		napisy[n::Usunieto] = L"Удалено";
		napisy[n::WymaganeUprawneiniaAdministratora] = L"Разрешение администратора требуется";
		napisy[n::Zaakceptuj] = L"Примите";
		napisy[n::Licencja] = L"Программа содержит библиотеки: JSON.org и ZXing\r\n\r\nПри установке PilotPC, вы будете принимать их условия лицензии";
		napisy[n::PotrzebnaJava] = L"Чтобы запустить программу, необходимую для установки Java.";
		napisy[n::JavaTak] = L"Установите Java и продолжить";
		napisy[n::JavaNie] = L"Отмени";
		napisy[n::Wybierz] = L"Выберите";
		napisy[n::BladPolaczeniaZSerwerrem] = L"Не удалось подключиться к серверу . Вы, наверное, не имеют доступа в Интернет.";
		napisy[n::NieMoznaUtworzycFolderu] = L"Ошибка создания папки ";
		napisy[n::SterujKomputeremZdalnie] = L"дистанционное управление";
		napisy[n::OdinstalujPilotPC] = L"удалить Jaebe PilotPC";
		napisy[n::Zakoncz] = L"Zakończ";
		break;
	default:
		break;
	}
	
}