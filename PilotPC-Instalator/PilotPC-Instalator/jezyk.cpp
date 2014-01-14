#include "jezyk.h"


jezyk::jezyk()
{
}


jezyk::~jezyk()
{
}
wchar_t* jezyk::napisy[16];
void jezyk::laduj(jezyki a)
{
	switch (a)
	{
	case jezyk::Polski:
		napisy[n::Instaluj] = L"Instaluj";
		napisy[n::Odinstaluj] = (L"Odinstaluj");
		napisy[n::WybierzFolder] = (L"Wybierz folder");
		napisy[n::DlaObecnegoUzytkownika] = (L"Dla obecnego użytkownika");
		napisy[n::DlaWszystkichUzytkownikow] = (L"Dla wszystkich użytkowników");
		napisy[n::UruchamiajAutomatyczneiPrzyStarcieSystemu] = (L"Uruchamiaj automatycznie przy starcie systemu");
		napisy[n::SkrotNaPulpicie] = (L"Skrót na pulpicie");
		napisy[n::SkrotWMenuStart] = (L"Skrót w Menu Start");
		napisy[n::ProgramJestJuzZainstalowany] = (L"Program jest już zainstalowany.");
		napisy[n::InstalujPonownie] = (L"Instaluj ponownie");
		napisy[n::PilotPCInstalator] = (L"PilotPC - Instalator");
		napisy[n::Zainstalowano] = (L"Zainstalowano");
		napisy[n::BladPodczasInstalacji] = (L"Błąd podczas instalacji");
		napisy[n::BladZapisuDoPliku] = (L"Błąd zapisu do pliku");
		napisy[n::NieMoznaUtworzycPliku] = L"Nie można utworzyć pliku.";
		napisy[n::Usunieto] = L"Usunięto";
		break;
	case jezyk::Angielski:
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
		break;
	case jezyk::Rosyjski:
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
		break;
	default:
		break;
	}
	
}