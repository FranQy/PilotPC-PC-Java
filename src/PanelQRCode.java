import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.geom.Rectangle2D;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import org.w3c.dom.css.Rect;

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

	ByteMatrix macierz;

	public PanelQRCode()
	{
		setBackground(Color.white);
	
		QRCode kod;
		try {
			kod = Encoder.encode("http://"+java.net.InetAddress.getLocalHost().getHostAddress()+":12345/"+Program.ustawienia.haslo, ErrorCorrectionLevel.M);
			macierz= kod.getMatrix();
		
		
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for(int y=0;y<macierz.getHeight();y++)
            for(int x=0;x<macierz.getWidth();x++)
            {
            	Rectangle2D rectangle = new Rectangle2D.Double(x*10, y*10, 10, 10);
            	if(macierz.get(x, y)==1)
                	g2d.setPaint(Color.BLACK);
            	else
            	g2d.setPaint(Color.WHITE);
                g2d.fill(rectangle);
            	g2d.draw(rectangle);
            }
        
 
    }
	
}