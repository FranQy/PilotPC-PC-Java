#!/bin/sh
# przyklad
echo Program instalacyjny programu Jaebe PilotPC
echo -n "Podaj folder do zainstalowania programu:"
read folder
mkdir $folder
wget http://jaebe.za.pl/Linux.sh --output-document="$folder/Linux.sh"
chmod +rwx "$folder/Linux.sh"
echo Uruchamianie...
"./$folder/Linux.sh"
