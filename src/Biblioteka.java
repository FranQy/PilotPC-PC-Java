/**
 * Created by Mateusz on 05.01.14.
 */
public class Biblioteka {
    public static boolean zaladowano = false;

    public static void load() {
        if (zaladowano)
            return;
        String OS = System.getProperty("os.name").toUpperCase();
        //if (OS.contains("WIN")) {
        try {

            System.loadLibrary("pilotpc");
            zaladowano = true;
        } catch (Throwable e) {
            try {

                System.loadLibrary("lib//pilotpc");
                zaladowano = true;
            } catch (Throwable e2) {
                try {

                    System.loadLibrary("..//lib//pilotpc");
                    zaladowano = true;
                } catch (Throwable e3) {
                    try {

                        System.loadLibrary("..//lib//pilotpc-x64");
                        zaladowano = true;
                    } catch (Throwable e4) {
                        try {

                            System.loadLibrary("lib//pilotpc-x64");
                            zaladowano = true;
                        } catch (Throwable e5) {
                            // e.printStackTrace();
                            try {
                                System.loadLibrary("pilotpc-x64");
                                zaladowano = true;
                            } catch (UnsatisfiedLinkError f) {
                                //f.printStackTrace();
                                System.out.println("Nie załadowano dodatkowych bibliotek, nastąpi pobraniez internetu");
                                Aktualizacja.wymus = true;
                            } catch (Throwable f) {
                                System.out.println(Jezyk.napisy[Jezyk.n.BladZLadowaniemBiblioteki.ordinal()]);
                                //f.printStackTrace();

                            }
                        }
                    }
                }
            }
        }
       /* } else

        {
            try {

                System.loadLibrary("pilotpc");
                zaladowano = true;
            } catch (Throwable e) {
                //e.printStackTrace();
                try {
                    System.loadLibrary("pilotpc-x64");
                    zaladowano = true;
                } catch (UnsatisfiedLinkError f) {
                    //f.printStackTrace();
                    Aktualizacja.wymus = true;
                } catch (Throwable f) {
                    System.out.println(Jezyk.napisy[Jezyk.n.BladZLadowaniemBiblioteki.ordinal()]);
                    //f.printStackTrace();

                }
            }
        } */

    }

    static public native void click(int i);

    static public native void autostart(boolean wlacz, boolean wszyscy, String folder);
    static public native boolean CzyAutostart();

    static public native void runAsRoot(String polecenie, String atr);

    static public void sprawdz() {
    }
}
