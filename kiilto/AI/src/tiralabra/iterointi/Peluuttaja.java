package tiralabra.iterointi;

import logiikka.Pelinpystyttaja;
import tiralabra.AlmaIlmari;
import tiralabra.vuorologiikka.Strategia;
import tiralabra.vuorologiikka.Vuoro;

/**
 * Peluuttaa tekoälyä itseään vastaan erilaisilla strategioilla.
 */
public class Peluuttaja {

    private Pelinpystyttaja pp;
    private AlmaIlmari AI;

    public Peluuttaja(Pelinpystyttaja pp) {
        this.pp = pp;
        this.AI = new AlmaIlmari();
    }

    public void peluuta(Strategia[] strategiat) {
        if (strategiat.length < 2 || strategiat.length > 4) {
            return;
        }

    }

    /**
     * peluuttaa seuraavaksi vuorossa olevat, peräkkäiset tekoälyt, kunnes seuraava pelaaja on ihminen tai kierros loppuu.
     */
    public void peluutaSeuraavatTekoalyt() {
        int round = pp.kierros;
        while (pp.onkoPelaajaAI[pp.vuorossaOlevanNro] && pp.kierros == round && pp.peliJatkuu) {
            Long start = System.currentTimeMillis();
            peluutaAInVuoro();
            Long ready = System.currentTimeMillis();
            System.out.println("AI took " + (ready - start) + "ms\n");

            pp.seuraavanPelaajanVuoro();
        }
    }


    private void peluutaAInVuoro() {
        System.out.println("Peluuttaja kutsuu AI.ta suunnittelemaan vuoron pelaajalla nro " + pp.vuorossaOlevanNro + ", arvovaltaa " + pp.vuorossaOleva.getArvovalta());
        Vuoro v = AI.suunnitteleVuoro(pp.vuorossaOleva, pp.poyta);
        String toiminto = v.toiminto.toString();

        if (toiminto.contains("NOSTA"))
            pp.nostaNallekarkkeja(v.mitaNallekarkkejaNostetaan);
        else if (toiminto.contains("VARAA"))
            pp.varaa(v.varattavanOmistuksenNimi);
        else if (toiminto.contains("OSTA"))
            pp.osta(v.ostettavanOmaisuudenNimi);
        else
            System.out.println("\t\tAI nro " + pp.vuorossaOlevanNro + " ei osannut tehdä mitään!");

        System.out.println("\tAIn nro " + pp.vuorossaOlevanNro + " vuoro on ohi, toiminto: " +toiminto +", arvovaltaa nyt " + pp.vuorossaOleva.getArvovalta() + "\n");
    }
}
