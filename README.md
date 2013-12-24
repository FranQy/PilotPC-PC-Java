<h1>PilotPC-PC-Java</h1>
<h2>Wstęp</h2>
<p>
	Jest to serwer dla programu PilotPC. Program posiada mechanizm automatycznej aktualizacji.
</p>
<!--instrukcja-->
<h2>Instrukcja</h2>
<p>
	Aby uruchomić serwer należy zapisać plik <b>PilotPC-PC-Java</b>.jar, najlepiej w pustym folderze, a następnie go uruchomić. Jeśli dostaniesz komunikat z zapory (firewalla) to zezwól na połączenie/dodanie wyjątku.
</p>
<p>
	W zasobniku systemowym pokarze się ikona (tymczasowo jest to czarny kwadrat) informująca że serwer jest uruchomiony. Można kliknąć ją 2 razy aby pojawiło się okno serwera. Okno to można dowolnie otwierać i zamykać: nie wpływa to na działanie serwera. Aby wyłączyć serwer kliknij prawym przyciskiem myszy na ikonę w zasobniku i wybierz <b>Zamknij</b>
</p>
<p>
	Pierwszą metodą na połączenie się jest skorzystanie z <a href="https://github.com/FranQy/PilotPC-Android">Aplikacji na androida</a>. należy w niej wpisać adres IP komputera (pokazuje się on w oknie serwera) oraz hasło (również pokazuje się w oknie serwera).
</p>
<p>
	Drugą metodą jest zeskanowanie kodu QR wyświetlonego w oknie serwera.
</p>
<p>
	Trzecią metodą jest uruchomienie przeglądarki www w telefonie/tablecie i wpisanie numeru IP komputera, dwukropka i liczby <i>8753</i> np. <i>127.127.127.127:8753</i>.
</p>
<!--/instrukcja-->
<h2>Pliki</h2>
<table>
	<tr><td><b>PilotPC-PC-Java</b>.jar</td><td>Główny plik z programem</td></tr>
	<tr><td><b>libpilotpc</b>.so</td><td>Dodatkowa biblioteka dla Linuksa z 32-bitową Javą</td></tr>
	<tr><td><b>libpilotpc-x64</b>.so</td><td>Dodatkowa biblioteka dla Linuksa z 64-bitową Javą</td></tr>
	<tr><td><b>pilotpc</b>.dll</td><td>Dodatkowa biblioteka dla Windowsa z 32-bitową Javą</td></tr>
	<tr><td><b>pilotpc-x64</b>.dll</td><td>Dodatkowa biblioteka dla Windowsa z 64-bitową Javą</td></tr>
	<tr><td><b>windows</b>.exe</td><td>Plik uruchamiający program pod systemem Windows</td></tr>
</table>
<h2>Wspierane systemy operacyjne</h2>
<p>
Windows XP i nowsze (dla procesorów x86 i x64)
</p><p>
GNU/Linux (dla procesorów x86 i x64)
</p>
<h2>Lista zmian</h2>
<table>
<tr><th>Numer</th><th>Data</th><th>Zmiany</th></tr>
<tr><td>0.1.9</td><td>28.11.2013r.</td><td><ul><li>Zmiany w wyglądzie okna serwera</li></ul></td></tr>
<tr><td>0.1.10</td><td>28.11.2013r.</td><td><ul>
<li>Zmiany w wyglądzie okna serwera</li>
<li>Możliwość rozłączania telefonów z poziomu serwera</li>
<li>Wersja www: dodanie ikon w menu na dole</li>
<li>Wersja www: ustawienie kolorów interfejsu na takie, jakie są w wersji na androida</li>
</ul></td></tr>
<tr><td>0.1.11</td><td>30.11.2013r.</td><td><ul><li>Odczytywanie informacji o telefonie i pokazywanie na serwerze.</li></ul></td></tr>
<tr><td>0.1.12</td><td>30.11.2013r.</td><td><ul>
<li>Zmiana kolorystyki serwera</li>
<li>Ulepszenie odczytywania informacji o podłączonym telefonie</li>
<li>Wersja www: sprawdzanie hasła</li>
<li>Wersja www: wyświetlanie komunikatu, że rozłączono</li>
</ul></td></tr><tr><td>0.1.13</td><td>6.12.2013r.</td><td><ul>
<li>Poprawa problemów z odczytywaniem kodu QR</li>
</ul></td></tr><tr><td>0.1.14</td><td>6.12.2013r.</td><td><ul>
<li>Automatyczne ściąganie brakujących bibliotek</li>
</ul></td></tr>
</ul></td></tr><tr><td>0.1.15</td><td>7.12.2013r.</td><td><ul>
<li>Blokowanie wyskakiwania kilku okien na raz</li>
</ul></td></tr>
</ul></td></tr><tr><td>0.1.16</td><td>13.12.2013r.</td><td><ul>
<li>Drobne zmiany w wyglądzie</li>
</ul></td></tr><tr><td>0.1.17</td><td>14.12.2013r.</td><td><ul>
<li>Drobne</li>
</ul></td></tr><tr><td>0.1.19</td><td>16.12.2013r.</td><td><ul>
<li>Pilot przez przeglądarkę</li>
</ul></td></tr><tr><td>0.1.19</td><td>16.12.2013r.</td><td><ul>
<li>Poprawki błędów</li>
</ul></td></tr><tr><td>0.1.20</td><td>18.12.2013r.</td><td><ul>
</ul></td></tr><tr><td>0.1.21</td><td>19.12.2013r.</td><td><ul>
<li>Wersja WWW:menu</li>
<li>Wersja WWW:scroll (prawe 10% szerokości touchpada)</li>
</ul></td></tr><tr><td>0.1.23</td><td>21.12.2013r.</td><td><ul>
<li>Wersja WWW:głośność przez przytrzymanie</li>
<li>Wersja WWW:naprawa błędu z menu</li>
<li>Wersja WWW:zalążek klawiatury</li>
</ul></td></tr><tr><td>0.1.24</td><td>23.12.2013r.</td><td><ul>
<li>Wsparcie dla komuniakcji z innymi programami (np. z dodatkiem dla Firefoksa)</li>
<li>Dodanie dodatku dla Firefoksa obsługującego przyciski play/pauza, następny i poprzedni na YouTube.com</li>
<li>Wersja WWW:informacje o połączeniu</li>
<li>Wersja WWW:przycisk utwierający menu i animacja</li>
<li>Wersja WWW:usunięcie gamepada</li>
</ul></td></tr>
</table>
<h2>Linki</h2>
<a href="https://github.com/FranQy/PilotPC-Android">Aplikacja na androida</a><br/>
<a href="http://pilotpc.za.pl">Oficjalna strona</a><br/>