@echo off

if not exist %systemroot%\system32\javaw.exe goto java
if not exist pilotpc-pc-java.jar echo Brak pliku PilotPC-PC-Java.jar
if not exist pilotpc-pc-java.jar pause
if exist pilotpc-pc-java.jar javaw -jar pilotpc-pc-java.jar
goto end
:java
echo Brak Javy. Czy przejść na stronę pobierania?
choice /C:TN
IF ERRORLEVEL 2 goto end
IF ERRORLEVEL 1 goto pobierz
goto end
:pobierz
explorer http://java.com/
:end