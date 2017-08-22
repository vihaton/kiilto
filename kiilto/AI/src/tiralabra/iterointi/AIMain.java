package tiralabra.iterointi;

import logiikka.Pelinpystyttaja;
import tiralabra.vuorologiikka.Strategia;

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
