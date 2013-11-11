import java.util.Random;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
/**
 * 
 * Zawiera wszystkie ustawienia programu, które są zapisywane w pliku
 * 
 * @author Mateusz
 *
 */
public class Ustawienia implements Serializable {
	/**
	 * Znajduje folder z ustawieniami programu
	 * @return
	 * adres folderu, w którym można zapisać ustawienia
	 */
	static String getFolder()
	{
	    String OS = System.getProperty("os.name").toUpperCase();
	    if (OS.contains("WIN"))
	        return System.getenv("APPDATA")+"\\PilotPC-PC-Java";
	    else if (OS.contains("MAC"))
	        return System.getProperty("user.home") + "/Library/Application/PilotPC-PC-Java "
	                + "Support";
	    else if (OS.contains("NUX"))
	        return System.getProperty("user.home")+"/.PilotPC-PC-Java";
	    return System.getProperty("user.dir")+"/.PilotPC-PC-Java";
	}
	static String generujHaslo()
	{
		String ret="";
		for(byte i=0;i<6;i++)
			ret+=(new Random()).nextInt(9);
		return ret;
	}
	/**
	 * Zapisuje ustawienia do pliku
	 */
	public void eksportuj()
	{
		String folder=getFolder();
		File folderObjekt=new File(folder);
		if(!folderObjekt.exists())
			folderObjekt.mkdirs();
		String nazwaPliku=folder+"/ustawienia.txt";
		
			try {
				ObjectOutputStream strumien=new ObjectOutputStream(new FileOutputStream(nazwaPliku));
				strumien.writeObject(this);
				strumien.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	private Ustawienia(){}
	public static Ustawienia importuj()
	{
		try {
			ObjectInputStream strumien=new ObjectInputStream(new FileInputStream(getFolder()+"/ustawienia.txt"));
			Ustawienia ret= (Ustawienia) strumien.readObject();
			strumien.close();
			return ret;
		} catch (FileNotFoundException e) {
			Ustawienia ret=new Ustawienia();
			ret.eksportuj();
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();Ustawienia ret=new Ustawienia();
			ret.eksportuj();
			return ret;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();Ustawienia ret=new Ustawienia();
			ret.eksportuj();
			return ret;
		}
	}
	/**
	 * Hasło urzywane do łączenia się
	 */
	public  String haslo=generujHaslo();
}
