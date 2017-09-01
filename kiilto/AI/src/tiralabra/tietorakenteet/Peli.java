package tiralabra.tietorakenteet;

import logiikka.Pelaaja;
import tiralabra.tietorakenteet.apurakenteet.Lista;

/**
 * Created by vili on 23.8.2017.
 */
public class Peli {

    public Lista<Kierros> kierrokset;
    public Pelaaja voittaja;
    public Strategia voittoStrategia;
    public Strategia[] strategiat;

    public Peli() {
        kierrokset = new Lista<>();
    }
}
