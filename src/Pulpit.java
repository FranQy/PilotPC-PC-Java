import javafx.stage.Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Mateusz on 14.01.14.
 */
public class Pulpit {

    public static String HTTP(String wyj, int i, OutputStream os)
    {
        try {
            String wysylanie = "HTTP/1.1 200 OK\n" +
                "Server: PilotPC\n" +
                "Set-Cookie: id="+i+"; path=/\n" +
                "Content-Type: image/jpeg; charset=UTF-8\n" +
                "\n";
            os.write(wysylanie.getBytes());
            String[] wymiary= wyj.split("/");
        BufferedImage obr=Program.robot.createScreenCapture(new Rectangle(Integer.parseInt(wymiary[3]), Integer.parseInt(wymiary[4]), Integer.parseInt(wymiary[5]), Integer.parseInt(wymiary[6])));
            Image obraz2= obr.getScaledInstance(Integer.parseInt(wymiary[7]),Integer.parseInt(wymiary[8]),BufferedImage.SCALE_DEFAULT) ;
            BufferedImage obrazout = new BufferedImage(Integer.parseInt(wymiary[7]),Integer.parseInt(wymiary[8]), obr.TYPE_3BYTE_BGR);
            obrazout.getGraphics().drawImage(obraz2, 0 , 0, null);  //pseudo rzutowanie Image na BufferedImge 
            ImageIO.write(obrazout, "JPEG", os);

        return wysylanie; } catch (IOException e) {
        e.printStackTrace();
    }
        return "";
    }
}
