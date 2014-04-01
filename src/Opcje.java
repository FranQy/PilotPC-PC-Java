import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mateusz on 01.04.14.
 */
public class Opcje {
    public JFrame frame;
    private JPanel zawartosc;
    private JButton gladkaMysz;
    private JButton startZSystememButton;
    private JComboBox WybierzJezyk;
    private JLabel NazwaProgramu;
    static Opcje okno;

    public static Opcje pokarz() {
        if (okno == null)
            okno = new Opcje(true);
        okno.frame.setVisible(true);
        return okno;
    }

    public Opcje(boolean wyswietl) {

        frame = new JFrame("Opcje");

        //System.out.println("1");
        frame.setContentPane(zawartosc);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Jezyk.jezyki[] langs = Jezyk.jezyki.values();
        for (short i = 0; i < langs.length; i++) {
            WybierzJezyk.addItem(langs[i].toString());

            if (Program.ustawienia.jezyk == langs[i])
                WybierzJezyk.setSelectedIndex(i);
        }
        WybierzJezyk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Program.ustawienia.jezyk = Jezyk.jezyki.values()[WybierzJezyk.getSelectedIndex()];
                Jezyk.przeladuj(Program.ustawienia.jezyk);
                Program.ustawienia.eksportuj();
            }
        });
        startZSystememButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Program.autostart();
            }
        });
        if (Program.ustawienia.plynnaMysz)
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WylaczWygladzanieMyszy.ordinal()]);
        else
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WlaczWygladzanieMyszy.ordinal()]);
        gladkaMysz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Program.ustawienia.plynnaMysz = !Program.ustawienia.plynnaMysz;
                if (Program.ustawienia.plynnaMysz) {
                    (new WatekMouseRobot()).start();
                    gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WylaczWygladzanieMyszy.ordinal()]);
                } else
                    gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WlaczWygladzanieMyszy.ordinal()]);

                Program.ustawienia.eksportuj();
            }
        });
        ustawJezyk();
        // System.out.println("4");
        frame.setSize(400, 250);
        if (wyswietl)
            frame.setVisible(true);
    }

    public void ustawJezyk() {
        startZSystememButton.setText(Jezyk.napisy[Jezyk.n.StartZSystemem.ordinal()]);
        if (Program.ustawienia.plynnaMysz)
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WylaczWygladzanieMyszy.ordinal()]);
        else
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WlaczWygladzanieMyszy.ordinal()]);
        NazwaProgramu.setText("PilotPC " + Jezyk.napisy[Jezyk.n.Wersja.ordinal()] + " " + Program.wersja);
    }

}
