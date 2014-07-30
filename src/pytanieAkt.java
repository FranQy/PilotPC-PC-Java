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
    public JFrame frame;
    public String[] linie;
    public String urlSerwera;
    String najnowsza;

    public pytanieAkt(String[] linieZ, String urlSerweraZ, String najn) {
        frame = new JFrame("PytanieAkt");
        frame.setTitle("Aktualizacja PilotPC");
        //frame.setUndecorated(true);
        frame.setContentPane(tre);
        frame.pack();
        frame.setSize(450, 280);
        frame.setVisible(true);

        this.linie = linieZ;
        najnowsza = najn;
        this.urlSerwera = urlSerweraZ;

        takButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Aktualizacja.samoAkt(linie, urlSerwera);
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
