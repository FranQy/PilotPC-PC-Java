import javax.swing.*;
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
    public static String katalog = "";
    public static boolean możnaAktualizować = true;

    @Override
    public void run() {
        //if ((!zaktualizowano && Program.ustawienia.aktualizujAutomatycznie) || wymus)
        {
            if (!możnaAktualizować)
                return;
            InputStream is = null;
            String s;
            String content = "";
            String[] serwerUrl = {"http://jaebestudio.tk/", "http://jaebe.za.pl/", "http://pilotpc.za.pl/", "http://jaebestudio.com/", "http://jaebestudio.eu/"};
            for (int SerNr = 0; SerNr < serwerUrl.length; SerNr++) {
                try {
                    //pobiera z serwera informacje o najnowszej wersji i plikach do ściągnięcia
                    URL u = new URL(serwerUrl[SerNr] + katalog + "version.php?v=" + Program.wersja + "&ok=" + Program.ustawienia.WersjaOk + "&auto=" + Program.ustawienia.aktualizujAutomatycznie);

                    is = u.openStream();

                    BufferedReader dis = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
                    while ((s = dis.readLine()) != null) {
                        content += s + '\n';

                    }
                    aktualizuj(content, serwerUrl[SerNr] + katalog);
                    trwa = false;
                    //próbuje załadować biblioteki
                    Biblioteka.load();
                    break;
                } catch (IOException ioe) {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException ioe2) {
                        // just going to ignore this one
                    }
                    Debugowanie.Błąd(ioe);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException ioe) {
                        // just going to ignore this one
                    }
                }
            }
        }
    }

    static void aktualizuj(String content, String UrlSerwera) {
        if (!content.substring(0, 13).equals("nazwa=PilotPC")) {
            System.out.println(Jezyk.napisy[Jezyk.n.BladPolaczeniaZSerweremAktualizacji.ordinal()]);
            return;
        }
        if (!możnaAktualizować)
            return;
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

                Boolean czyAkt = ((!zaktualizowano && Program.ustawienia.aktualizujAutomatycznie && !najnowsza.equals(Program.wersja)) || wymus);
                String uruchPocz = Program.wersja.substring(0, Program.wersja.lastIndexOf('.'));
                String aktPocz = najnowsza.substring(0, najnowsza.lastIndexOf('.'));
                if (Program.ustawienia.WersjaOk == null)
                    Program.ustawienia.WersjaOk = "0.0.0";
                String okPocz = Program.ustawienia.WersjaOk.substring(0, Program.ustawienia.WersjaOk.lastIndexOf('.'));
                if (!czyAkt && !Program.ustawienia.aktualizujAutomatycznie) {
                    if (!uruchPocz.equals(aktPocz) && !okPocz.equals(aktPocz)) {
                        /*int czy=JOptionPane.showConfirmDialog(null, "Dostępna jest nowa wersja programu PilotPC. Czy chcesz zaktualizować?", "Aktualizacja Jaebe PilotPC", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                     if(czy==0)
                         czyAkt=true;
                        else
                         Program.ustawienia.WersjaOk =  najnowsza;
                        Program.ustawienia.eksportuj();*/
                        new pytanieAkt(linie, UrlSerwera, najnowsza);
                    }
                }
                if (czyAkt)//czy jest inna wersja
                    samoAkt(linie, UrlSerwera);
                break;
            }
    }

    public static void samoAkt(String[] linie, String UrlSerwera) {

        {
            if (!możnaAktualizować)
                return;
            trwa = true;

            for (int i2 = 0; i2 < linie.length; i2++) {
                if (linie[i2].split("=")[0].compareTo("plik") == 0) {
                    InputStream is = null;
                    try {
                        URL u;
                        if (linie[i2].split("=")[1].endsWith("exe")) {
                            u = new URL(UrlSerwera + linie[i2].split("=")[1] + ".bin");
                            is = u.openStream();
                        } else
                            try {
                                u = new URL(UrlSerwera + linie[i2].split("=")[1]);
                                is = u.openStream();
                            } catch (IOException e) {
                                u = new URL(UrlSerwera + linie[i2].split("=")[1] + ".bin");
                                is = u.openStream();

                            }
                        if (linie[i2].lastIndexOf('/') > 0)
                            (new File(linie[i2].substring(linie[i2].indexOf('=') + 1, linie[i2].lastIndexOf('/')))).mkdirs();
                                /*String[] podfoldery=linie[i2].split("=")[1].split("/");
                                for(byte i3=0;i3<podfoldery.length-1;i3++)
                                {
                                    CreateDirectory();
                                }  */
                        FileOutputStream strumien = null;
                        String workspace = new File(".").getCanonicalPath() + "/";
                        String plikpodst = workspace + linie[i2].split("=")[1];
                        jeszczeraz:
                        try {
                            if (linie[i2].split("=")[1].compareTo("PilotPC-PC-Java.jar") == 0) {
                                if (Program.glowneOkno == null)
                                    Program.glowneOkno = new Okno(false);  //otwiera okno, bo potem będzie problem, ale go nie pokazuje
                                strumien = new FileOutputStream(plikpodst);
                                // strumien=new FileOutputStream(linie[i2].split("=")[1]+".new");
                            } else if (!(new File(plikpodst)).exists()) {
                                strumien = new FileOutputStream(new File(plikpodst));

                                if (linie[i2].split("=")[1].equals("Linux.sh"))
                                    (new File(plikpodst)).setExecutable(true);
                            } else if ((new File(plikpodst + ".new")).exists() || Program.nadpisywanie) {
                                strumien = new FileOutputStream(plikpodst);

                                if (linie[i2].split("=")[1].equals("Linux.sh"))
                                    (new File(plikpodst)).setExecutable(true);
                            } else {
                                try {
                                    strumien = new FileOutputStream(plikpodst + ".new");

                                    if (linie[i2].split("=")[1].equals("Linux.sh"))
                                        (new File(plikpodst + ".new")).setExecutable(true);
                                } catch (IOException ioe) {
                                    strumien = new FileOutputStream(plikpodst);

                                    if (linie[i2].split("=")[1].equals("Linux.sh"))
                                        (new File(plikpodst)).setExecutable(true);
                                }
                            }
                        } catch (IOException ioe) {
                            try {
                                plikpodst = plikpodst.replace('/', '\\');
                                if (linie[i2].split("=")[1].compareTo("PilotPC-PC-Java.jar") == 0) {
                                    if (Program.glowneOkno == null)
                                        Program.glowneOkno = new Okno(false);  //otwiera okno, bo potem będzie problem, ale go nie pokazuje
                                    strumien = new FileOutputStream(plikpodst);
                                    // strumien=new FileOutputStream(linie[i2].split("=")[1]+".new");
                                } else if (!(new File(plikpodst)).exists()) {
                                    strumien = new FileOutputStream(new File(plikpodst));

                                    if (linie[i2].split("=")[1].equals("Linux.sh"))
                                        (new File(plikpodst)).setExecutable(true);
                                } else if ((new File(plikpodst + ".new")).exists() || Program.nadpisywanie) {
                                    strumien = new FileOutputStream(plikpodst);

                                    if (linie[i2].split("=")[1].equals("Linux.sh"))
                                        (new File(plikpodst)).setExecutable(true);
                                } else {
                                    strumien = new FileOutputStream(plikpodst + ".new");

                                    if (linie[i2].split("=")[1].equals("Linux.sh"))
                                        (new File(plikpodst + ".new")).setExecutable(true);
                                }
                            } catch (IOException ioe2) {

                                Debugowanie.Błąd(ioe);
                                JOptionPane.showMessageDialog(null, "Wystąpił błąd z zapisem pliku:\r\n" + plikpodst + "\r\n\r\nSpróbuj zmienić uprawnienia folderu lub zainstaluj program w inny folderze.", "Błąd aktualizacji Jaebe PilotPC", JOptionPane.ERROR_MESSAGE);

                            }
                        }

                        while (true) {
                            int bajt = is.read();
                            if (bajt == -1)
                                break;
                            strumien.write(bajt);
                        }
                        if (strumien != null)
                            strumien.close();
                    } catch (IOException ioe) {

                        //String pdst2=plikpodst.
                        Debugowanie.Błąd(ioe);
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
            System.out.print("Pobrano aktualizację. Zostanie ona zainstalowana po ponownym uruchomieniu programu.");

        }
    }

    public static void reset() {
        try {
            while (true) {
                if (!Aktualizacja.trwa) {
                    if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
                        Runtime.getRuntime().exec("Windows.exe");
                    } else {

                        Runtime.getRuntime().exec("Linux.exe");
                    }
                    System.exit(0);
                } else
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block

                        Debugowanie.Błąd(e1);
                    }
            }
        } catch (Throwable e) {
            Debugowanie.Błąd(e);
        }
    }
}