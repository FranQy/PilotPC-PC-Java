@echo off

if not exist %systemroot%\system32\javaw.exe goto java
if not exist pilotpc-pc-java.jar echo Brak pliku PilotPC-PC-Java.jar
if not exist pilotpc-pc-java.jar pause
if exist pilotpc-pc-java.jar javaw -jar pilotpc-pc-java.jar
goto end
:java
echo Brak Javy. CUruchamianie strony pobierania.
:pobierz
explorer http://java.com/
:end