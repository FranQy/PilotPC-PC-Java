#pragma once
#include "stdafx.h"
;

using namespace std;
class jezyk
{
public:
	jezyk();
	~jezyk();
	enum jezyki;
	static void laduj(jezyki);
	static void nazwyLoad();
	static wchar_t* napisy[30];
	static LPWSTR nazwyJezykow[3];
	static int nazwyJezykowLen[3];
};


enum jezyk::jezyki{ Polski, Angielski, Rosyjski };
enum n{ Kod, Instaluj, Odinstaluj, WybierzFolder, DlaObecnegoUzytkownika, DlaWszystkichUzytkownikow, UruchamiajAutomatyczneiPrzyStarcieSystemu, SkrotNaPulpicie, SkrotWMenuStart, ProgramJestJuzZainstalowany, InstalujPonownie, PilotPCInstalator, Zainstalowano, BladPodczasInstalacji, BladZapisuDoPliku, NieMoznaUtworzycPliku, Usunieto, WymaganeUprawneiniaAdministratora, Zaakceptuj, Licencja, PotrzebnaJava, JavaTak, JavaNie, Wybierz, BladPolaczeniaZSerwerrem, NieMoznaUtworzycFolderu, SterujKomputeremZdalnie, OdinstalujPilotPC };
