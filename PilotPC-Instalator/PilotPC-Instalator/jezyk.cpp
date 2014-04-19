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
wchar_t* jezyk::napisy[18];
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
		napisy[n::WymaganeUprawneiniaAdministratora] = L"Wymagane uprawneinia administratora";
		napisy[n::Zaakceptuj] = L"Zaakceptuj";
		napisy[n::Licencja] = L"Postanowienia licencji wkrótce...";
		napisy[n::PotrzebnaJava] = L"Do działania programu wymagana jest instalacja Javy.";
		napisy[n::JavaTak] = L"Zainstaluj Javę i kontynuuj";
		napisy[n::JavaNie] = L"Anuluj";
		napisy[n::Wybierz] = L"Wybierz";
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
		napisy[n::Licencja] = L"Licence coming soon...";
		napisy[n::PotrzebnaJava] = L"This program need Java";
		napisy[n::JavaTak] = L"Install Java and continue";
		napisy[n::JavaNie] = L"Cancel";
		napisy[n::Wybierz] = L"Choose";
		break;
	case jezyk::Rosyjski:
		napisy[n::Kod] = L"RU";
		napisy[n::Instaluj] = L"Instaluj";
		napisy[n::Odinstaluj] = (L"Odinstaluj");
		napisy[n::WybierzFolder] = (L"Выберите папку");
		napisy[n::DlaObecnegoUzytkownika] = (L"Для текущего пользователя");
		napisy[n::DlaWszystkichUzytkownikow] = (L"Для всех пользователей");
		napisy[n::UruchamiajAutomatyczneiPrzyStarcieSystemu] = (L"Автоматический запуск при загрузке");
		napisy[n::SkrotNaPulpicie] = (L"Ярлык на рабочем столе");
		napisy[n::SkrotWMenuStart] = (L"Ярлык в меню Пуск");
		napisy[n::ProgramJestJuzZainstalowany] = (L"Program jest już zainstalowany.");
		napisy[n::InstalujPonownie] = (L"Instaluj ponownie");
		napisy[n::PilotPCInstalator] = (L"PilotPC - Instalator");
		napisy[n::Zainstalowano] = (L"Zainstalowano");
		napisy[n::BladPodczasInstalacji] = (L"Błąd podczas instalacji");
		napisy[n::BladZapisuDoPliku] = (L"Błąd zapisu do pliku");
		napisy[n::NieMoznaUtworzycPliku] = L"Nie można utworzyć pliku.";
		napisy[n::Usunieto] = L"Usunięto";
		napisy[n::WymaganeUprawneiniaAdministratora] = L"Wymagane uprawneinia administratora";
		napisy[n::Zaakceptuj] = L"Zaakceptuj";
		napisy[n::Licencja] = L"Postanowienia licencji wkrótce...";
		napisy[n::PotrzebnaJava] = L"Do działania programu wymagana jest instalacja Javy.";
		napisy[n::JavaTak] = L"Zainstaluj Javę i kontynuuj";
		napisy[n::JavaNie] = L"Anuluj";
		napisy[n::Wybierz] = L"Wybierz";
		break;
	default:
		break;
	}
	
}