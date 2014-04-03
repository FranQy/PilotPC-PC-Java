import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    public static boolean potrzebneOdswierzenie = false;
    private JLabel kod;
    private JLabel info;
    private JPanel telefony;
    private JPanel zawartosc;
    private JPanel QRPanel;
    private JLabel PodlaczoneUrzadzenia;
    private JButton powiększQRCodeButton;
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
                Opcje.pokarz();
            }
        });

        powiększQRCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okienkoQR = new JFrame();
                JPanel okienkoQRPanel = new JPanel();
                okienkoQR.add(okienkoQRPanel);
                PanelQRCode qrO = new PanelQRCode(okienkoQRPanel);
                okienkoQRPanel.add(qrO);
                okienkoQR.setUndecorated(true);
                okienkoQR.setExtendedState(Frame.MAXIMIZED_BOTH);
                okienkoQR.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okienkoQR.dispose();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                okienkoQR.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        okienkoQR.dispose();
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });
                okienkoQR.setVisible(true);

            }
        });
        //System.out.println("2");
        frame.setSize(750, 400);
        (new Start()).start();
        //System.out.println("3");

        //QRPanel.add(new JPanel(),0);
        qr = new PanelQRCode(QRPanel);
        QRPanel.add(qr);
        java.util.Timer timer1 = new java.util.Timer();
        Odswierz timer1_task = new Odswierz();
        timer1_task.okno = this;
        timer1.schedule(timer1_task, 100, 100);
        //System.out.println("5");
    }

    JFrame okienkoQR;

    public void ustawJezyk() {
        (new Start()).start();
        zmieńKodButton.setText(Jezyk.napisy[Jezyk.n.ZmienKod.ordinal()]);
        informacjeButton.setText(Jezyk.napisy[Jezyk.n.Infromacje.ordinal()]);
        kod.setText(Jezyk.napisy[Jezyk.n.KodDoPolaczenia.ordinal()] + ": " + Program.ustawienia.haslo);
        PodlaczoneUrzadzenia.setText(Jezyk.napisy[Jezyk.n.PodlaczoneUrzadzenia.ordinal()]);
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
