import java.io.*;
import java.net.URL;
import java.util.TimerTask;

/**
 * Odpowiada za autoaktualizację programu oraz ściąganie plików bibliotek (.dll oraz .so) jeśli ich brakuje
 *
 * @author Mateusz
 */
public class Aktualizacja
        extends TimerTask {
    public static boolean zaktualizowano = false;
    public static boolean trwa = false;
    public static Boolean wymus = false;
    public static String najnowsza = "";

    @Override
    public void run() {
        if ((!zaktualizowano && Program.ustawienia.aktualizujAutomatycznie) || wymus) {
            InputStream is = null;
            String s;
            String content = "";
            String[] serwerUrl = {"http://jaebe.za.pl/", "http://pilotpc.za.pl/"};
            for (int SerNr = 0; SerNr < serwerUrl.length; SerNr++) {
                try {
                //pobiera z serwera informacje o najnowszej wersji i plikach do ściągnięcia
                    URL u = new URL(serwerUrl[SerNr] + "version.php?v=" + Program.wersja);

                    is = u.openStream();

                BufferedReader dis = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
                while ((s = dis.readLine()) != null) {
                    content += s + '\n';

                }
                    aktualizuj(content, serwerUrl[SerNr]);
                    trwa = false;
                //próbuje załadować biblioteki
                Biblioteka.load();
                    break;
                } catch (IOException ioe) {
                    try {
                        is.close();
                    } catch (IOException ioe2) {
                        // just going to ignore this one
                    }
                    if (Program.debug)
                        ioe.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException ioe) {
                    // just going to ignore this one
                }
                }
            }
        }
    }

    static void aktualizuj(String content, String UrlSerwera) {
        String[] linie = content.split("\n");
        for (int i2 = 0; i2 < linie.length; i2++) {
            if (linie[i2].split("=")[0].compareTo("plik") == 0) {//Usuwa pozostałości po ostatniej aktualizacji
                File old = new File(linie[i2].split("=")[1] + ".old");
                if (old.isFile())
                    old.delete();
                /*File New = new File(linie[i2].split("=")[1] + ".new");
                if (New.isFile())
                    New.delete(); */
            }
        }
        for (int i = 0; i < linie.length; i++)
            if (linie[i].split("=")[0].compareTo("wersja") == 0) {
                najnowsza = linie[i].split("=")[1];
                if (linie[i].split("=")[1].compareTo(Program.wersja) != 0 || wymus)//czy jest inna wersja
                {
                    trwa = true;

                    for (int i2 = 0; i2 < linie.length; i2++) {
                        if (linie[i2].split("=")[0].compareTo("plik") == 0) {
                            InputStream is = null;
                            try {
                                URL u;
                                if (linie[i2].split("=")[1].endsWith("exe"))
                                    u = new URL(UrlSerwera + linie[i2].split("=")[1] + ".bin");
                                else
                                    u = new URL(UrlSerwera + linie[i2].split("=")[1]);
                                is = u.openStream();
                                if (linie[i2].lastIndexOf('/') > 0)
                                    (new File(linie[i2].substring(linie[i2].indexOf('=') + 1, linie[i2].lastIndexOf('/')))).mkdirs();
                                /*String[] podfoldery=linie[i2].split("=")[1].split("/");
                                for(byte i3=0;i3<podfoldery.length-1;i3++)
                                {
                                    CreateDirectory();
                                }  */
                                FileOutputStream strumien;
                                if (linie[i2].split("=")[1].compareTo("PilotPC-PC-Java.jar") == 0) {
                                    if (Program.glowneOkno == null)
                                        Program.glowneOkno = new Okno(false);  //otwiera okno, bo potem będzie problem, ale go nie pokazuje
                                    strumien = new FileOutputStream(linie[i2].split("=")[1]);
                                    // strumien=new FileOutputStream(linie[i2].split("=")[1]+".new");
                                } else if (!(new File(linie[i2].split("=")[1])).exists()) {
                                    strumien = new FileOutputStream(linie[i2].split("=")[1]);

                                    if (linie[i2].split("=")[1].equals("Linux.sh"))
                                        (new File(linie[i2].split("=")[1])).setExecutable(true);
                                } else if ((new File(linie[i2].split("=")[1] + ".new")).exists()) {
                                    strumien = new FileOutputStream(linie[i2].split("=")[1]);

                                    if (linie[i2].split("=")[1].equals("Linux.sh"))
                                        (new File(linie[i2].split("=")[1])).setExecutable(true);
                                } else {
                                    strumien = new FileOutputStream(linie[i2].split("=")[1] + ".new");

                                    if (linie[i2].split("=")[1].equals("Linux.sh"))
                                        (new File(linie[i2].split("=")[1] + ".new")).setExecutable(true);
                                }

                                while (true) {
                                    int bajt = is.read();
                                    if (bajt == -1)
                                        break;
                                    strumien.write(bajt);
                                }
                                strumien.close();
                            } catch (IOException ioe) {

                                if (Program.debug)
                                    ioe.printStackTrace();
                            } finally {
                                try {
                                    is.close();
                                } catch (IOException ioe) {
                                    // just going to ignore this one
                                } catch (NullPointerException ioe) {
                                    // just going to ignore this one
                                }
                            }
                        }
                    }
                    for (int i2 = 0; i2 < linie.length; i2++) {
                        if ((new File(linie[i2].split("=")[1] + ".new")).exists() && linie[i2].split("=")[0].compareTo("plik") == 0 && (linie[i2].split("=")[1].equals("Windows.exe") || linie[i2].split("=")[1].equals("Linux.sh"))) {
                            (new File(linie[i2].split("=")[1])).renameTo(new File(linie[i2].split("=")[1] + ".old"));
                            (new File(linie[i2].split("=")[1] + ".new")).renameTo(new File(linie[i2].split("=")[1]));
                        }
                    }
                    zaktualizowano = true;
                }
                break;
            }
    }

}