import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

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
    PanelQRCode qr;
    public JFrame frame  ;

    public Okno(){

        frame= new JFrame("Okno");
        frame.setContentPane(zawartosc);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
       kod.setText("Kod do połączenia: " + Program.ustawienia.haslo);
        zmieńKodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Program.ustawienia.haslo = Ustawienia.generujHaslo();
                kod.setText("Kod do połączenia: " + Program.ustawienia.haslo);
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
        String tekstIP = "Twoje IP to:<br/>";
        Enumeration<NetworkInterface> n;
        try {
            n = NetworkInterface.getNetworkInterfaces();

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
        } catch (SocketException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        info.setText("<html>" + tekstIP + "</html>");


        //QRPanel.add(new JPanel(),0);
        qr = new PanelQRCode(QRPanel);
        QRPanel.add(qr);
        java.util.Timer timer1 = new java.util.Timer();
        Odswierz timer1_task = new Odswierz();
        timer1_task.okno = this;
        timer1.schedule(timer1_task, 100, 100);

        frame.setVisible(true);
    }     public class Odswierz extends TimerTask {
        public Okno okno;
        int licznik = 0;

        public void run() {
            String statusTxt = "";
            if (Polaczenie.nasluchiwanie) {
                if (Polaczenie.watki[0].gotowe)
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
                        if (!Polaczenie.watki[i].pokazane && Polaczenie.watki[i].infoPrzyPolaczeniu != null)
                        {
                            Polaczenie.watki[i].pokazane = true;
                            telefony.add(Polaczenie.watki[i].UI = new Urzadzenie(Polaczenie.watki[i], telefony));
                            potrzebneOdswierzenie = true;
                        }
                    }
            }
            for (byte i = 0; i < Polaczenie.polaczeniaHttp.length; i++) {
                if (Polaczenie.polaczeniaHttp[i] != null)
                {
                    long rozn=(new Date()).getTime() - Polaczenie.polaczeniaHttp[i].czas.getTime();
                    if (!Polaczenie.polaczeniaHttp[i].pokazane && rozn < 5000) {
                        Polaczenie.polaczeniaHttp[i].pokazane = true;
                        telefony.add(Polaczenie.polaczeniaHttp[i].UI = new Okno.Urzadzenie(Polaczenie.polaczeniaHttp[i], telefony));
                        potrzebneOdswierzenie = true;
                    } else if (Polaczenie.polaczeniaHttp[i].pokazane&&rozn > 5000) {
                        telefony.remove(Polaczenie.polaczeniaHttp[i].UI);
                        Polaczenie.polaczeniaHttp[i].pokazane = false;
                        potrzebneOdswierzenie = true;

                    }    }
            }
            //if(potrzebneOdswierzenie)
/*{telefony.repaint();//(telefony.getGraphics());
       //telefony.paintImmediately(0, 0, 2000, 2000);
for(int i=0;i<telefony.countComponents();i++)
{
	telefony.getComponent(i).paint(telefony.getComponent(i).getGraphics());
}}*/
            //if (potrzebneOdswierzenie)
            //    okno.paintAll(okno.getGraphics());
            potrzebneOdswierzenie = false;
        }
    }            class Urzadzenie extends JPanel {
        public PolaczenieInfo zrodlo;
        JButton rozlacz = new JButton("Rozłącz");
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
}
