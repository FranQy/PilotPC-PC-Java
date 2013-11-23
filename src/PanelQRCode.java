import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.JPanel;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

/**
 * 
 */

/**
 * @author Mateusz
 *Graficzna reprezentacja QRCode
 */
public class PanelQRCode extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ByteMatrix macierz;
Dimension rozmiar;
	public PanelQRCode()
	{
		setBackground(Color.white);
odswierz();
		
		//setSize(rozmiar);
	}
/**
 * generuje QRCode na nowo
 * 
 */
	public void odswierz()
	{
		QRCode kod;
		try {
String adres1="";
String adres2="";
			
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
			               {
			            	   if(adres1.length()==0)
			            	   adres1=addr.getHostAddress();
			            	   else
			            		   adres2+="/"+addr.getHostAddress();
			               
			               }
			        }
			}} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String tresc="http://"+adres1+":12345/"+Program.ustawienia.haslo+adres2;
			kod = Encoder.encode(tresc, ErrorCorrectionLevel.M);
			macierz= kod.getMatrix();
		//rozmiar=new Dimension(macierz.getHeight()*10, macierz.getHeight()*10);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	this.paintImmediately(0,0,2000,2000);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		//setSize(rozmiar);
        int wielkosc;
        if(getSize().height>getSize().width)
        	wielkosc=getSize().width/macierz.getWidth();
        else
        	wielkosc=getSize().height/macierz.getWidth();
        Graphics2D g2d = (Graphics2D) g;

        for(int y=0;y<macierz.getHeight();y++)
            for(int x=0;x<macierz.getWidth();x++)
            {
            	Rectangle2D rectangle = new Rectangle2D.Double(x*wielkosc, y*wielkosc, wielkosc, wielkosc);
            	if(macierz.get(x, y)==1)
                	g2d.setPaint(Color.BLACK);
            	else
            	g2d.setPaint(Color.WHITE);
                g2d.fill(rectangle);
            	g2d.draw(rectangle);
            }
        

    }
	
}