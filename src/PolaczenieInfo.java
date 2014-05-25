/**
 * Pojedynćze urządzenie wyświetlone na liście w oknie
 */
public interface PolaczenieInfo {
    public String opis();

    public Okno.Urzadzenie getUI();

    public void rozlacz();
}
