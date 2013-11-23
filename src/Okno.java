import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
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

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Okno extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Label status=new Label("Inicjowanie");
	Label kod=new Label("Kod do połączenia: "+Program.ustawienia.haslo);
	JLabel telefony=new JLabel();
	PanelQRCode qr=new PanelQRCode();
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

setLayout(new GridLayout());
Panel lewy=new Panel();
lewy.setLayout(new FlowLayout());
Button przyciskInformacje=new Button("Informacje");
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
lewy.add(przyciskInformacje);

lewy.add(status);

JLabel info=new JLabel();
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
Button zmienKod=new Button("Zmień kod");
zmienKod.addMouseListener(zmienKodClick);
lewy.add(zmienKod);
/*JLabel link=new JLabel();
link.setText("http://"+java.net.InetAddress.getLocalHost().getHostAddress()+":12345/");
System.out.println();
link.setForeground(Color.BLUE);
lewy.add(link);*/

add(lewy);
add(qr);

setVisible(true);
Timer timer1 = new Timer();
Odswierz timer1_task = new Odswierz();
timer1.schedule (timer1_task, 100, 100);
}
protected void finalize() throws Throwable {
	Program.wyswietlanie=TypWyswietlania.Konsola;
}
class Odswierz extends TimerTask
{
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
   String listaUrzadzen="<html>Połączone urządzenia:<ul>";
   for(byte i=0;i<Polaczenie.watki.length;i++)
   {
	   if(Polaczenie.watki[i]!=null)
		   if(Polaczenie.watki[i].czyPolaczono())
		   {   listaUrzadzen+="<li>";
		   if(Polaczenie.watki[i].infoPrzyPolaczeniu!=null)
			   listaUrzadzen+=Polaczenie.watki[i].infoPrzyPolaczeniu.nazwa+" - ";
		   listaUrzadzen+=Polaczenie.watki[i].getIP()+"</li>";
		   
		   }
   }
   telefony.setText(listaUrzadzen+"</ul></html>");
 }
}
}
