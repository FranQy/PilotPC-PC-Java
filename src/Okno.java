import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.omg.CORBA.Object;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.*;

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
try{
JLabel info=new JLabel();
InetAddress[] adresy=java.net.Inet4Address.getAllByName(java.net.InetAddress.getLocalHost().getHostName());
String tekstIP="";
if(adresy.length==1)
	tekstIP="twój IP to: "+adresy[0].getHostAddress();
else
{
tekstIP="twoje IP to:<br/>"+adresy[0].getHostAddress();
	for(int i=1;i<adresy.length;i++)
		if(adresy[i].getAddress().length==4)
		tekstIP+=",<br/>"+adresy[i].getHostAddress();
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
} catch (UnknownHostException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
add(lewy);
add(qr);

show();
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
   if(Polaczenie.nasluchiwanie)
   {
	   if(Polaczenie.watki[0].gotowe)
		   status.setText("Gotowe!");
	   else
		   status.setText("Inicjowanie...");
   }
   String listaUrzadzen="<html>Połączone urządzenia:<ul>";
   for(byte i=0;i<Polaczenie.watki.length;i++)
   {
	   if(Polaczenie.watki[i]!=null)
		   if(Polaczenie.watki[i].czyPolaczono())
			   listaUrzadzen+="<li>"+Polaczenie.watki[i].getIP()+"</li>";
   }
   telefony.setText(listaUrzadzen+"</ul></html>");
 }
}
}
