import java.awt.*;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.Dictionary;
import java.util.Enumeration;


public class Polaczenie {
    public static ServerSocket socServ = null;
    Robot robot;
    static int port = 8753;
    public static HttpPolaczenie[] polaczeniaHttp = new HttpPolaczenie[100];
    public static boolean nasluchiwanie = false;
    public static PolaczenieWatek[] watki = new PolaczenieWatek[100];
    static Dictionary<Integer, Integer> hasłoIPort = new Dictionary<Integer, Integer>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Enumeration<Integer> keys() {
            return null;
        }

        @Override
        public Enumeration<Integer> elements() {
            return null;
        }

        @Override
        public Integer get(Object key) {
            return null;
        }

        @Override
        public Integer put(Integer key, Integer value) {
            return null;
        }

        @Override
        public Integer remove(Object key) {
            return null;
        }
    };

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
            System.out.print("Błąd, port " + port + "zajęty\r\n");
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
