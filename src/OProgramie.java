import javax.swing.*;

/**
 * Zawiera okienko wyświetlające się po kliknięciu przycisku "Informacje"
 * @author Mateusz
 *
 */
public class OProgramie extends JFrame {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public OProgramie()
{
	setSize(300,300);
	setTitle("O Programie PilotPC");
	JLabel tresc = new JLabel("<html><h1>PilotPC-PC-Java</h1><p>wersja "+Program.wersja+"</p><p>Programiści:<ul><li>FranQy</li><li>Matrix0123456789</li></ul></p></html>");
	add(tresc);
	setVisible(true);
}
}
