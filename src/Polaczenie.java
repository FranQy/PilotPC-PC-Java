import java.awt.*;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class Polaczenie {
    public static ServerSocket socServ = null;
    Robot robot;
    static int port = 8753;
  //  public static Hashtable<Long, HttpPolaczenie> polaczeniaHttp = new Hashtable<Long, HttpPolaczenie>();
    public static HttpPolaczenie[] polaczeniaHttp=new HttpPolaczenie[120];
    public static boolean nasluchiwanie = false;
    public static PolaczenieWatek[] watki = new PolaczenieWatek[100];
    static Map<Integer, Integer> hasłoIPort = new HashMap<Integer, Integer>();
    /*
    Zabezpieczenie przed BruceForce
     */
    static long czasBlokadyHasla = 0;

    public Polaczenie() {

        while (true) {
            try {
                if (socServ == null)
                    socServ = new ServerSocket(port);
                nasluchiwanie = true;
                //System.out.print("Nasłuchiwanie na porcie "+port+"\r\n");
                for (byte i = 0; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
                {
                    if (watki[i] == null) {
                        watki[i] = new PolaczenieWatek();
                        watki[i].socServ = socServ;
                        watki[i].start();
                        break;
                    }
                }
                Thread.sleep(100);
                break;
            } catch (BindException e) {
                //System.out.print("Błąd, port " + port + "zajęty\r\n");
                port++;

                /**
                 * bo teraz przy rozlaczaniu to wywala
                 */
            } catch (IOException e) {

                System.out.print(Jezyk.napisy[Jezyk.n.PilotPCWersja.ordinal()] + "\r\n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
