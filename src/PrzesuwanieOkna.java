import javax.swing.*;
import java.awt.*;

/**
 * Wątek do przeciągania okna programu
 */
class PrzesuwanieOkna extends Thread {

    boolean przesuwanie = false;
    JFrame okno;
    public boolean działa = true;
    int przesuwanieX, przesuwanieY;

    public PrzesuwanieOkna(JFrame okno) {
        Point b = MouseInfo.getPointerInfo().getLocation();
        przesuwanieX = (int) b.getX();
        przesuwanieY = (int) b.getY();
        this.okno = okno;
        start();
    }

    public void run() {
        try {
            while (działa) {

                Point b = MouseInfo.getPointerInfo().getLocation();
                int przesuwanieXS = (int) b.getX();
                int przesuwanieYS = (int) b.getY();
                okno.setLocation(okno.getLocation().x + przesuwanieXS - przesuwanieX, okno.getLocation().y + przesuwanieYS - przesuwanieY);
                przesuwanieX = przesuwanieXS;
                przesuwanieY = przesuwanieYS;
                currentThread().sleep(5);
            }
        } catch (Throwable e) {
        }
    }
}