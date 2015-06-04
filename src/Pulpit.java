import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Wyświetlanie podglądu pulpitu w telefonie
 */
public class Pulpit {

    public static String HTTP(String wyj, int i, OutputStream os) {
        BufferedImage obr = null;
        Image obraz2 = null;
        BufferedImage obrazout = null;
        try {
            //Tylko do debugowania!
            //Thread.sleep(1000);
            String[] wymiary = wyj.split("/");
            String wysylanie = "HTTP/1.1 200 OK\n" +
                    "Server: PilotPC\n" +
                    "Set-Cookie: id=" + i + "; path=/\n" +
                    "Content-Type: image/" + wymiary[9] + "; charset=UTF-8\n" +
                    "\n";
            os.write(wysylanie.getBytes());
            obr = Program.robot.createScreenCapture(new Rectangle(Integer.parseInt(wymiary[3]), Integer.parseInt(wymiary[4]), Integer.parseInt(wymiary[5]), Integer.parseInt(wymiary[6])));
            obraz2 = obr.getScaledInstance(Integer.parseInt(wymiary[7]), Integer.parseInt(wymiary[8]), BufferedImage.SCALE_SMOOTH);
            obrazout = new BufferedImage(Integer.parseInt(wymiary[7]), Integer.parseInt(wymiary[8]), obr.TYPE_3BYTE_BGR);
            obrazout.getGraphics().drawImage(obraz2, 0, 0, null);  //pseudo rzutowanie Image na BufferedImge


            ImageIO.write(obrazout, wymiary[9], os);

            return wysylanie;
        } catch (Throwable e) {

            String wysylanie = "HTTP/1.1 500 Internal Server Error\n" +
                    "\n";
            try {
                os.write(wysylanie.getBytes());
            }
            catch(java.net.SocketException e1){

            }
            catch (IOException e1) {

                Debugowanie.Błąd(e1);
            }
            Debugowanie.Błąd(e);
            try {
                os.flush();
                os.close();

            } catch (IOException e1) {
                Debugowanie.Błąd(e1);
            }

        } finally {
            if (obr != null)
                obr.flush();
            if (obraz2 != null)
                obraz2.flush();
            if (obrazout != null)
                obrazout.flush();

            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                Debugowanie.Błąd(e);
            }
        }
        return "";
    }
}
