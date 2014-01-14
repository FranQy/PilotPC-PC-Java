#pragma once
#include "stdafx.h"
using namespace std;
class jezyk
{
public:
	jezyk();
	~jezyk();
 enum jezyki;
	static void laduj(jezyki);
	static wchar_t* napisy[16];
};


enum jezyk::jezyki{ Polski, Angielski, Rosyjski };
enum n{ Instaluj, Odinstaluj, WybierzFolder, DlaObecnegoUzytkownika, DlaWszystkichUzytkownikow, UruchamiajAutomatyczneiPrzyStarcieSystemu, SkrotNaPulpicie, SkrotWMenuStart, ProgramJestJuzZainstalowany, InstalujPonownie, PilotPCInstalator, Zainstalowano, BladPodczasInstalacji, BladZapisuDoPliku, NieMoznaUtworzycPliku, Usunieto };
