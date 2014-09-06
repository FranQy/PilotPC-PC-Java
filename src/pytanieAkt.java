import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mateusz on 30.07.14.
 */
public class pytanieAkt {
    private JButton takButton;
    private JButton nieButton;
    private JPanel tre;
    private JLabel pole;
    public JFrame frame;
    public String[] linie;
    public String urlSerwera;
    String najnowsza;
    public static boolean pytanie1 = true;

    public pytanieAkt(String[] linieZ, String urlSerweraZ, String najn) {
        frame = new JFrame("PytanieAkt");
        frame.setTitle("Aktualizacja PilotPC");
        //frame.setUndecorated(true);
        frame.setContentPane(tre);
        frame.pack();
        frame.setSize(450, 280);
        frame.setVisible(true);
        // Dostępna jest nowa wersja programu PilotPC. Czy chcesz zaktualizować?

        pole.setText(Jezyk.napisy[Jezyk.n.DostepnaNowa.ordinal()]);
        this.linie = linieZ;
        najnowsza = najn;
        this.urlSerwera = urlSerweraZ;

        takButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //frame.setVisible(false);
                if (pytanie1) {
                    pole.setText(Jezyk.napisy[Jezyk.n.TrwaAktualizowanie.ordinal()]);
                    Thread wątek2 = new Thread() {
                        @Override
                        public void run() {
                            Aktualizacja.samoAkt(linie, urlSerwera);
                            Aktualizacja.trwa = false;
                        }
                    };
                    wątek2.start();
                    Thread wątek = new Thread() {
                        @Override
                        public void run() {
                            try {
                                while (true) {
                                    pole.setText(Opcje.status());
                                    if (Aktualizacja.zaktualizowano) {

                                        // frame.setVisible(false);
                                        pytanie1 = false;
                                        pole.setText(Jezyk.napisy[Jezyk.n.Zaktualizowano.ordinal()]);
                                        takButton.setVisible(true);
                                        nieButton.setVisible(true);
                                        return;
                                    }
                                    Thread.sleep(100);
                                }
                            } catch (Throwable ee) {
                                pole.setText(Jezyk.napisy[Jezyk.n.Blad.ordinal()]);
                            }
                        }
                    };
                    wątek.start();
                    takButton.setVisible(false);
                    nieButton.setVisible(false);

                } else {
                    Aktualizacja.reset();
                }
            }
        });
        nieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Program.ustawienia.WersjaOk = najnowsza;
                Program.ustawienia.eksportuj();
            }
        });
    }
}
