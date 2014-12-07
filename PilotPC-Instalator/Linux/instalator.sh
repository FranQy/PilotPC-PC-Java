#!/bin/sh
# przyklad
clear
echo Program instalacyjny programu Jaebe PilotPC
echo Zostaną zainstalowane niezbędne pakiety
sudo apt-get install wget openjdk-7-jre -y -f
clear
echo Program instalacyjny programu Jaebe PilotPC
echo -n "Podaj folder do zainstalowania programu:"
read folder
sudo mkdir $folder
sudo chmod +rwx "$folder"
wget http://jaebe.za.pl/Linux.sh --output-document="$folder/Linux.sh"
sudo chmod +rwx "$folder/Linux.sh"
ln -s "$folder/Linux.sh" ~/Pulpit
sudo "./$folder/Linux.sh"
