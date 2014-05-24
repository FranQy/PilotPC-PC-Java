import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Mateusz on 01.05.14.
 */
public class Konsola extends Thread {
    public void run() {
        Scanner wej = new Scanner(System.in);
        while (true) {
            try {
                polecenie((" " + wej.nextLine()).split(" "), true);
            } catch (NoSuchElementException e) {
            }
        }

        /*try {
            polecenie((" " + wej.nextLine()).split(" "), true);
        } catch (NoSuchElementException e) {
        }
        try {
            polecenie((" " + wej.nextLine()).split(" "), true);
        } catch (NoSuchElementException e) {
        }     */
    }

    public static boolean polecenie(String args[], boolean nasobie) {
        if (args.length >= 2 && args[1].charAt(0) != '/' && args[1].charAt(0) != '-') {

            if (args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("pomoc")) {
                System.out.println("Dostępne polecenia:\r\n  zmień kod\r\n  pokarz kod\r\n  pokarz okno\r\n  zamknij");
            } else if (args[1].equalsIgnoreCase("change") || args[1].equalsIgnoreCase("zmień") || args[1].equalsIgnoreCase("zmien")) {
                if (args.length == 2)
                    System.out.println("Polecenia:\r\n  zmień kod");
                else if (args[2].equalsIgnoreCase("kod") || args[2].equalsIgnoreCase("code")) {

                    Program.ustawienia.haslo = Ustawienia.generujHaslo();
                    //kod.setText(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ":");
                    //Kod2.setText(Program.ustawienia.haslo);
                    System.out.print("Nowy kod:" + Program.ustawienia.haslo);
                    //qr.odswierz();
                    Program.ustawienia.eksportuj();
                    return false;
                } else
                    System.out.println("Nie można zmienić " + args[2].toLowerCase(Locale.getDefault()));
            } else if (args[1].equalsIgnoreCase("show") || args[1].equalsIgnoreCase("pokarz")) {
                if (args.length == 2)
                    System.out.println("Polecenia:\r\n  pokarz kod\r\n  pokarz okno");
                else if (args[2].equalsIgnoreCase("kod") || args[2].equalsIgnoreCase("code")) {

                    System.out.println("Kod:" + Program.ustawienia.haslo);
                    return false;
                } else if (args[2].equalsIgnoreCase("window") || args[2].equalsIgnoreCase("okno")) {


                    if (Program.glowneOkno == null)
                        Program.glowneOkno = new Okno();

                    Program.glowneOkno.frame.setVisible(true);
                    return false;
                } else
                    System.out.println("Nie można pokazać " + args[2].toLowerCase(Locale.getDefault()));
            } else if (args[1].equalsIgnoreCase("exit") || args[1].equalsIgnoreCase("wyjdź") || args[1].equalsIgnoreCase("wyjdz") || args[1].equalsIgnoreCase("zamknij")) {
                if (!Aktualizacja.trwa)
                    System.exit(0);
                else
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
            } else
                System.out.println("Nieznane polecenie " + args[1].toLowerCase(Locale.getDefault()) + ", wpisz pomoc");
        }
        return true;
    }
}
