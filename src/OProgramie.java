import javax.swing.*;

/**
 * Zawiera okienko wyświetlające się po kliknięciu przycisku "Informacje"
 *
 * @author Mateusz
 */
public class OProgramie extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OProgramie() {
        setSize(300, 300);
        setTitle(Jezyk.napisy[Jezyk.n.OProgramiePilotPc.ordinal()]);
        JLabel tresc = new JLabel("<html><h1>PilotPC-PC-Java</h1><p>" + Jezyk.napisy[Jezyk.n.Wersja.ordinal()] + " " + Program.wersja + "</p><p>" + Jezyk.napisy[Jezyk.n.Programisci.ordinal()] + "<ul><li>FranQy</li><li>Matrix0123456789</li></ul></p></html>");
        add(tresc);
        setVisible(true);
    }
}
