import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Mateusz on 14.01.14.
 */
public class Pulpit {

    public static String HTTP(String wyj, int i, OutputStream os) {
        try {
            String[] wymiary = wyj.split("/");
            String wysylanie = "HTTP/1.1 200 OK\n" +
                    "Server: PilotPC\n" +
                    "Set-Cookie: id=" + i + "; path=/\n" +
                    "Content-Type: image/" + wymiary[9] + "; charset=UTF-8\n" +
                    "\n";
            os.write(wysylanie.getBytes());
            BufferedImage obr = Program.robot.createScreenCapture(new Rectangle(Integer.parseInt(wymiary[3]), Integer.parseInt(wymiary[4]), Integer.parseInt(wymiary[5]), Integer.parseInt(wymiary[6])));
            Image obraz2 = obr.getScaledInstance(Integer.parseInt(wymiary[7]), Integer.parseInt(wymiary[8]), BufferedImage.SCALE_SMOOTH);
            BufferedImage obrazout = new BufferedImage(Integer.parseInt(wymiary[7]), Integer.parseInt(wymiary[8]), obr.TYPE_3BYTE_BGR);
            obrazout.getGraphics().drawImage(obraz2, 0, 0, null);  //pseudo rzutowanie Image na BufferedImge

            /*ImageWriter writer = ImageIO.getImageWritersByFormatName(wymiary[9]).next();
            writer.setOutput(os);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // Needed see javadoc
            param.setCompressionQuality(0.1F);
            writer.write(null, new IIOImage(obrazout, null, null), param);  */
            ImageIO.write(obrazout, wymiary[9], os);

            return wysylanie;
        } catch (Throwable e) {

            String wysylanie = "HTTP/1.1 500 Internal Server Error\n" +
                    "\n";
            try {
                os.write(wysylanie.getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return "";
    }
}
