import java.io.IOException;

/**
 * Umożliwia wykorzystanie karty sieciowej komputera jako AP po wifi, na razie tylko dla windows 7 i nowszych
 */
public class AccessPoint {
    public static void start(String nazwa, String klucz) throws IOException {
        if (System.getProperty("os.name").toUpperCase().contains("WIN")) {
            Biblioteka.runAsRoot("netsh", "wlan set hostednetwork mode=allow \"ssid=" + nazwa + "\" \"key=" + klucz + "\" keyUsage=temporary");
            Biblioteka.runAsRoot("netsh", "wlan start hostednetwork");
        }
    }

    public static void stop() throws IOException {
        if (System.getProperty("os.name").toUpperCase().contains("WIN")) {
            Biblioteka.runAsRoot("netsh", "wlan stop hostednetwork");
        }
    }

    /**
     * Informuje, czy na obecnym systemie można odpalić AP
     *
     * @return
     */
    public static boolean dostepny() {
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            if (System.getProperty("os.name").length() > 8 && System.getProperty("os.name").charAt(8) >= '0' && System.getProperty("os.name").charAt(8) <= '9') {
                return true;
            }
        }
        return false;
    }
}
