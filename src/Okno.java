import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.*;

public class Okno extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public Okno()
{
	super("SockedServer");
this.setSize(500,500);

setLayout(new GridLayout());
Panel lewy=new Panel();
lewy.setLayout(new FlowLayout());
Button przyciskInformacje=new Button("Informacje");
przyciskInformacje.setSize(100, 20);
przyciskInformacje.setMaximumSize(new Dimension(100, 20));
lewy.add(przyciskInformacje);
try{
Label info=new Label();

InetAddress[] adresy=java.net.Inet4Address.getAllByName(java.net.InetAddress.getLocalHost().getHostName());
String tekstIP="";
if(adresy.length==1)
	tekstIP="twój IP to: "+adresy[0].getHostAddress();
else
{
tekstIP="twoje IP to: "+adresy[0].getHostAddress();
	for(int i=1;i<adresy.length;i++)
		tekstIP+=", "+adresy[i].getHostAddress();
}
info.setText(tekstIP);
lewy.add(info);
Label link=new Label();
link.setText("http://"+java.net.InetAddress.getLocalHost().getHostAddress()+":12345/");
System.out.println();
link.setForeground(Color.BLUE);
lewy.add(link);
} catch (UnknownHostException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
add(lewy);
add(new PanelQRCode());

show();
}
protected void finalize() throws Throwable {
	Program.wyswietlanie=TypWyswietlania.Konsola;
}
}
