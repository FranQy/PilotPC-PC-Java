import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Odpowiada za obsługę konsoli w trybie tekstowym oraz częściowo za uruchamianie programu z parametrami
 */
public class Konsola extends Thread {
    public void run() {
        Scanner wej = new Scanner(System.in);
        while (true) {
            for (int i = 0; i < 10; i++) {
                try {
                    polecenie((" " + wej.nextLine()).split(" "), true);
            } catch (NoSuchElementException e) {
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
                System.out.println(Jezyk.napisy[Jezyk.n.DostepnePolecenia.ordinal()]);
            } else if (args[1].equalsIgnoreCase("change") || args[1].equalsIgnoreCase("zmień") || args[1].equalsIgnoreCase("zmien")) {
                if (args.length == 2)
                    System.out.println(Jezyk.napisy[Jezyk.n.DostepnePolecenia.ordinal()]);
                else if (args[2].equalsIgnoreCase("kod") || args[2].equalsIgnoreCase("code")) {

                    Program.ustawienia.haslo = Ustawienia.generujHaslo();
                    //kod.setText(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ":");
                    //Kod2.setText(Program.ustawienia.haslo);
                    System.out.print(Jezyk.napisy[Jezyk.n.NowyKod.ordinal()] + ":" + Program.ustawienia.haslo);
                    //qr.odswierz();
                    Program.ustawienia.eksportuj();
                    return false;
                } else
                    System.out.println(Jezyk.napisy[Jezyk.n.NieMoznaZmienic.ordinal()] + " " + args[2].toLowerCase(Locale.getDefault()));
            } else if (args[1].equalsIgnoreCase("show") || args[1].equalsIgnoreCase("pokaz") || args[1].equalsIgnoreCase("pokaż")) {
                if (args.length == 2)
                    System.out.println(Jezyk.napisy[Jezyk.n.DostepnePolecenia.ordinal()]);
                else if (args[2].equalsIgnoreCase("kod") || args[2].equalsIgnoreCase("code")) {

                    System.out.println(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ":" + Program.ustawienia.haslo);
                    return false;
                } else if (args[2].equalsIgnoreCase("window") || args[2].equalsIgnoreCase("okno")) {


                    if (Program.glowneOkno == null)
                        Program.glowneOkno = new Okno();

                    Program.glowneOkno.frame.setVisible(true);
                    return false;
                } else
                    System.out.println(Jezyk.napisy[Jezyk.n.NieMoznaPokazac.ordinal()] + " " + args[2].toLowerCase(Locale.getDefault()));
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
                System.out.println(Jezyk.napisy[Jezyk.n.NieznanePolecenie.ordinal()] + " " + args[1].toLowerCase(Locale.getDefault()) + ", " + Jezyk.napisy[Jezyk.n.WpiszPomoc.ordinal()]);
        }
        return true;
    }
}
