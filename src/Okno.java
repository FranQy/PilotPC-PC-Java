import java.awt.Button;
import java.awt.Color;
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

setLayout(new GridLayout(1, 2));
Panel lewy=new Panel();
lewy.setLayout(new GridLayout(3,1));
Button przyciskInformacje=new Button();
lewy.add(przyciskInformacje);
try{
Label info=new Label();
info.setText("twój IP to:"+java.net.InetAddress.getLocalHost().getHostAddress());
lewy.add(info);
Label link=new Label();
link.setText("http://"+java.net.InetAddress.getLocalHost().getHostAddress()+":12345/");
link.setForeground(Color.BLUE);
lewy.add(link);
} catch (UnknownHostException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
add(lewy);
add(new PanelQRCode());
}
}
