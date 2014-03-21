import java.util.Date;
import java.util.TimerTask;

public class OdśmiecanieHttpPołączenia
        extends TimerTask {

    @Override
    public void run() {
        long terazCzas = (new Date()).getTime();
        for (byte i = 0; i < 100; i++)//Otwiera max 100 połączeń, zapisuje je w tablicy
        {

            if (Polaczenie.polaczeniaHttp[i] != null && !Polaczenie.polaczeniaHttp[i].pokazane && terazCzas - Polaczenie.polaczeniaHttp[i].czas.getTime() > 5000) {
                Polaczenie.polaczeniaHttp[i] = null;
            }
        }
    }
}