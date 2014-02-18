import java.io.*;
import java.net.URL;
import java.util.TimerTask;

/**
 * Odpowiada za autoaktualizację programu
 *
 * @author Mateusz
 */
public class Aktualizacja
        extends TimerTask {
    public static boolean zaktualizowano = false;
    public static boolean trwa = false;
    public static Boolean wymus = false;

    @Override
    public void run() {
        if (!zaktualizowano) {
            InputStream is = null;
            String s;
            String content = new String();

            try {
                URL u = new URL("http://pilotpc.za.pl/version.ini");
                is = u.openStream();
                DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
                while ((s = dis.readLine()) != null) {
                    content += s + '\n';

                }
                aktualizuj(content);
                trwa = false;
                Biblioteka.load();
            } catch (IOException ioe) {

                System.out.println("Oops- an IOException happened.");
            } finally {
                try {
                    is.close();
                } catch (IOException ioe) {
                    // just going to ignore this one
                }
            }
        }
    }

    static void aktualizuj(String content) {
        String[] linie = content.split("\n");
        for (int i = 0; i < linie.length; i++)
            if (linie[i].split("=")[0].compareTo("wersja") == 0) {
                if (linie[i].split("=")[1].compareTo(Program.wersja) != 0 || wymus)//czy jest inna wersja
                {
                    trwa = true;

                    for (int i2 = 0; i2 < linie.length; i2++) {
                        if (linie[i2].split("=")[0].compareTo("plik") == 0) {//Usuwa pozostałości po ostatniej aktualizacji
                            File old = new File(linie[i2].split("=")[1] + ".old");
                            if (old.isFile())
                                old.delete();
                            File New = new File(linie[i2].split("=")[1] + ".new");
                            if (New.isFile())
                                New.delete();
                        }
                    }
                    for (int i2 = 0; i2 < linie.length; i2++) {
                        if (linie[i2].split("=")[0].compareTo("plik") == 0) {
                            InputStream is = null;
                            try {
                                URL u;
                                if (linie[i2].split("=")[1].endsWith("exe"))
                                    u = new URL("http://pilotpc.za.pl/" + linie[i2].split("=")[1] + ".bin");
                                else
                                    u = new URL("http://pilotpc.za.pl/" + linie[i2].split("=")[1]);
                                is = u.openStream();
                                FileOutputStream strumien;
                                if (linie[i2].split("=")[1].compareTo("PilotPC-PC-Java.jar") == 0) {
                                    if (Program.glowneOkno == null)
                                        Program.glowneOkno = new Okno(false);  //otwiera okno, bo potem będzie problem, ale go nie pokazuje
                                    strumien = new FileOutputStream(linie[i2].split("=")[1]);
                                    // strumien=new FileOutputStream(linie[i2].split("=")[1]+".new");
                                } else
                                    strumien = new FileOutputStream(linie[i2].split("=")[1] + ".new");
                                //while(is.available()>0)
                                // for(int i3=0;i3<1000000;i3++)
                                while (true) {
                                    int bajt = is.read();
                                    if (bajt == -1)
                                        break;
                                    strumien.write(bajt);
                                }
                                strumien.close();
                            } catch (IOException ioe) {

                                System.out.println("Oops- an IOException happened.");
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
                        if (linie[i2].split("=")[0].compareTo("plik") == 0 && linie[i2].split("=")[1].compareTo("PilotPC-PC-Java.jar") != 0) {
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