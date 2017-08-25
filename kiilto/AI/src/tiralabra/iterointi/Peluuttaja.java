package tiralabra.iterointi;

import logiikka.Pelinpystyttaja;
import tiralabra.AlmaIlmari;
import tiralabra.tietorakenteet.Strategia;
import tiralabra.tietorakenteet.Vuoro;

/**
 * Peluuttaa tekoälyä itseään vastaan erilaisilla strategioilla.
 */
public class Peluuttaja {

    private Pelinpystyttaja pp;
    private AlmaIlmari AI;
    public Strategia[] strategiat;

    public Peluuttaja(Pelinpystyttaja pp) {
        this(pp, new AlmaIlmari());
    }

    protected Peluuttaja(Pelinpystyttaja pelinpystyttaja, AlmaIlmari AI) {
        this.pp = pelinpystyttaja;
        this.AI = AI;
        asetaStrategiat();
        korjaaPelaajienNimet();
    }

    public void peluuta(Strategia[] strategias) {
        if (strategias.length < 2 || strategias.length > 4) {
            return;
        }
        this.strategiat = strategias;

    }

    /**
     * Olettaa, että kaikki tekoälyt ovat yhteen putkeen, ja että vuorossa oleva on tekoäly.
     *
     * peluuttaa seuraavaksi vuorossa olevat, peräkkäiset tekoälyt, kunnes seuraava pelaaja on ihminen tai kierros loppuu.
     */
    public void peluutaSeuraavatTekoalyt() {
        int round = pp.kierros;
        int i = 0;
        for (int j = 0; j < pp.kuinkaMontaAIta(); j++) {
            Long start = System.currentTimeMillis();
            peluutaAInVuoro(strategiat[i]);
            Long ready = System.currentTimeMillis();
            System.out.println("AI took " + (ready - start) + "ms\n");
            if (i+1 < strategiat.length) {
                i++;
            }

            pp.seuraavanPelaajanVuoro();
        }
    }


    protected void peluutaAInVuoro(Strategia strategia) {
        /*
        System.out.println("Peluuttaja kutsuu AI.ta suunnittelemaan vuoron pelaajalla nro " + pp.vuorossaOlevanNro +
                ", strategia " + strategia + ", arvovaltaa " + pp.vuorossaOleva.getArvovalta());
                */
        Vuoro v = AI.suunnitteleVuoro(pp.vuorossaOleva, pp.poyta, strategia);
        String toiminto = v.toiminto.toString();

        if (toiminto.contains("NOSTA"))
            pp.nostaNallekarkkeja(v.mitaNallekarkkejaNostetaan);
        else if (toiminto.contains("VARAA"))
            pp.varaa(v.varattavanOmistuksenNimi);
        else if (toiminto.contains("OSTA"))
            pp.osta(v.ostettavanOmaisuudenNimi);
        else
            System.out.println("\t\tAI nro " + pp.vuorossaOlevanNro + " ei osannut tehdä mitään!");

        //System.out.println("\tAIn nro " + pp.vuorossaOlevanNro + " vuoro on ohi, toiminto: " +toiminto +", arvovaltaa nyt " + pp.vuorossaOleva.getArvovalta() + "\n");
    }

    private void asetaStrategiat() {
        Strategia[] s = new Strategia[pp.kuinkaMontaAIta()];
        for (int i = 0; i < s.length; i++) {
            s[i] = Strategia.strategiat[i];
        }
        this.strategiat = s;
    }

    private void korjaaPelaajienNimet() {
        int ind = 0;

        for (int i = 0; i < pp.onkoPelaajaAI.length; i++) {
            if (pp.onkoPelaajaAI[i]) {
                strategiat[0].name();
                pp.pelaajat.get(i).setNimi("AI " + strategiat[ind].name());
                ind++;
            }
        }
    }
}
