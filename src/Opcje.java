import javax.swing.*;

/**
 * Created by Mateusz on 01.04.14.
 */
public class Opcje {
    public JFrame frame;
    private JPanel zawartosc;

    public Opcje(boolean wyswietl) {

        frame = new JFrame("Opcje");

        //System.out.println("1");
        frame.setContentPane(zawartosc);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }
}
