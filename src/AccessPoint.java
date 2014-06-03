import java.io.IOException;

/**
 * Umożliwia wykorzystanie karty sieciowej komputera jako AP po wifi, na razie tylko dla windows 7 i nowszych
 */
public class AccessPoint {
    public static void start(String nazwa, String klucz) throws IOException {
        if (System.getProperty("os.name").toUpperCase().contains("WIN")) {
            Runtime.getRuntime().exec("netsh wlan set hostednetwork mode=allow \"ssid=" + nazwa + "\" \"key=" + klucz + "\" keyUsage=temporary");
            Runtime.getRuntime().exec("netsh wlan start hostednetwork");
        }
    }
}
