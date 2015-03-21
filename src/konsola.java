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
                Debugowanie.Błąd(e);
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
        try{
        if (args.length >= 2 && args[1].charAt(0) != '/' && args[1].charAt(0) != '-') {

            if (args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("pomoc")|| args[1].equalsIgnoreCase("помощь")) {
                System.out.println(Jezyk.napisy[Jezyk.n.DostepnePolecenia.ordinal()]);
            } else if (args[1].equalsIgnoreCase("change") || args[1].equalsIgnoreCase("zmień") || args[1].equalsIgnoreCase("zmien")|| args[1].equalsIgnoreCase("измените")) {
                if (args.length == 2)
                    System.out.println(Jezyk.napisy[Jezyk.n.DostepnePolecenia.ordinal()]);
                else if (args[2].equalsIgnoreCase("kod") || args[2].equalsIgnoreCase("code")|| args[2].equalsIgnoreCase("код")) {

                    Program.ustawienia.haslo = Ustawienia.generujHaslo();
                    //kod.setText(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ":");
                    //Kod2.setText(Program.ustawienia.haslo);
                    System.out.print(Jezyk.napisy[Jezyk.n.NowyKod.ordinal()] + ":" + Program.ustawienia.haslo);
                    //qr.odswierz();
                    Program.ustawienia.eksportuj();
                    return false;
                } else
                    System.out.println(Jezyk.napisy[Jezyk.n.NieMoznaZmienic.ordinal()] + " " + args[2].toLowerCase(Locale.getDefault()));
            } else if (args[1].equalsIgnoreCase("show") || args[1].equalsIgnoreCase("pokaz") ||args[1].equalsIgnoreCase("pokarz") ||args[1].equalsIgnoreCase("pokaż") || args[1].equalsIgnoreCase("покажи")|| args[1].equalsIgnoreCase("покажите")) {
                if (args.length == 2)
                    System.out.println(Jezyk.napisy[Jezyk.n.DostepnePolecenia.ordinal()]);
                else if (args[2].equalsIgnoreCase("kod") || args[2].equalsIgnoreCase("code")|| args[2].equalsIgnoreCase("код")) {

                    System.out.println(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ":" + Program.ustawienia.haslo);
                    return false;
                } else if (args[2].equalsIgnoreCase("window") || args[2].equalsIgnoreCase("okno") || args[2].equalsIgnoreCase("окно")) {


                    if (Program.glowneOkno == null)
                        Program.glowneOkno = new Okno();

                    Program.glowneOkno.frame.setVisible(true);
                    return false;
                }else if (args[2].equalsIgnoreCase("options") ||args[2].equalsIgnoreCase("settings") || args[2].equalsIgnoreCase("opcje") || args[2].equalsIgnoreCase("utawienia")) {


                    Opcje.pokarz();
                    return false;
                } else
                    System.out.println(Jezyk.napisy[Jezyk.n.NieMoznaPokazac.ordinal()] + " " + args[2].toLowerCase(Locale.getDefault()));
            } else if (args[1].equalsIgnoreCase("exit") ||args[1].equalsIgnoreCase("end") || args[1].equalsIgnoreCase("wyjdź") || args[1].equalsIgnoreCase("wyjdz") ||  args[1].equalsIgnoreCase("конец")) {
                if (!Aktualizacja.trwa)
                    System.exit(0);
                else
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        Debugowanie.Błąd(e1);
                    }
            }else if (args[1].equalsIgnoreCase("beta")) {
                if (args[2].equalsIgnoreCase("on"))
                    Aktualizacja.katalog="beta/";
               else
                    Aktualizacja.katalog="";

            }else if (args[1].equalsIgnoreCase("close") || args[1].equalsIgnoreCase("zamknij")) {
                if (args.length>2&&(args[2].equalsIgnoreCase("window") || args[2].equalsIgnoreCase("okno") || args[2].equalsIgnoreCase("окно"))) {
                    if (Program.glowneOkno == null)
                        System.out.println("Nie można zaknąć okna, jeśli nie zostało otwarte. " + Jezyk.napisy[Jezyk.n.WpiszPomoc.ordinal()]);
else
                        Program.glowneOkno.frame.setVisible(false);
                }
                else if (args.length>2&&(args[2].equalsIgnoreCase("options") ||args[2].equalsIgnoreCase("settings") || args[2].equalsIgnoreCase("opcje") || args[2].equalsIgnoreCase("utawienia"))) {
                    if (Opcje.okno == null)
                        System.out.println("Nie można zaknąć okna, jeśli nie zostało otwarte. " + Jezyk.napisy[Jezyk.n.WpiszPomoc.ordinal()]);
                    else
                        Opcje.zamknij();
                }

                    else if (!Aktualizacja.trwa)
                    System.exit(0);
                else
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block

                        Debugowanie.Błąd(e1);
                    }
            }
            else if (args[1].equalsIgnoreCase("aktualizacja") || args[1].equalsIgnoreCase("update")) {
                if (args.length>2&&(args[2].equalsIgnoreCase("on") || args[2].equalsIgnoreCase("włącz") || args[2].equalsIgnoreCase("wlacz"))) {
                    Program.ustawienia.aktualizujAutomatycznie = true;
                    Program.ustawienia.eksportuj();
                    System.out.println("Automatyczna aktualizacja została włączona");
                }
           else if (args.length>2&&(args[2].equalsIgnoreCase("off") || args[2].equalsIgnoreCase("wyłącz") || args[2].equalsIgnoreCase("wylacz")) ){
                    Program.ustawienia.aktualizujAutomatycznie = false;
                    Program.ustawienia.eksportuj();
                    System.out.println("Automatyczna aktualizacja została wyłączona");}
       else if (args.length>2&&(args[2].equalsIgnoreCase("now") || args[2].equalsIgnoreCase("teraz"))) {
                    System.out.print("Rozpoczęto aktualizację");
                    Aktualizacja.wymus = true;

                    java.util.Timer timer1 = new java.util.Timer();
                    Aktualizacja timer1_task = new Aktualizacja();
                    timer1.schedule(timer1_task, 0);
                }
            else{
                    System.out.println(Opcje.status());
                    System.out.print("Automatyczna aktualizacja jest w");
                    if(!Program.ustawienia.aktualizujAutomatycznie)
                        System.out.print("y");
                    System.out.print("łączona\n");
                    System.out.println("Dostępne polecenia:\n  aktualizacja włącz\n  aktualizacja wyłącz\n  aktualizacja teraz");
                }}

            else
                System.out.println(Jezyk.napisy[Jezyk.n.NieznanePolecenie.ordinal()] + " " + args[1].toLowerCase(Locale.getDefault()) + ", " + Jezyk.napisy[Jezyk.n.WpiszPomoc.ordinal()]);
        }
    }catch (Throwable e){Debugowanie.Błąd(e);}
        return true;
}}
