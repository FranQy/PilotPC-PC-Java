import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class MouseRobot {
	Robot robot;
	public MouseRobot()
	{
		try {
			 robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void PPM()
	{
		robot.mousePress(InputEvent.BUTTON3_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	public void LPM()
	{
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void move(int mx, int my)
	{
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		robot.mouseMove(x+mx, y+my);
	}
	public void move(boolean LONG, int mx, int my)
	{
		robot.mousePress(InputEvent.BUTTON1_MASK);
		 move(mx,  my);
			
	}
	
	public void up()
	{
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public void scroll(int y)
	{
		robot.mouseWheel(y);
	}

}
