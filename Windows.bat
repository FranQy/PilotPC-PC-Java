@echo off

if not exist %systemroot%\system32\javaw.exe goto java
:uruchom
if exist pilotpc-pc-java.jar.new goto aktualizacja
if not exist pilotpc-pc-java.jar echo Brak pliku PilotPC-PC-Java.jar
if not exist pilotpc-pc-java.jar pause
if exist pilotpc-pc-java.jar javaw -jar pilotpc-pc-java.jar
goto end
:aktualizacja
ren pilotpc-pc-java.jar pilotpc-pc-java.jar.old
ren pilotpc-pc-java.jar.new pilotpc-pc-java.jar
javaw -jar pilotpc-pc-java.jar
goto end
:java
echo Brak Javy. Uruchamianie strony pobierania.
:pobierz
explorer http://java.com/
goto uruchom
:end