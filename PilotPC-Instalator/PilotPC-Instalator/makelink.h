


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
	LPSTR pszIconfile, int iIconindex);
