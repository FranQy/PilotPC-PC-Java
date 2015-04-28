import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Mateusz on 2015-03-01.
 * <p/>
 * Klasa umożliwia zapisywanie komunikatów o błędach i wysyłanie ich na serwer
 */
public class Debugowanie {
    public static void Błąd(Throwable e) {
        if (Program.debug)
            e.printStackTrace();
        String stack = "";
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        PrintWriter pri = new PrintWriter(s);
        e.printStackTrace(pri);
        pri.close();

        String komunikat = (new Date()).toString() + "Błąd w programie PilotPC wersja " + Program.wersja + "\r\n" +
                "System:" + System.getProperty("os.name") + "\r\n" +
                System.getProperties().toString() + "\r\n" +
                "\r\n" +
                e.toString() + "\r\n\r\n" +
                s.toString() + "\r\n\r\n";
        try {
            FileWriter plik = new FileWriter("error.txt");
            plik.write(komunikat);
            plik.close();
        } catch (Throwable ee) {
        }
        try {


            Socket soc = new Socket("jaebestudio.tk", 80);
            OutputStream str = soc.getOutputStream();
            BASE64Encoder en = new BASE64Encoder();

            String base = en.encode(komunikat.getBytes());
            base = base.replaceAll("\r", "").replaceAll("\n", "");
            str.write(("POST /debug.php?p=pilotpc HTTP/1.1\r\nHost: jaebestudio.tk\r\n" +
                    "User-Agent: Mozilla/4.0\r\n" +
                    "Content-Length: " + (base.length() + 2) + "\r\nContent-Type: application/x-www-form-urlencoded\r\n" +
                    "Connection: keep-alive\r\n\r\nt=" + base).getBytes());
            str.flush();

            str.close();
            soc.close();

        } catch (Throwable ee) {
        }
        try {


            Socket soc = new Socket("jaebestudio.tk", 80);
            OutputStream str = soc.getOutputStream();
            String base = new sun.misc.BASE64Encoder().encode(komunikat.getBytes());
            str.write(("POST /debug.php?p=pilotpc HTTP/1.1\r\nHost: jaebestudio.tk\r\nContent-Type: application/x-www-form-urlencoded\r\n" +
                    "Connection: keep-alive\r\n\r\nt=" + base + "\r\n\r\n").getBytes());
            str.flush();

            str.close();
            soc.close();

        } catch (Throwable ee) {
        }

    }
}
