import org.omg.CORBA.Environment;

import javax.tools.JavaCompiler;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * Created by Mateusz on 2015-03-01.
 *
 * Klasa umożliwia zapisywanie komunikatów o błędach i wysyłanie ich na serwer
 */
public class Debugowanie {
    public static void Błąd(Throwable e)
    {
        if (Program.debug)
            e.printStackTrace();
        String komunikat=(new Date()).toString()+"Błąd w programie PilotPC wersja "+ Program.wersja+"\r\n" +
                "System:"+ System.getProperty("os.name")+"\r\n" +
                System.getProperties().toString()+"\r\n" +
            "\r\n"+
                e.toString()+"\r\n\r\n";
        try{
            FileWriter plik=new FileWriter("error.txt");
            plik.write(komunikat);
        }catch(Throwable ee){}
        try{URL u = new URL("http://jaebestudio.tk/debug.php?t="+komunikat);
            InputStream is = u.openStream();
            is.close();}catch(Throwable ee){}

    }
}
