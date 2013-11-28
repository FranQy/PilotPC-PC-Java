import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Okno extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Tekst status=new Tekst("Inicjowanie", new Point(105,2));
	Tekst kod=new Tekst("Kod do połączenia: "+Program.ustawienia.haslo, new Point(0,26));
	Urzadzenia telefony;
	PanelQRCode qr;
	Przycisk przyciskInformacje;
	private MouseListener zmienKodClick=new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Program.ustawienia.haslo=Ustawienia.generujHaslo();
			kod.setText("Kod do połączenia: "+Program.ustawienia.haslo);
			qr.odswierz();
			Program.ustawienia.eksportuj();
			
			
		}
	};
public Okno()
{
	super("SockedServer");
this.setSize(750,550);
//setLayout(new GridLayout());
Panel lewy=new Panel();
qr=new PanelQRCode(lewy);
telefony=new Urzadzenia(lewy);
przyciskInformacje=new Przycisk("Informacje",new Dimension(100, 20),new Point(0,0));
ActionListener przyciskInformacjeListener=new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		new OProgramie();
	}
};
przyciskInformacje.addActionListener(przyciskInformacjeListener);
przyciskInformacje.setSize(100, 20);
przyciskInformacje.setMaximumSize(new Dimension(100, 20));
przyciskInformacje.setLocation(0,0);
lewy.add(przyciskInformacje);
status.setLocation(105, 0);
lewy.add(status);

JLabel info=new Tekst(null, new Point(0,50));
String tekstIP="Twoje IP to:<br/>";
Enumeration<NetworkInterface> n;
try {
	n = NetworkInterface.getNetworkInterfaces();

for (; n.hasMoreElements();)
{
        NetworkInterface e = n.nextElement();
       // System.out.println("Interface: " + e.getName());
        Enumeration<InetAddress> a = e.getInetAddresses();
        for (; a.hasMoreElements();)
        {
                InetAddress addr = a.nextElement();
               if(!addr.isLoopbackAddress()&&addr.getAddress().length==4)
                	tekstIP+=addr.getHostAddress()+"<br/>";
        }
}} catch (SocketException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
info.setText("<html>"+tekstIP+"</html>");
lewy.add(info);

lewy.add(telefony);
lewy.add(kod);
Przycisk zmienKod=new Przycisk("Zmień kod", new Dimension(100,20), new Point(153,23));
zmienKod.addMouseListener(zmienKodClick);
lewy.add(zmienKod);
/*JLabel link=new JLabel();
link.setText("http://"+java.net.InetAddress.getLocalHost().getHostAddress()+":12345/");
System.out.println();
link.setForeground(Color.BLUE);
lewy.add(link);*/
lewy.add(qr);
add(lewy);
setVisible(true);
Timer timer1 = new Timer();
Odswierz timer1_task = new Odswierz();
timer1_task.okno=this;
timer1.schedule (timer1_task, 100, 100);
}




protected void finalize() throws Throwable {
	Program.wyswietlanie=TypWyswietlania.Konsola;
}
class Odswierz extends TimerTask
{
	int licznik=0;
 public void run()
 {
	 String statusTxt="";
   if(Polaczenie.nasluchiwanie)
   {
	   if(Polaczenie.watki[0].gotowe)
		   statusTxt="Gotowe!";
	   else
		   statusTxt="Inicjowanie...";
   }
if(Aktualizacja.zaktualizowano)
	statusTxt+=" Zaktualizowano do nowej wersji!";
else if(Aktualizacja.trwa)
		statusTxt+=" Trwa aktualizacja do nowej wersji...";
   status.setText(statusTxt);
   boolean potrzebneOdswierzenie=false;
   for(byte i=0;i<Polaczenie.watki.length;i++)
   {
	   if(Polaczenie.watki[i]!=null)
		   if(Polaczenie.watki[i].czyPolaczono())
		   {
			   if(!Polaczenie.watki[i].pokazane)
				 //  if(!Polaczenie.watki[i].pokazane&&Polaczenie.watki[i].infoPrzyPolaczeniu!=null)
			   {
				   Polaczenie.watki[i].pokazane=true;
				   telefony.add(Polaczenie.watki[i].UI=new Urzadzenie(Polaczenie.watki[i],telefony));
				   potrzebneOdswierzenie=true;
			   }
		   }
   }
   for(byte i=0;i<Polaczenie.polaczeniaHttp.length;i++)
   {
	   if(Polaczenie.polaczeniaHttp[i]!=null)
		   if(!Polaczenie.polaczeniaHttp[i].pokazane)
		   {
			   Polaczenie.polaczeniaHttp[i].pokazane=true;
		   telefony.add(Polaczenie.polaczeniaHttp[i].UI=new Okno.Urzadzenie(Polaczenie.polaczeniaHttp[i],telefony));
		   potrzebneOdswierzenie=true;
		   }
   }
   //if(potrzebneOdswierzenie)
/*{telefony.repaint();//(telefony.getGraphics());
	   //telefony.paintImmediately(0, 0, 2000, 2000);
for(int i=0;i<telefony.countComponents();i++)
{
	telefony.getComponent(i).paint(telefony.getComponent(i).getGraphics());
}}*/
   if(potrzebneOdswierzenie||licznik++%100==0)
	   okno.paintAll(okno.getGraphics());
 }
 public Okno okno;
}
class Przycisk extends JButton
{
	public Przycisk(String string, Dimension dimension, Point point) {
		super(string);
		wymiary=dimension;
		lokacja=point;

	}
	    @Override
	    protected void paintComponent(Graphics g) {
	    	
	    setLocation(lokacja);
	    setSize(wymiary);

super.paintComponent(g);
	    }

	public Dimension wymiary;
	public Point lokacja;
	
}
class Tekst extends JLabel
{
	public Tekst(String string, Point point) {
		super(string);
		//wymiary=dimension;
		lokacja=point;

	}
	    @Override
	    protected void paintComponent(Graphics g) {
	    	
	    setLocation(lokacja);
	    //setSize(wymiary);

super.paintComponent(g);
	    }

	public Dimension wymiary;
	public Point lokacja;
	
}
class Urzadzenia extends JPanel
{
	Panel okno;
	public Urzadzenia(Panel lewy)
		{
		super();
			this.okno=lewy;
			   setBackground(Color.CYAN);
			   setLayout(new GridLayout(200,1));
			   Label tytul=new Label("Podłączone urządzenia:");
			   add(tytul);
		}
	@Override
	protected void paintComponent(Graphics g)
	{
		
int liczPolaczenia=countComponents();
		   setSize(okno.getWidth()/2,liczPolaczenia*30+15);
		   setLocation(0,okno.getHeight()-liczPolaczenia*30-15);
		  // setLocation(0,50);
		super.paintComponent(g);
		   
	}
}
class Urzadzenie extends JPanel
{
	Button rozlacz=new Button("Rozłącz");
	Label tekst=new Label();
	
	public PolaczenieInfo zrodlo;
	Urzadzenia ramka;
	public Urzadzenie(PolaczenieInfo z, Urzadzenia telefony)
	{
		zrodlo=z;
		ramka=telefony;
		rozlacz.setLocation(0, 0);
		add(rozlacz);
		tekst.setText(z.toString());
		
		add(tekst);
		setToolTipText(zrodlo.opis());
		
	}
}
}
