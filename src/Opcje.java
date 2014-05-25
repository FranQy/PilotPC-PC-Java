import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    private JLabel Programisci;
    private JLabel AktualizacjaStatus;
    static Opcje okno;

    public static Opcje pokarz() {
        if (okno == null)
            okno = new Opcje(true);
        okno.frame.setVisible(true);
        return okno;
    }

    public Opcje(boolean wyswietl) {

        frame = new JFrame("Opcje");
        frame.setTitle(Jezyk.napisy[Jezyk.n.Infromacje.ordinal()]);
        if (Program.imgObrazek == null) {
            try {
                Program.imgObrazek = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                //InputStream input = classLoader.getResourceAsStream(
                // "resources/ikona.png");
                Program.imgObrazek = ImageIO.read(classLoader.getResourceAsStream(
                        "resources/ikona.png"));
            } catch (IOException e) {
                Program.imgObrazek = null;
            }
        }
        if (Program.imgObrazek != null)
            frame.setIconImage(Program.imgObrazek);
        //System.out.println("1");
        frame.setContentPane(zawartosc);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Jezyk.jezyki[] langs = Jezyk.jezyki.values();
        for (short i = 0; i < langs.length; i++) {
            WybierzJezyk.addItem(Jezyk.nazwyJezykow[i]);

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
        if (wyswietl) {
            frame.setVisible(true);

        }
    }

    public void ustawJezyk() {
        startZSystememButton.setText(Jezyk.napisy[Jezyk.n.StartZSystemem.ordinal()]);
        if (Program.ustawienia.plynnaMysz)
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WylaczWygladzanieMyszy.ordinal()]);
        else
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WlaczWygladzanieMyszy.ordinal()]);
        NazwaProgramu.setText("PilotPC " + Jezyk.napisy[Jezyk.n.Wersja.ordinal()] + " " + Program.wersja);
        Programisci.setText(Jezyk.napisy[Jezyk.n.Programisci.ordinal()] + ":");

        if (Aktualizacja.zaktualizowano)
            AktualizacjaStatus.setText(Jezyk.napisy[Jezyk.n.AktualizacjaZostanie.ordinal()]);
        else if (Aktualizacja.trwa)
            AktualizacjaStatus.setText(Jezyk.napisy[Jezyk.n.TrwaAktualizowanie.ordinal()]);
        else if (Aktualizacja.najnowsza.equals(""))
            AktualizacjaStatus.setText(Jezyk.napisy[Jezyk.n.TrwaSprawdzanieAKtualizacji.ordinal()]);
        else if (Aktualizacja.najnowsza.equals(Program.wersja))
            AktualizacjaStatus.setText(Jezyk.napisy[Jezyk.n.ProgramAktualny.ordinal()]);
        else
            AktualizacjaStatus.setText(Jezyk.napisy[Jezyk.n.MoznaZaktualizowac.ordinal()] + " " + Aktualizacja.najnowsza);
    }

}
