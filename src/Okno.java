import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.TimerTask;

/**
 * Created by Mateusz on 04.01.14.
 */
public class Okno {

    private JButton zmieńKodButton;
    private JButton informacjeButton;
    private JButton startZSystememButton;
    public static boolean potrzebneOdswierzenie = false;
    private JLabel kod;
    private JLabel info;
    private JPanel telefony;
    private JPanel zawartosc;
    private JPanel QRPanel;
    private JComboBox WybierzJezyk;
    private JLabel PodlaczoneUrzadzenia;
    private JButton gladkaMysz;
    PanelQRCode qr;
    public JFrame frame;

    public Okno() {
        this(true);
    }

    public Okno(boolean wyswietl) {

        frame = new JFrame("PilotPC");

        //System.out.println("1");
        frame.setContentPane(zawartosc);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        telefony.setLayout(new GridLayout(0, 1));
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
        kod.setText(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ": " + Program.ustawienia.haslo);
        ustawJezyk();
        zmieńKodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Program.ustawienia.haslo = Ustawienia.generujHaslo();
                kod.setText(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ": " + Program.ustawienia.haslo);
                qr.odswierz();
                Program.ustawienia.eksportuj();
            }
        });
        informacjeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OProgramie();
            }
        });
        startZSystememButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Program.autostart();
            }
        });
        //System.out.println("2");

        (new Start()).start();
        //System.out.println("3");

        //QRPanel.add(new JPanel(),0);
        qr = new PanelQRCode(QRPanel);
        QRPanel.add(qr);
        java.util.Timer timer1 = new java.util.Timer();
        Odswierz timer1_task = new Odswierz();
        timer1_task.okno = this;
        timer1.schedule(timer1_task, 100, 100);
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
        // System.out.println("4");
        frame.setSize(750, 400);
        if (wyswietl)
            frame.setVisible(true);
        //System.out.println("5");
    }

    public void ustawJezyk() {
        (new Start()).start();
        zmieńKodButton.setText(Jezyk.napisy[Jezyk.n.ZmienKod.ordinal()]);
        informacjeButton.setText(Jezyk.napisy[Jezyk.n.Infromacje.ordinal()]);
        startZSystememButton.setText(Jezyk.napisy[Jezyk.n.StartZSystemem.ordinal()]);
        kod.setText(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ": " + Program.ustawienia.haslo);
        PodlaczoneUrzadzenia.setText(Jezyk.napisy[Jezyk.n.PodlaczoneUrzadzenia.ordinal()]);
        if (Program.ustawienia.plynnaMysz)
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WylaczWygladzanieMyszy.ordinal()]);
        else
            gladkaMysz.setText(Jezyk.napisy[Jezyk.n.WlaczWygladzanieMyszy.ordinal()]);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public class Odswierz extends TimerTask {
        public Okno okno;
        int licznik = 0;

        public void run() {
            String statusTxt = "";
            if (Polaczenie.nasluchiwanie) {
                if (Polaczenie.watki[0] != null && Polaczenie.watki[0].gotowe)
                    statusTxt = "Gotowe!";
                else
                    statusTxt = "Inicjowanie...";
            }
            if (Aktualizacja.zaktualizowano)
                statusTxt += " Zaktualizowano do nowej wersji!";
            else if (Aktualizacja.trwa)
                statusTxt += " Trwa aktualizacja do nowej wersji...";
            // status.setText(statusTxt);
            for (byte i = 0; i < Polaczenie.watki.length; i++) {
                if (Polaczenie.watki[i] != null)
                    if (Polaczenie.watki[i].czyPolaczono()) {
                        if (!Polaczenie.watki[i].pokazane && Polaczenie.watki[i].infoPrzyPolaczeniu != null) {
                            Polaczenie.watki[i].pokazane = true;
                            telefony.add(Polaczenie.watki[i].UI = new Urzadzenie(Polaczenie.watki[i], telefony));
                            potrzebneOdswierzenie = true;
                        }
                    }
            }
            for (byte i = 0; i < Polaczenie.polaczeniaHttp.length; i++) {
                if (Polaczenie.polaczeniaHttp[i] != null) {
                    long rozn = (new Date()).getTime() - Polaczenie.polaczeniaHttp[i].czas.getTime();
                    if (!Polaczenie.polaczeniaHttp[i].pokazane && rozn < 5000) {
                        Polaczenie.polaczeniaHttp[i].pokazane = true;
                        telefony.add(Polaczenie.polaczeniaHttp[i].UI = new Okno.Urzadzenie(Polaczenie.polaczeniaHttp[i], telefony));
                        potrzebneOdswierzenie = true;
                    } else if (Polaczenie.polaczeniaHttp[i].pokazane && rozn > 5000) {
                        telefony.remove(Polaczenie.polaczeniaHttp[i].UI);
                        Polaczenie.polaczeniaHttp[i].pokazane = false;
                        potrzebneOdswierzenie = true;

                    }
                }
            }
            //if(potrzebneOdswierzenie)
/*{telefony.repaint();//(telefony.getGraphics());
       //telefony.paintImmediately(0, 0, 2000, 2000);
for(int i=0;i<telefony.countComponents();i++)
{
	telefony.getComponent(i).paint(telefony.getComponent(i).getGraphics());
}}*/
            if (potrzebneOdswierzenie) {
                telefony.paintAll(telefony.getGraphics());
                zawartosc.paintAll(zawartosc.getGraphics());
            }
            potrzebneOdswierzenie = false;
        }
    }

    public class Urzadzenie extends JPanel {
        public PolaczenieInfo zrodlo;
        JButton rozlacz = new JButton(Jezyk.napisy[Jezyk.n.Rozlacz.ordinal()]);
        JLabel tekst = new JLabel();
        JPanel ramka;

        public Urzadzenie(PolaczenieInfo z, JPanel telefony) {
            setBackground(new Color(46, 46, 46));
            tekst.setForeground(Color.white);
            rozlacz.setBackground(Color.white);
            zrodlo = z;
            ramka = telefony;
            rozlacz.setLocation(0, 0);
            rozlacz.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // TODO Auto-generated method stub
                    zrodlo.rozlacz();
                }
            });
            add(rozlacz);
            tekst.setText(z.toString());
            setToolTipText(zrodlo.opis());
            tekst.setToolTipText(zrodlo.opis());
            add(tekst);

        }

        @Override
        protected void paintComponent(Graphics g) {
            String nowaNazwa = zrodlo.toString();
            if (tekst.getText().compareTo(nowaNazwa) != 0)
                tekst.setText(nowaNazwa);
            super.paintComponent(g);

        }
    }

    class Start extends Thread {

        @Override
        public void run() {
            String tekstIP = Jezyk.napisy[Jezyk.n.TwojeIPTo.ordinal()] + ":<br/>";
            Enumeration<NetworkInterface> n;
            try {
                n = NetworkInterface.getNetworkInterfaces();

                //System.out.println("2a");
                for (; n.hasMoreElements(); ) {
                    NetworkInterface e = n.nextElement();
                    // System.out.println("Interface: " + e.getName());
                    Enumeration<InetAddress> a = e.getInetAddresses();
                    for (; a.hasMoreElements(); ) {
                        InetAddress addr = a.nextElement();
                        if (!addr.isLoopbackAddress() && addr.getAddress().length == 4)
                            tekstIP += addr.getHostAddress() + "<br/>";
                    }
                }
                // System.out.println("2b");
            } catch (SocketException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            info.setText("<html>" + tekstIP + "</html>");
        }
    }
}
