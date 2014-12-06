import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mateusz on 01.04.14.
 */
public class Opcje {
    private Timer timer1;
    public JFrame frame;
    private JPanel zawartosc;
    private JButton gladkaMysz;
    private JButton startZSystememButton;
    private JComboBox WybierzJezyk;
    private JLabel NazwaProgramu;
    private JLabel Programisci;
    private JLabel AktualizacjaStatus;
    private JCheckBox AktualizujAutomatycznie;
    private JButton AktualizujTeraz;
    private JPanel APOpcje;
    private JTextField APNazwa;
    private JTextField APHaslo;
    private JButton APWlacz;
    private JButton APWylacz;
    private JLabel APTytul;
    private JLabel APNazwaLabel;
    private JLabel APHasloLabel;
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
        if (!AccessPoint.dostepny()) {
            APOpcje.setVisible(false);
        } else {
            APWlacz.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AccessPoint.start(APNazwa.getText(), APHaslo.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            APWylacz.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AccessPoint.stop();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
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
                ustawJezyk();
            }
        });
        AktualizujAutomatycznie.setSelected(Program.ustawienia.aktualizujAutomatycznie);
        AktualizujAutomatycznie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Program.ustawienia.aktualizujAutomatycznie = AktualizujAutomatycznie.isSelected();
                Program.ustawienia.eksportuj();
            }
        });
        AktualizujTeraz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Aktualizacja.wymus = true;

                java.util.Timer timer1 = new java.util.Timer();
                Aktualizacja timer1_task = new Aktualizacja();
                timer1.schedule(timer1_task, 0);
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
        frame.setSize(510, 370);
        if (wyswietl) {
            frame.setVisible(true);

        }


        timer1 = new java.util.Timer();
        Odswierz timer1_task = new Odswierz();
        timer1.schedule(timer1_task, 0, 100);
    }

    public void ustawJezyk() {
        startZSystememButton.setText(Jezyk.napisy[Jezyk.n.StartZSystemem.ordinal()]);
        try{
            if(Biblioteka.CzyAutostart())
                startZSystememButton.setText(Jezyk.napisy[Jezyk.n.WylaczStartZSystemem.ordinal()]);
            else
            startZSystememButton.setText(Jezyk.napisy[Jezyk.n.WlaczStartZSystemem.ordinal()]);
        }
        catch(Throwable e){}
        if (Program.ustawienia.plynnaMysz)
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WylaczWygladzanieMyszy.ordinal()]);
        else
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WlaczWygladzanieMyszy.ordinal()]);
        NazwaProgramu.setText("PilotPC " + Jezyk.napisy[Jezyk.n.Wersja.ordinal()] + " " + Program.wersja);
        Programisci.setText(Jezyk.napisy[Jezyk.n.Programisci.ordinal()] + ":");
        AktualizujTeraz.setText(Jezyk.napisy[Jezyk.n.AktualizujTeraz.ordinal()]);
        AktualizujAutomatycznie.setText(Jezyk.napisy[Jezyk.n.AktualizacjaAutomatyzna.ordinal()]);
        aktNapis();
        APNazwaLabel.setText(Jezyk.napisy[Jezyk.n.Nazwa.ordinal()]);
        APHasloLabel.setText(Jezyk.napisy[Jezyk.n.Haslo.ordinal()]);
        APWlacz.setText(Jezyk.napisy[Jezyk.n.Start.ordinal()]);
        APWylacz.setText(Jezyk.napisy[Jezyk.n.Stop.ordinal()]);
        APTytul.setText(Jezyk.napisy[Jezyk.n.TworzenieSieciWiFi.ordinal()]);

    }

    public void aktNapis() {
        AktualizacjaStatus.setText(status());
    }

    public static String status() {
        if (Aktualizacja.zaktualizowano)
            return (Jezyk.napisy[Jezyk.n.AktualizacjaZostanie.ordinal()]);
        else if (Aktualizacja.trwa)
            return (Jezyk.napisy[Jezyk.n.TrwaAktualizowanie.ordinal()]);
        else if (Aktualizacja.najnowsza.equals(""))
            return (Jezyk.napisy[Jezyk.n.TrwaSprawdzanieAKtualizacji.ordinal()]);
        else if (Aktualizacja.najnowsza.equals(Program.wersja))
            return (Jezyk.napisy[Jezyk.n.ProgramAktualny.ordinal()]);
        else
            return (Jezyk.napisy[Jezyk.n.MoznaZaktualizowac.ordinal()] + " " + Aktualizacja.najnowsza);

    }

    public class Odswierz extends TimerTask {

        @Override
        public void run() {
            aktNapis();
        }
    }
}
