package tiralabra.iterointi;

import logiikka.Pelinpystyttaja;
import tiralabra.tietorakenteet.AInVuoro;
import tiralabra.tietorakenteet.Strategia;
import tiralabra.tietorakenteet.VuoronToiminto;

/**
 * To run AI-module iterations
 */
public class AIMain {

    private static Strategia[] kolmeEnsimmaista = new Strategia[]{
            Strategia.OLETUS,
            Strategia.HAMSTRAA_KARKKEJA,
            Strategia.HAMSTRAA_OMISTUKSIA
    };

    public static void main(String[] args) {
        Peluuttaja koutsi = new Peluuttaja(new Pelinpystyttaja(3));
        koutsi.peluuta(kolmeEnsimmaista);
    }
}
