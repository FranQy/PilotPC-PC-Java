import java.util.Timer;

/**
 * Created by Mateusz on 05.01.14.
 */
public class Biblioteka {

    static {


        try{

            System.loadLibrary("pilotpc");
        }
        catch(Throwable e)
        {
            e.printStackTrace();
            try{
                System.loadLibrary("pilotpc-x64");
            }
            catch(UnsatisfiedLinkError f)
            {
                f.printStackTrace();
                Timer timer1 = new Timer();
                Aktualizacja timer1_task = new Aktualizacja();
                Aktualizacja.wymus=true;
                timer1.schedule (timer1_task, 0, 0);
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
