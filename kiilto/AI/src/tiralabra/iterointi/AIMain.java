package tiralabra.iterointi;

import logiikka.Pelinpystyttaja;
import tiralabra.tietorakenteet.Strategia;

/**
 * To run AI-module iterations
 */
public class AIMain {

    private static Strategia[] kolmenKopla = new Strategia[]{
            Strategia.OLETUS,
            Strategia.TEHOKAS,
            Strategia.HAMSTRAA_OMISTUKSIA
    };

    public static void main(String[] args) {
        Strategia[] peluutettavat = kolmenKopla;

        Peluuttaja koutsi = new Peluuttaja(new Pelinpystyttaja(peluutettavat.length), true);
        koutsi.peluuta(peluutettavat, 1000);
    }
}
