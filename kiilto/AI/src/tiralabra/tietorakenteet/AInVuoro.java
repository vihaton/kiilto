package tiralabra.tietorakenteet;

/**
 * Created by vili on 23.8.2017.
 */
public class AInVuoro {

    public Vuoro vuoro;
    public Strategia strategia;
    public long millis;

    public AInVuoro(Vuoro vuoro, Strategia strategia, long millis) {
        this.vuoro = vuoro;
        this.strategia = strategia;
        this.millis = millis;
    }
}
