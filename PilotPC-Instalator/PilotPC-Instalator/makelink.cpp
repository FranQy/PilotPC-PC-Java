
/*
--------------------------------------------------------------------------------
File Name   : MAKELINK

Contains    : Console mode program (EXE file) to create a link file (short-cut).
This is based on:

(i) An example in MSDN: Platform SDK -> User Interface Services ->
Shell and Common Controls -> Windows Shell API -> Shell links.
Microsoft keep moving the articles in MSDN so it may be easier
to search for the example by its name (CreateLink).

(ii) A public domain program on the web that uses the same
technique as MSDN: http://www.metathink.com/shlink/shlink.c

Glossary    :

To do       :

Legal       : Copyright (c) 2005. All rights reserved.
--------------------------------------------------------------------------------
*/

/* Windows headers come first */
#define STRICT
#include <windows.h>

/* C run time library headers */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/* COM headers (requires shell32.lib, ole32.lib, uuid.lib) */
#include <objbase.h>
#include <shlobj.h>

#include "makelink.h";


/*
--------------------------------------------------------------------------------
Description:
Show the help page using printf statements. Ideally this should not occupy
more than 25 lines.
--------------------------------------------------------------------------------
*/
static void ShowHelp(void)
{
	printf("\n");
	printf("MAKELINK - creates a short-cut (link) to a file. Usage:\n");
	printf("\n");
	printf("  MAKELINK -h\n");
	printf("  MAKELINK file args linkfile desc [show [curdir [iconfile iconindex] ] ]\n");
	printf("\n");
	printf("  -h, -?     ... Show help screen then quit.\n");
	printf("  file       ... File to which we are creating a short-cut.\n");
	printf("  args       ... Arguments of Targetfile, use \"\" if none.\n");
	printf("  linkfile   ... Name of the short-cut file, ending in 'lnk'.\n");
	printf("  desc       ... Description of Targetfile, use \"\" if none.\n");
	printf("  show       ... 1=Normal window(default), 3=Maximized, 7=Minimized (optional).\n");
	printf("  curdir     ... Current directory set when the link file runs (optional).\n");
	printf("  iconfile   ... DLL or ICO file containing icon used for the link (optional).\n");
	printf("  iconindex  ... Integer index of the icon used for the link (optional).\n");
	printf("\n");
	printf("The File and Args elements are always required (Args can equal \"\" if no\n");
	printf("arguments apply). Also the Linkfile and Desc elements are always required\n");
	printf("(Desc can equal \"\" if no description applies). The other elements are\n");
	printf("optional.\n");
	printf("\n");
}

/*
--------------------------------------------------------------------------------
Description:
Show an error message using printf statements to stdout.

Parameters:
pszError - Error text to show.
hRes     - HRESULT values >= 0 indicate success, < 0 indicate failure.
--------------------------------------------------------------------------------
*/
static void ShowError(LPSTR pszError, HRESULT hRes)
{
	printf("%s %x \n", pszError, hRes);
}

/*
--------------------------------------------------------------------------------
Description:
Get the command line arguments.

Parameters:
pfHelp           - Returns TRUE if -H was used.
pszTargetfile    - Returns the file name of the link's target.
sizeTargetfile   - Sizeof the file name to avoid overflow.
pszTargetargs    - Returns the command line arguments of the link's target.
sizeTargetargs   - Sizeof the command line arguments buffer to avoid overflow.
pszLinkfile      - Returns the file name of the actual link file.
sizeLinkfile     - Sizeof the file name to avoid overflow.
pzDescription    - Returns the description of the link's target.
sizeDescription  - Sizeof the description string to avoid overflow.
piShowmode       - Returns the ShowWindow() constant for the link's target.
pszCurdir        - Returns the working directory of the active link.
sizeCurdir       - Sizeof the working directory to avoid overflow.
pszIconfile      - Returns the file name of the icon file used for the link.
sizeIconfile     - Sizeof the file name to avoid overflow.
piIconindex      - Returns the index of the icon in the icon file.
pfUnknown        - Returns TRUE if an invalid parameter was used.
--------------------------------------------------------------------------------
*/

/*
--------------------------------------------------------------------------------
Description:
Creates the actual 'lnk' file (assumes COM has been initialized).

Parameters:
pszTargetfile    - File name of the link's target, must be a non-empty
string.

pszTargetargs    - Command line arguments passed to link's target, may
be an empty string.

pszLinkfile      - File name of the actual link file, must be a non-empty
string.

pszDescription   - Description of the linked item. If this is an empty
string the description is not set.

iShowmode        - ShowWindow() constant for the link's target. Use one of:
1 (SW_SHOWNORMAL) = Normal window.
3 (SW_SHOWMAXIMIZED) = Maximized.
7 (SW_SHOWMINNOACTIVE) = Minimized.
If this is zero the showmode is not set.

pszCurdir        - Working directory of the active link. If this is
an empty string the directory is not set.

pszIconfile      - File name of the icon file used for the link.
If this is an empty string the icon is not set.

iIconindex       - Index of the icon in the icon file. If this is
< 0 the icon is not set.

Returns:
HRESULT value >= 0 for success, < 0 for failure.
--------------------------------------------------------------------------------
*/
static HRESULT CreateShortCut(LPSTR pszTargetfile, LPSTR pszTargetargs,
	LPSTR pszLinkfile, LPSTR pszDescription,
	int iShowmode, LPSTR pszCurdir,
	LPSTR pszIconfile, int iIconindex)
{
	HRESULT       hRes;                  /* Returned COM result code */
	IShellLink*   pShellLink;            /* IShellLink object pointer */
	IPersistFile* pPersistFile;          /* IPersistFile object pointer */
	WORD          wszLinkfile[MAX_PATH]; /* pszLinkfile as Unicode string */
	int           iWideCharsWritten;     /* Number of wide characters written */

	hRes = E_INVALIDARG;
	if (
		(pszTargetfile != NULL) && (strlen(pszTargetfile) > 0) &&
		(pszTargetargs != NULL) &&
		(pszLinkfile != NULL) && (strlen(pszLinkfile) > 0) &&
		(pszDescription != NULL) &&
		(iShowmode >= 0) &&
		(pszCurdir != NULL) &&
		(pszIconfile != NULL) &&
		(iIconindex >= 0)
		)
	{
		hRes = CoCreateInstance(&CLSID_ShellLink,     /* pre-defined CLSID of the IShellLink object */
			NULL,                 /* pointer to parent interface if part of aggregate */
			CLSCTX_INPROC_SERVER, /* caller and called code are in same process */
			&IID_IShellLink,      /* pre-defined interface of the IShellLink object */
			&pShellLink);         /* Returns a pointer to the IShellLink object */
		if (SUCCEEDED(hRes))
		{
			/* Set the fields in the IShellLink object */
			hRes = pShellLink->lpVtbl->SetPath(pShellLink, pszTargetfile);
			hRes = pShellLink->lpVtbl->SetArguments(pShellLink, pszTargetargs);
			if (strlen(pszDescription) > 0)
			{
				hRes = pShellLink->lpVtbl->SetDescription(pShellLink, pszDescription);
			}
			if (iShowmode > 0)
			{
				hRes = pShellLink->lpVtbl->SetShowCmd(pShellLink, iShowmode);
			}
			if (strlen(pszCurdir) > 0)
			{
				hRes = pShellLink->lpVtbl->SetWorkingDirectory(pShellLink, pszCurdir);
			}
			if (strlen(pszIconfile) > 0 && iIconindex >= 0)
			{
				hRes = pShellLink->lpVtbl->SetIconLocation(pShellLink, pszIconfile, iIconindex);
			}

			/* Use the IPersistFile object to save the shell link */
			hRes = pShellLink->lpVtbl->QueryInterface(pShellLink,        /* existing IShellLink object */
				&IID_IPersistFile, /* pre-defined interface of the IPersistFile object */
				&pPersistFile);    /* returns a pointer to the IPersistFile object */
			if (SUCCEEDED(hRes))
			{
				iWideCharsWritten = MultiByteToWideChar(CP_ACP, 0, pszLinkfile, -1, wszLinkfile, MAX_PATH);
				hRes = pPersistFile->lpVtbl->Save(pPersistFile, wszLinkfile, TRUE);
				pPersistFile->lpVtbl->Release(pPersistFile);
			}
			pShellLink->lpVtbl->Release(pShellLink);
		}

	}
	return (hRes);
}

/*
--------------------------------------------------------------------------------
Description:
Get the command line arguments and create the link.

Returns:
Program return code of zero if we succeeded, or non-zero otherwise. This
is the usual convention for programs run from the command line.
--------------------------------------------------------------------------------
*/