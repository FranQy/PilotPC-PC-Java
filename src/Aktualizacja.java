import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.TimerTask;

/**
 * Odpowiada za autoaktualizację programu
 * @author Mateusz
 */
public class Aktualizacja
    extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
        InputStream is = null;
        String s;
        String content = new String();

        try {
            URL u = new URL("http://pilotpc.za.pl/version.ini");
            is = u.openStream();
            DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
            while ((s = dis.readLine()) != null) {
                content += s+'\n';
                
            }
                aktualizuj(content);

        } catch (IOException ioe) {

            System.out.println("Oops- an IOException happened.");
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                // just going to ignore this one
            }
        }
	}
	static void aktualizuj(String content)
	{
		String[] linie=content.split("\n");
		for(int i=0;i<linie.length;i++)
			if(linie[i].split("=")[0].compareTo("wersja")==0)
			{
				if(linie[i].split("=")[1].compareTo(Program.wersja)!=0)//czy jest inna wersja
				{
					for(int i2=0;i2<linie.length;i2++)
					{
						if(linie[i2].split("=")[0].compareTo("plik")==0)
						{
							(new File(linie[i2].split("=")[1])).renameTo(new File(linie[i2].split("=")[1]+".old"));
							InputStream is = null;
							try
							{URL u = new URL("http://pilotpc.za.pl/"+linie[i2].split("=")[1]);
				            is = u.openStream();
				            FileOutputStream strumien=new FileOutputStream(linie[i2].split("=")[1]);
							//while(is.available()>0)
				           // for(int i3=0;i3<1000000;i3++)
				            while(true)
				            {
				            	int bajt=is.read();
				            	if(bajt==-1)
				            		break;
								strumien.write(bajt);
				            }
							strumien.close();
							}catch (IOException ioe) {

					            System.out.println("Oops- an IOException happened.");
					        }
							finally{try {
				                is.close();
				            } catch (IOException ioe) {
				                // just going to ignore this one
				            }}
						}
					}
				}
				break;
			}
	}

}