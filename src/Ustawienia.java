import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * Zawiera wszystkie ustawienia programu, które są zapisywane w pliku
 *
 * @author Mateusz
 */
public class Ustawienia implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public boolean aktualizujAutomatycznie = true;

    /**
     * Znajduje folder z ustawieniami programu
     *
     * @return adres folderu, w którym można zapisać ustawienia
     */
    static String getFolder() {
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN"))
            return System.getenv("APPDATA") + "\\PilotPC-PC-Java";
        else if (OS.contains("MAC"))
            return System.getProperty("user.home") + "/Library/Application/PilotPC-PC-Java "
                    + "Support";
        else if (OS.contains("NUX"))
            return System.getProperty("user.home") + "/.PilotPC-PC-Java";
        return System.getProperty("user.dir") + "/.PilotPC-PC-Java";
    }

    static String generujHaslo() {
        return generujHaslo(true);
    }

    static String generujHaslo(Boolean wyslij) {
        String ret = "";
        for (byte i = 0; i < 6; i++)
            ret += (new Random()).nextInt(9);
        if (wyslij && Polaczenie.port > 8753) {
            try {
                Socket soc = new Socket("localhost", 8753);
                OutputStream output = soc.getOutputStream();
                InputStream input = soc.getInputStream();
                output.write(("pilotpc" + ret + Polaczenie.port + "\n\n\n\n").getBytes());
                soc.close();
            } catch (Throwable e) {

            }
        }

        return ret;
    }

    /**
     * Zapisuje ustawienia do pliku
     */
    public void eksportuj() {
        String folder = getFolder();
        File folderObjekt = new File(folder);
        if (!folderObjekt.exists())
            folderObjekt.mkdirs();
        String nazwaPliku = folder + "/ustawienia.txt";

        try {
            ObjectOutputStream strumien = new ObjectOutputStream(new FileOutputStream(nazwaPliku));
            strumien.writeObject(this);
            strumien.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private Ustawienia() {
    }

    public static Ustawienia importuj() {
        try {
            ObjectInputStream strumien = new ObjectInputStream(new FileInputStream(getFolder() + "/ustawienia.txt"));
            Ustawienia ret = (Ustawienia) strumien.readObject();
            strumien.close();
            return ret;
        } catch (FileNotFoundException e) {
            Ustawienia ret = new Ustawienia();
            ret.eksportuj();
            return ret;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Ustawienia ret = new Ustawienia();
            ret.eksportuj();
            return ret;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Ustawienia ret = new Ustawienia();
            ret.eksportuj();
            return ret;
        }
    }

    /**
     * Hasło urzywane do łączenia się
     */
    public String haslo = generujHaslo(false);
    public Jezyk.jezyki jezyk = Jezyk.jezyki.Polski;
    public boolean plynnaMysz = false;
    /**
     * wersja, przy której urzytkownik kliknął "nie aktualizować"
     */
    public String WersjaOk = "0.0.0";
}
