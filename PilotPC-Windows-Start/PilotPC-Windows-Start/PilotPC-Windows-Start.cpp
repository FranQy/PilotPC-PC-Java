// PilotPC-Windows-Start.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <Windows.h>
#include <io.h>
#include <fstream>
#include <Sddl.h>
#include <string>
using namespace std;
#pragma comment (lib,"Advapi32.lib")
#pragma comment (lib,"User32.lib")
#pragma comment (lib,"Kernel32.lib")
#pragma comment (lib,"ws2_32.lib")
void plikiNew(wstring fold);
wstring fold = wstring(L"");

int serNr = 0;
char** serwery = new char*[] { "jaebe.za.pl", "pilotpc.za.pl" };
int* serweryl = new int[] { 11, 13 };
void convert(std::string &in, std::wstring &out)
{
	out.resize(in.size());
	const char   *in_next = &*in.begin();
	wchar_t      *out_next = &*out.begin();


	typedef std::codecvt<wchar_t, char, std::mbstate_t> converter;
	const converter &my_converter = std::use_facet<converter>(std::locale());


	std::mbstate_t   my_state;


	my_converter.in(my_state,
		in.c_str(), in.c_str() + in.length(), in_next,
		&out[0], (&out[0]) + in.length(), out_next);
}
int getHttp(char host[], int hostl, string path, int pathl)
{
	WSAData wsdata;
	if (WSAStartup(MAKEWORD(2, 2), &wsdata) != 0)
	{
		return -1;
	}
	SOCKET soc = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	struct sockaddr_in odb;
	memset(odb.sin_zero, 0, sizeof(odb.sin_zero));
	odb.sin_family = AF_INET;
	odb.sin_port = htons(80);
	hostent *hp = gethostbyname(host);
	odb.sin_addr.s_addr = ((in_addr*)*hp->h_addr_list)->s_addr;
	if (connect(soc, (sockaddr*)&odb, sizeof(odb)))
	{
		return -1;
	}
	char data1[] = "GET /";
	char data2[] = " HTTP/1.1\nHost: ";
	int test1 = sizeof(host);
	int test2 = sizeof(path);
	int test = hostl + pathl + 23;
	char data[1024];
	//memset(data1 + 5, 0, hostl + pathl + 23);
	for (int i = 0; i < 5; i++)
	{
		data[i] = data1[i];
	}
	for (int i = 0; i < pathl; i++)
	{
		data[i + 5] = path[i];
	}
	for (int i = 0; i < sizeof(data2); i++)
	{
		data[i + 5 + pathl] = data2[i];
	}
	for (int i = 0; i < hostl; i++)
	{
		data[i + 4 + pathl + sizeof(data2)] = host[i];
	}
	data[hostl + pathl + 21] = '\n';
	data[hostl + pathl + 22] = '\n';
	data[hostl + pathl + 23] = 0;
	//&data1 = path;
	//char data[] = "GET /" + path + " HTTP/1.1\nHost: " + host + "\n\n";
	send(soc, data, hostl + pathl + 23, 0);
	return soc;
	/*const int BuffSize = 10485760;
	char buff[1];
	memset(&buff, 0, BuffSize+1);
	//char[] = new char[];
	while (true)
	{
	int n = recv(soc, buff, BuffSize, 0);
	if (n <= 0) break;
	buff[n] = 0;

	}*/
	//cout<
	//closesocket(soc);
	WSACleanup();
	//return 5;
}
void pobierz(string nazwa, wstring fol)
{
	int soc;

	//SetWindowTextA(StanInstalacji, nazwa.c_str());
	int leng = nazwa.length();
	if (nazwa[leng - 4] == '.'&&nazwa[leng - 3] == 'e'&&nazwa[leng - 2] == 'x'&&nazwa[leng - 1] == 'e')
		return;//soc = getHttp(serwery[serNr], serweryl[serNr], nazwa + ".bin", nazwa.length() + 4);
	else
		soc = getHttp(serwery[serNr], serweryl[serNr], nazwa, nazwa.length());
	const int BuffSize = 10240;
	char buff[1 + BuffSize];
	int n = 1;
	bool znalezione = false;

	for (int i = 0; i < nazwa.length(); i++)
	if (nazwa[i] == '/')
		nazwa[i] = '\\';

	SECURITY_ATTRIBUTES  sa;
	SECURITY_ATTRIBUTES*  saw = &sa;

	sa.nLength = sizeof(SECURITY_ATTRIBUTES);
	sa.bInheritHandle = FALSE;
	TCHAR * szSD = TEXT("D:")       // Discretionary ACL
		TEXT("(A;OICI;GA;;;AU)");
	ConvertStringSecurityDescriptorToSecurityDescriptor(
		szSD,
		SDDL_REVISION_1,
		&(saw->lpSecurityDescriptor),
		NULL);
	wstring b;
	convert(nazwa, b);
	const WCHAR* nazwa2 = b.c_str();;

	const WCHAR* folder2 = fol.c_str();
	//tworzy podfoldery
	wstring calyplik = (fol + wstring(L"\\") + b);
	for (int i = 3; i < calyplik.length(); i++)
	{

		if (calyplik[i] == '\\')
		{
			if (!CreateDirectory(calyplik.substr(0, i).c_str(), &sa) && _waccess(calyplik.substr(0, i).c_str(), 0) == ENOENT){
				printf("B³¹d podczas pobierania :(");
				//MessageBox(NULL, (wstring(L"Nie mo¿na utworzyæ folderu ") + calyplik.substr(0, i)).c_str(), jezyk::napisy[BladPodczasInstalacji], MB_ICONERROR);
				//exit(1);
			}
		}
	}
	//_bstr_t naz2 = new _bstr_t()
	//HANDLE  hPlik = CreateFile(lacz(folder, nazwa), GENERIC_WRITE, 0, NULL, CREATE_ALWAYS, 0, NULL);
	//WCHAR* test = c +L"\\"+ b;
	HANDLE  hPlik = CreateFile((fol + wstring(L"\\") + b).c_str(), GENERIC_ALL, 0, NULL, CREATE_ALWAYS, FILE_FLAG_SEQUENTIAL_SCAN, NULL);


	if (hPlik == INVALID_HANDLE_VALUE) {
		printf("B³¹d podczas pobierania :(");
	}
	DWORD licz = 0;

	int zapisane = 0;
	int rozmiar = 2000000000;

	/*string cd = "@echo off\r\ncd \"" + string(folderStr.begin(),folderStr.end()) + "\"\r\n";
	if (!WriteFile(hPlik, cd.c_str(), cd.length(), 0, NULL)) {
	MessageBox(NULL, L"B³¹d podczas instalacji", L"B³¹d zapisu do pliku", MB_ICONEXCLAMATION);
	PostQuitMessage(0); // Zakoñcz program
	}*/
	try{
		while (n > 0 && rozmiar != zapisane)
		{
			n = recv(soc, buff, BuffSize, 0);
			int i = 0;
			if (!znalezione)
			for (; i < n; i++)
			{
				if (buff[i] == '\n'&&buff[i + 1] == '\n')
				{
					i = i + 2;
					znalezione = true;
					break;
				}
				else if (buff[i] == '\n'&&buff[i + 2] == '\n'){
					i = i + 3;
					znalezione = true;
					break;
				}
			}
			int lengthStringPos = ((string)buff).find("Content-Length:");
			if (lengthStringPos > 0)
			{
				int test = lengthStringPos + 15;
				int test2 = ((string)buff).find('\r', lengthStringPos + 15) - lengthStringPos - 15;
				string lengthString = ((string)buff).substr(lengthStringPos + 15, ((string)buff).find('\r', lengthStringPos + 15) - lengthStringPos - 15);
				rozmiar = stoi(lengthString);
			}
			if (znalezione)
			{
				//MessageBox(NULL, L"B", L"3b", MB_ICONEXCLAMATION);
				if (!WriteFile(hPlik, buff + i, n - i, &licz, NULL)) {

					PostQuitMessage(0); // Zakoñcz program
				}
				else
				{
					zapisane += n - i;
				}
			}
		}
	}
	catch (exception e){
		string co = e.what();

		printf((string("b³¹d: ") + co).c_str());
	}


	CloseHandle(hPlik);
	shutdown(soc, 2);
}
void instalujJave(){
	pobierz("java.bin", fold);
	MoveFile(L"java.bin", L"\\javaInstalacja.exe");
	//MessageBox(NULL, L"W systemie brak Javy. Proszê zainstalowaæ Javê", L"Informacja o Javie", MB_ICONEXCLAMATION);
	STARTUPINFO si;
	PROCESS_INFORMATION pi;

	ZeroMemory(&si, sizeof(si));
	si.cb = sizeof(si);
	ZeroMemory(&pi, sizeof(pi));
	CreateProcess(L"javaInstalacja.exe", L"javaInstalacja.exe", NULL, NULL, false, 0, NULL, 0, &si, &pi);
}
void plikiNew(wstring fold){
	WIN32_FIND_DATA  FindFileData;

	HANDLE pliczekH = FindFirstFile((fold + wstring(L"*.new")).c_str(), &FindFileData);
	if (pliczekH != INVALID_HANDLE_VALUE)
	{
		while (true){
			wstring pliczek = wstring(FindFileData.cFileName);


			MoveFileEx((fold + pliczek).c_str(), ((fold + pliczek.substr(0, pliczek.length() - 4)).c_str()), MOVEFILE_REPLACE_EXISTING);
			if (!FindNextFile(pliczekH, &FindFileData))
				break;
		}
	}
	FindClose(pliczekH);

	pliczekH = FindFirstFile((fold + wstring(L"*")).c_str(), &FindFileData);
	while (pliczekH != INVALID_HANDLE_VALUE)
	{
		wstring pliczek = wstring(FindFileData.cFileName);
		if (FindFileData.dwFileAttributes == FILE_ATTRIBUTE_DIRECTORY&&pliczek != wstring(L".") && pliczek != wstring(L"..") && pliczek != wstring(L""))
			plikiNew(fold + pliczek + wstring(L"\\"));
		if (!FindNextFile(pliczekH, &FindFileData))
			break;
	}
	FindClose(pliczekH);


}
int run(_TCHAR* argv[], LPCWSTR polec, bool konsola)
{
	HINSTANCE hInst;
	//	if (!access("PilotPC-PC-Java.jar.new", 0))
	WCHAR *pol;
	if (konsola)
		pol = L"java.exe";
	else

		pol = L"javaw.exe";
	if (konsola){

		wstring komenda = wstring(pol) + wstring(L" ") + wstring(polec);
		system(string(komenda.begin(), komenda.end()).c_str());
	}
	else
	{

		if (argv[0][1] == L':')
		{
			hInst = ShellExecute(0,
				L"open",                      // Operation to perform
				pol,  // Application name
				polec,           // Additional parameters
				wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\')).c_str(),                           // Default directory
				SW_SHOW);
			wprintf(wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\')).c_str());
		}
		else
			hInst = ShellExecute(0,
			L"open",                      // Operation to perform
			pol,  // Application name
			polec,           // Additional parameters
			0,                           // Default directory
			SW_SHOW);
	}
	if (reinterpret_cast<int>(hInst) <= 32)
	{
		if ((reinterpret_cast<int>(hInst)) == ERROR_FILE_NOT_FOUND)
		{
			//brak javy
			printf("Brak zainstalowanej Javy!");
			instalujJave();
			return false;
		}
	}
}
void pobierzPlikiWszytskie()
{
restart:
	int soc = getHttp(serwery[serNr], serweryl[serNr], "version.php?instal", 18);
	if (soc == -1)
	{
		serNr++;
		goto restart;
	}

	const int BuffSize = 10000;
	char buff[1 + BuffSize];
	//memset(&buff, 0, BuffSize + 1);
	//char[] = new char[];
	//while (true)
	//{
	int n = recv(soc, buff, BuffSize, 0);
	string buforS = string(buff);
	if (buforS.find("HTTP/1.1 200") != 0)
	{
		serNr++;
		goto restart;
	}
	buff[n] = 0;
	int i = 0;
	for (; i < n; i++)
	{
		if (buff[i] == '\n'&&buff[i + 1] == '\n')
		{
			i = i + 2;
			break;
		}
		else if (buff[i] == '\n'&&buff[i + 2] == '\n'){
			i = i + 3;
			break;
		}
	}
	string tresc = buff + i;
	for (int x = 0; x < n - i; x++)
	{
		if (tresc[x] == '\n'&&tresc[x + 1] == 'p'&&tresc[x + 2] == 'l'&&tresc[x + 3] == 'i'&&tresc[x + 4] == 'k'&&tresc[x + 5] == '=')
		{
			x = x + 6;
		}
	}
	for (int x = 0; x < n - i; x++)
	{
		if (tresc[x] == '\n'&&tresc[x + 1] == 'p'&&tresc[x + 2] == 'l'&&tresc[x + 3] == 'i'&&tresc[x + 4] == 'k'&&tresc[x + 5] == '=')
		{
			x = x + 6;
			int x2 = x;
			string plik = tresc.substr(x);
			plik = plik.substr(0, plik.find_first_of('\r'));
			pobierz(plik, fold);

		}
	}
}
int _tmain(const int argc, _TCHAR* argv[])
{
	if (argv[0][1] == L':')
		fold = (wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\')) + wstring(L"\\"));
	fstream sprPl;
	sprPl.open(L"java/pilotpc.jar", ios::in | ios::_Nocreate);  /* wa¿ne, by nie tworzyæ pliku, jeœli nie istnieje, st¹d flaga nocreate */
	if (!sprPl.is_open())
	{
		printf("Brak plików, pobieranie...");
		pobierzPlikiWszytskie();

	}

	if (argv[0][1] == L':')
		plikiNew(wstring(argv[0]).substr(0, wstring(argv[0]).find_last_of(L'\\')) + wstring(L"\\"));
	else
		plikiNew(wstring(L""));
	if (argc == 1)
	{
		LPCWSTR polec = L"-jar java/pilotpc.jar -o";

		for (short i = 1; i < argc; i++)
		if ((argv[i][0] == '/' || argv[i][0] == '-') && argv[i][1] == 'n'&&argv[i][2] == 'o')
			polec = L"-jar java/pilotpc.jar";

		return run(argv, polec, false);


	}/*
	else if (wstring(argv[1]) == wstring(L"/?"))
	{
	wprintf(L"PilotPC\r\n\r\n/a	wlacza autostart przy starcie systemu");
	}*/
	else if (wstring(argv[1]) == wstring(L"/a"))
	{

		wstring folder;
		if (argv[0][1] == L':')
			folder = wstring(L"\"") + wstring(argv[0]) + wstring(L" /no\"");
		else
		{
			WCHAR curDir[1024];
			GetCurrentDirectory(2048, curDir);
			folder = wstring(L"\"") + curDir + wstring(L"\\") + wstring(argv[0]) + wstring(L" /no\"");

		}
		wprintf(folder.c_str());
		HKEY hkTest;
		RegOpenKeyEx(HKEY_CURRENT_USER, L"Software\\Microsoft\\Windows\\CurrentVersion\\Run", 0, KEY_ALL_ACCESS, &hkTest);
		RegSetValueEx(hkTest, L"PilotPC", 0, REG_SZ, (byte*)folder.c_str(), 2 * folder.length());
		return 0;
	}
	if (argc > 1)
	{
		wstring polec = wstring(L"-jar java/pilotpc.jar -o");
		bool konsola = false;
		for (short i = 1; i < argc; i++)
		if ((argv[i][0] == '/' || argv[i][0] == '-') && argv[i][1] == 'n'&&argv[i][2] == 'o')
			polec = wstring(L"-jar java/pilotpc.jar");
		else
			konsola = true;

		for (short i = 1; i < argc; i++)

			polec += wstring(L" ") + wstring(argv[i]);

		run(argv, polec.c_str(), konsola);

	}
	//return 0;
}

