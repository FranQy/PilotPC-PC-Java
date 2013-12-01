#include "instalacja.h"
#include <stdio.h>
#include <stdlib.h>
#include <shellapi.h>
#ifdef WIN32
#include <io.h>
#else
#include <unistd.h>
#endif
#include <Winsock2.h>
using namespace std;


instalacja::instalacja(bool systemStart, bool wszyscy, char* folder)
{
	if (!czyJava())
	{
		
	}
	//int soc=getHttp("pilotpc.za.pl", 13, "pilotpc-pc-java.jar", 19);
	int soc=getHttp("pilotpc.za.pl", 13, "version.ini", 11);
	const int BuffSize = 10000;
	char buff[1+BuffSize];
	//memset(&buff, 0, BuffSize + 1);
	//char[] = new char[];
	//while (true)
	//{
		int n = recv(soc, buff, BuffSize, 0);
		
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
		char* tresc = buff + i;
		for (int x = 0; x < n - i; x++)
		{
			if (tresc[x] == '\n'&&tresc[x+1] == 'p'&&tresc[x+2] == 'l'&&tresc[x+3] == 'i'&&tresc[x+4] == 'k'&&tresc[x+5] == '=')
			{
				x = x + 6;
				int x2 = x;
				char* plik = tresc + x;
				for (; x<n; x++)
				{
					if (tresc[x] == '\r')
					{
						pobierz(plik, x - x2);
						break;
					}
				}
			}
		}
	//}
}


instalacja::~instalacja()
{
}
void instalacja::pobierz(char* od, int dlugosc)
{

}
bool instalacja::czyJava()
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
			return false;
		}
	}
	return true;
}
int instalacja::getHttp(char host[] ,int hostl,char* path,int pathl)
{
	WSAData wsdata;
	if (WSAStartup(MAKEWORD(2, 2), &wsdata) != 0)
	{
	exit(1);
	}
	int soc = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	struct sockaddr_in odb;
	memset(odb.sin_zero, 0, sizeof(odb.sin_zero));
	odb.sin_family = AF_INET;
	odb.sin_port = htons(80);
	hostent *hp = gethostbyname(host);
	odb.sin_addr.s_addr = ((in_addr*)*hp->h_addr_list)->s_addr;
	if (connect(soc, (sockaddr*)&odb, sizeof(odb)))
	{
	exit(1);
	}
	char data1[] = "GET /";
	char data2[] = " HTTP/1.1\nHost: ";
	int test1 = sizeof(host);
	int test2 = sizeof(path);
	int test = hostl+pathl+23;
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
}