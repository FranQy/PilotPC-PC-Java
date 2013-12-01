#include "instalacja.h"
#include <stdio.h>
#include <stdlib.h>
#include <shellapi.h>
#ifdef WIN32
#include <io.h>
#else
#include <unistd.h>
#endif
using namespace std;


instalacja::instalacja(bool systemStart, bool wszyscy, char* folder)
{
	if (!czyJava())
	{
		HINSTANCE hInst = ShellExecute(0,
			L"open",                      // Operation to perform
			L"C:\\Windows\\System32\\javaw.exe",  // Application name
			L"",           // Additional parameters
			0,                           // Default directory
			SW_SHOW);
		if (reinterpret_cast<int>(hInst) <= 32)
		{
			if ((reinterpret_cast<int>(hInst)) == ERROR_FILE_NOT_FOUND)
			{
				int a = 45 + 6;
			}
		}
		int test3 = 5 + 5;
	}
}


instalacja::~instalacja()
{
}
bool instalacja::czyJava()
{
	return false;
}
void instalacja::getHttp(char* url)
{
	/*WSAData wsdata;
	if (WSAStartup(MAKEWORD(2, 2), &wsdata) != 0)
	{
	cout<      cin.get();
	exit(1);
	}
	int soc = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	struct sockaddr_in odb;
	memset(odb.sin_zero, 0, sizeof(odb.sin_zero));
	odb.sin_family = AF_INET;
	odb.sin_port = htons(80);
	hostent *hp = gethostbyname("komputery.katalogi.pl");
	odb.sin_addr.s_addr = ((in_addr*)*hp->h_addr_list)->s_addr;
	if (connect(soc, (sockaddr*)&odb, sizeof(odb)))
	{
	cout<      WSACleanup();
	cin.get();
	exit(1);
	}
	char data[] = "GET /[c]http_w_winapi-t137968.html HTTP/1.1\nHost: komputery.katalogi.pl\n\n";
	send(soc, data, sizeof(data)-1, 0);
	const int BuffSize = 512;
	char buff[BuffSize + 1];
	memset(buff, 0, sizeof(buff));
	cout << "============ start transmision ========="<  while (true)
	{
	int n = recv(soc, buff, BuffSize, 0);
	if (n <= 0) break;
	buff[n] = 0;
	cout<
	}
	//cout<
	//closesocket(soc);
	WSACleanup();
	cin.get();
	return 0;*/
}