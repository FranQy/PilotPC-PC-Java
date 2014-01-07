import com.example.socketclient.TCP_Data;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class Pilot {



    Pilot() {
    }

    static public void click(TCP_Data data) {
        switch (data.button) {
            case OFF: {
                // TODO zrobic dla linuksa i wykrywanie sytemu
                try {
                    Runtime.getRuntime().exec("shutdown -s -t 0");
                    System.exit(0);
                } catch (IOException e) {
                    // TODO Komunikat o bledzie
                }
                break;
            }
            case BACK: {
                Program.robot.keyPress(KeyEvent.VK_BACK_SPACE);
                Program.robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                break;
            }
            case DOWN: {
                Program.robot.keyPress(KeyEvent.VK_DOWN);
                Program.robot.keyRelease(KeyEvent.VK_DOWN);
                break;
            }
            case EXIT: {
                Program.robot.keyPress(KeyEvent.VK_ESCAPE);
                Program.robot.keyRelease(KeyEvent.VK_ESCAPE);
                break;
            }
            case LEFT: {
                Program.robot.keyPress(KeyEvent.VK_LEFT);
                Program.robot.keyRelease(KeyEvent.VK_LEFT);
                break;
            }
            case MULTIMEDIA: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }

                break;
            }
            case MUSIC: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }
                break;
            }
            case VOLDOWN: {
                try {
                    Biblioteka.click(data.button.ordinal());//Korzysta z tego samego enuma co TCP_DATA, bo po co robic dwa osobne
                } catch (Throwable e) {
                }
                break;
            }
            case VOLUP: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }
                break;
            }
            case MUTE: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }
                break;
            }
            case NEXT: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
            }
            case PERV: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }
                break;
            }
            case PLAYPAUSE: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }
                break;
            }
            case RETTURN: {
                Program.robot.keyPress(KeyEvent.VK_ENTER);
                Program.robot.keyRelease(KeyEvent.VK_ENTER);
                break;
            }
            case RIGHT: {
                Program.robot.keyPress(KeyEvent.VK_RIGHT);
                Program.robot.keyRelease(KeyEvent.VK_RIGHT);
                break;
            }
            case STOP: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }
                break;
            }
            case UP: {
                Program.robot.keyPress(KeyEvent.VK_UP);
                Program.robot.keyRelease(KeyEvent.VK_UP);
                break;
            }
            case REWIND: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }
                break;
            }
            case FORWARD: {
                try {
                    Biblioteka.click(data.button.ordinal());
                } catch (Throwable e) {
                }
                break;
            }
        }
        //data.clean();
    }

}
