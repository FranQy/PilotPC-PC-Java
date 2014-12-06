#!/bin/sh
# przyklad
echo Program instalacyjny programu Jaebe PilotPC
echo -n "Podaj folder do zainstalowania programu:"
read folder
if [ "$folder" == "" ]
 then fi
wget http://jaebe.za.pl/Linux.sh
pause