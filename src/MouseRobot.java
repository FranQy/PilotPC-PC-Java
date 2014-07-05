import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Date;

/**
 * Obsługa myszy
 */
public class MouseRobot {


    static double pozostalex = 0;//liczby po przecinku pozostałe po wyliczeniu ostetecznego ruchu myszy
    static double pozostaley = 0;
    static boolean wcisniete = false;
    static double gladkieX = 0;
    static double gladkieY = 0;
    public static double sredniCzas = 0;
    static long ostatniCzas = 0;
    static short gladkiePos = 0;

    static {
        if (Program.ustawienia.plynnaMysz) //rozpoczyna wątek odpowiedzialny za wygładzony ruch myszy
            (new WatekMouseRobot()).start();
    }

    /**
     * Emuluje prawy przycisk myszy
     */
    static public void PPM() {
        Program.robot.mousePress(InputEvent.BUTTON3_MASK);
        Program.robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }

    /**
     * emuluje lewy przycisk myszy
     */
    static public void LPM() {
        Program.robot.mousePress(InputEvent.BUTTON1_MASK);
        Program.robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    /**
     * przesuwa mysz z zachowaniem wszystkich "bajerów"
     *
     * @param mx przesunięcie w poziomie (dodatnia w prawo, ujemna w lewo)
     * @param my przesunięcie w pionie (dodatnia w dół, ujemna w górę)
     */
    static public void move(int mx, int my) {
        double odleglosc = Math.sqrt(mx * mx + my * my);//liczy odległość

        if (Program.ustawienia.plynnaMysz) {
            gladkieX += mx * odleglosc / 15;//mnoży współrzędne i odległość, dzięki temu gdy szybko poruszymy palcem to kursor przesunie się na drógi koniec ekranu, a jeżeli wolno, to mamy większą dokładność
            gladkieY += my * odleglosc / 15;
            long cz = (new Date()).getTime();
            if (ostatniCzas > 0)
                sredniCzas = sredniCzas * 0.9 + (cz - ostatniCzas) * 0.1;
            if (sredniCzas > 100)
                sredniCzas = 100;
            ostatniCzas = cz;
        } else {
            double mx2 = mx * odleglosc / 15 + pozostalex;//mnoży współrzędne i odległość, dzięki temu gdy szybko poruszymy palcem to kursor przesunie się na drógi koniec ekranu, a jeżeli wolno, to mamy większą dokładność
            double my2 = my * odleglosc / 15 + pozostaley;
            pozostalex = mx2 - Math.floor(mx2);//zapisuje liczby pozostałe po przecinku, żeby jeśli kursor przesuwa się powoli to nie stał w miejscu
            pozostaley = my2 - Math.floor(my2);
            Point b = MouseInfo.getPointerInfo().getLocation();
            int x = (int) b.getX();
            int y = (int) b.getY();
            Program.robot.mouseMove(x + (int) Math.floor(mx2), y + (int) Math.floor(my2));
        }
    }

    static public void moveTo(int mx, int my) {

        Program.robot.mouseMove(mx, my);
    }

    static public void move(boolean LONG, int mx, int my) {
        if (!wcisniete) {
            Program.robot.mousePress(InputEvent.BUTTON1_MASK);
            wcisniete = true;
        }
        move(mx, my);

    }

    static public void up() {
        Program.robot.mouseRelease(InputEvent.BUTTON1_MASK);
        wcisniete = false;
    }

    static public void scroll(int y) {
        Program.robot.mouseWheel(y);
    }
}

/**
 * Osobny wątek dla wygładzania ruchu myszy
 */
class WatekMouseRobot extends Thread {
    static long ostatniCzas = 0;

    public void run() {
        while (Program.ustawienia.plynnaMysz) {
            try {

                Thread.sleep(6); //wykonuje

                long cz = (new Date()).getTime();
                double dziel = (cz - ostatniCzas) / MouseRobot.sredniCzas;
                if (dziel > 1)
                    dziel = 1;
                ostatniCzas = cz;
                Point b = MouseInfo.getPointerInfo().getLocation();
                int x = (int) b.getX();
                int y = (int) b.getY();
                if (Math.floor(MouseRobot.gladkieX * dziel) != 0 || Math.floor(MouseRobot.gladkieY * dziel) != 0)
                    Program.robot.mouseMove(x + (int) Math.floor(MouseRobot.gladkieX * dziel), y + (int) Math.floor(MouseRobot.gladkieY * dziel));
                MouseRobot.gladkieX -= Math.floor(MouseRobot.gladkieX * dziel);
                MouseRobot.gladkieY -= Math.floor(MouseRobot.gladkieY * dziel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}