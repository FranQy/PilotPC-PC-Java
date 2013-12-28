#include "String.h"

String::String(char* wej,int dlugosc)
{
	length = dlugosc;
	t = (WCHAR*)GlobalAlloc(GPTR, dlugosc * 2 + 2);
	for (int i = 0; i < dlugosc; i++){
		t[i] = (WCHAR)wej[i];
	}
	t[dlugosc] = 0;
}

String::~String()
{
}
