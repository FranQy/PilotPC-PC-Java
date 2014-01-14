import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;




public class MouseRobot {
	
	


	

	 
	Robot robot;
	static double pozostalex=0;//liczby po przecinku pozostałe po wyliczeniu ostetecznego ruchu myszy
	static double pozostaley=0;
    static boolean wcisniete=false;
	public MouseRobot()
	{

		//System.out.println(System.getProperty("java.library.path"));
		try {
			 robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public void PPM()
	{
		Program.robot.mousePress(InputEvent.BUTTON3_MASK);
		Program.robot.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	static public void LPM()
	{
		Program.robot.mousePress(InputEvent.BUTTON1_MASK);
		Program.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

    static public void move(int mx, int my)
    {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();

        double odleglosc=Math.sqrt(mx*mx+my*my);//liczy odległość
        double mx2=mx*odleglosc/15+pozostalex;//mnoży współrzędne i odległość, dzięki temu gdy szybko poruszymy palcem to kyrsor przesunie się na drógi koniec ekranu, a jeżeli wolno, to mamy większą dokładność
        double my2=my*odleglosc/15+pozostaley;
        pozostalex=mx2-Math.floor(mx2);//zapisuje liczby pozostałe po przecinku, żeby jeśli kursor przesuwa się powoli to nie stał w miejscu
        pozostaley=my2-Math.floor(my2);
        Program.robot.mouseMove(x+(int)Math.floor(mx2), y+(int)Math.floor(my2));
    }
    static public void moveTo(int mx, int my)
    {

        Program.robot.mouseMove(mx,my);
    }
	static public void move(boolean LONG, int mx, int my)
	{
		if(!wcisniete)
        {Program.robot.mousePress(InputEvent.BUTTON1_MASK);
            wcisniete=true;
        }
		 move(mx,  my);
			
	}
	
	static public void up()
	{
		Program.robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	static public void scroll(int y)
	{
		Program.robot.mouseWheel(y);
	}

}
