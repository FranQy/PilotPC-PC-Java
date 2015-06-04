/**
 *
 */

/**
 * @author Mateusz
 *         Klasa dekoduje ciąg UserAgent który wysyła przeglądarka przy połączeniu i z niego odczytuje informacje.
 */
public class UserAgent {
    /**
     * System operacyjny
     */
    public String OS = null;
    /**
     * Ciąg otrzymany od przeglądarki
     */
    public String UA = null;
    public String urzadzenie = null;

    public UserAgent(String UA) {
        this.UA = UA;
        //System
        if (UA.contains("Windows Phone")) {
            OS = UA.substring(UA.indexOf("Windows Phone"), UA.indexOf(";", UA.indexOf("Windows Phone")));
            String[] explode = UA.split("[;)]");
            urzadzenie = explode[explode.length - 2] + " " + explode[explode.length - 1];
            urzadzenie = UA.substring(UA.lastIndexOf(';')+2);
            urzadzenie = urzadzenie.substring(0, urzadzenie.indexOf(')'));
        } else if (UA.contains("Xbox One")) {
            urzadzenie = "Xbox One";
        } else if (UA.contains("Xbox")) {
            urzadzenie = "Xbox 360";
        } else if (UA.contains("PlayStation 4")) {
            urzadzenie = "PlayStation 4";
        } else if (UA.contains("PLAYSTATION 3")) {
            urzadzenie = "PlayStation 3";
        } else if (UA.contains("Windows")) {
            if (UA.contains("Windows NT 4.0"))
                OS = "Windows NT 4.0";
            else if (UA.contains("Windows NT 5.0"))
                OS = "Windows 2000";
            else if (UA.contains("Windows NT 5.1"))
                OS = "Windows XP";
            else if (UA.contains("Windows NT 5.2"))
                OS = "Windows Server 2003";
            else if (UA.contains("Windows NT 6.0"))
                OS = "Windows Vista";
            else if (UA.contains("Windows NT 6.1"))
                OS = "Windows 7";
            else if (UA.contains("Windows NT 6.2"))
                OS = "Windows 8";
            else if (UA.contains("Windows NT 6.3"))
                OS = "Windows 8.1";
            else
                OS = "Windows";
            urzadzenie = "PC";
        } else if (UA.contains("Android")) {
            try {
                OS = UA.substring(UA.indexOf("Android"), UA.indexOf(";", UA.indexOf("Android")));
                //Matcher regex=Pattern.compile("Mozilla/5.0 \\(Linux; U; Android [0-9\\.]+; [a-zA-Z]+-[a-zA-Z]+; (.+) Build").matcher(UA);
                //Matcher regex=Pattern.compile("; ([^;]+) Build").matcher(UA);
                urzadzenie = UA.substring(UA.indexOf(OS) + OS.length() + 9, UA.indexOf("Build", UA.indexOf(OS) + OS.length() + 7));
            } catch (Exception e) {
                OS = "Android";
            }
        } else if (UA.contains("iPod")) {
            try {
                OS = "iOS " + UA.substring(UA.indexOf("OS") + 3, UA.indexOf("like", UA.indexOf("OS") - 1));
                urzadzenie = "iPod";
            } catch (Exception e) {
                OS = "iOS";
            }
        } else if (UA.contains("iPad")) {
            try {
                OS = "iOS " + UA.substring(UA.indexOf("OS") + 3, UA.indexOf("like", UA.indexOf("OS") - 1));
                urzadzenie = "iPad";
            } catch (Exception e) {
                OS = "iOS";
            }
        } else if (UA.contains("iPhone")) {
            try {
                OS = "iOS " + UA.substring(UA.indexOf("OS") + 3, UA.indexOf("like", UA.indexOf("OS") - 1));
                urzadzenie = "iPhone";
            } catch (Exception e) {
                OS = "iOS";
            }
        } else if (UA.contains("Mac OS X")) {
            OS = UA.substring(UA.indexOf("Mac OS X"), UA.indexOf(")", UA.indexOf("Mac OS X")));
            urzadzenie = "Mac";
        } else if (UA.contains("Linux")) {
            OS = "Linux";
            urzadzenie = "PC";
        } else
            urzadzenie = Jezyk.napisy[Jezyk.n.Nieznane.ordinal()];
    }

    public String toString() {
        return UA;
    }
}
