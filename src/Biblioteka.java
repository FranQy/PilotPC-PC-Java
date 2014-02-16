/**
 * Created by Mateusz on 05.01.14.
 */
public class Biblioteka {
    public static boolean zaladowano = false;

    public static void load() {
        if (zaladowano)
            return;
        try{

            System.loadLibrary("pilotpc");
            zaladowano = true;
        }
        catch(Throwable e)
        {
            e.printStackTrace();
            try{
                System.loadLibrary("pilotpc-x64");
                zaladowano = true;
            }
            catch(UnsatisfiedLinkError f)
            {
                f.printStackTrace();
                Aktualizacja.wymus=true;
            }
            catch(Throwable f)
            {
                System.out.println("Błąd z ładowaniem biblioteki! Niektóre elementy programu nie będą działały poprawnie.");
                f.printStackTrace();

            }
        }

    }
    static public native void click(int i);

    static public native void autostart(boolean wlacz, boolean wszyscy, String folder);
    static public void sprawdz(){}
}
