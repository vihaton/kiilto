package tiralabra.tietorakenteet;

import tiralabra.tietorakenteet.apurakenteet.Lista;

/**
 * Created by vili on 23.8.2017.
 */
public class Kierros {

    public Lista<AInVuoro> AInVuorot;

    public Kierros() {
        AInVuorot = new Lista<>();
    }

    public void lisaaVuoro(AInVuoro vuoro) {
        AInVuorot.lisaa(vuoro);
    }
}
