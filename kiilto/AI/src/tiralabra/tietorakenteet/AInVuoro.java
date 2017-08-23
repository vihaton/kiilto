package tiralabra.tietorakenteet;

/**
 * Created by vili on 23.8.2017.
 */
public class AInVuoro extends Vuoro {

    public Strategia strategia;

    public AInVuoro(VuoronToiminto toiminto, Strategia strategia) {
        super(toiminto);
        this.strategia = strategia;
    }
}
