package tiralabra.iterointi;

import logiikka.Pelinpystyttaja;
import tiralabra.AlmaIlmari;
import tiralabra.IO.Kirjuri;
import tiralabra.tietorakenteet.*;
import tiralabra.tietorakenteet.apurakenteet.Lista;

/**
 * Peluuttaa tekoälyä itseään vastaan erilaisilla strategioilla.
 */
public class Peluuttaja {

    public Strategia[] strategiat;

    private Kirjuri kirjuri;
    private Pelinpystyttaja pp;
    private AlmaIlmari AI;

    public Peluuttaja(Pelinpystyttaja pp) {
        this(pp, new AlmaIlmari());
    }

    protected Peluuttaja(Pelinpystyttaja pelinpystyttaja, AlmaIlmari AI) {
        this.pp = pelinpystyttaja;
        this.AI = AI;
        this.kirjuri = new Kirjuri();
        asetaStrategiat();
        korjaaPelaajienNimet();
    }

    public void peluuta(Strategia[] strategias, int peleja) {
        if (strategias.length < 2 || strategias.length > 4) { //onko älyjä laillinen määrä?
            return;
        }
        this.strategiat = strategias;

        Lista<Peli> pelit = new Lista<>();
        for (int i = 0; i < peleja; i++) {
            pelit.lisaa(peluutaPeli(strategiat));
        }

        kirjuri.kirjaa(pelit);
    }

    /**
     *
     * @param strategias joilla älyt pelaavat (2-4kpl)
     * @return
     */
    public Peli peluutaPeli(Strategia[] strategias) {
        pp.uusiPeli();
        Peli peli = new Peli(pp.pelaajat.size());
        peli.strategiat = strategias;

        while (pp.peliJatkuu) {
            peli.kierrokset.lisaa(peluutaSeuraavatTekoalyt());
        }
        peli.voittaja = pp.julistaVoittaja();
        selvitaVoittostrategia(peli);
        return peli;
    }

    /**
     * selvittää ja tallentaa parametrinä annettuun peliin, mikä strategia voitti
     * @param peli jonka voittaja on jo selvillä
     */
    private void selvitaVoittostrategia(Peli peli) {
        String nimi = peli.voittaja.getNimi();
        for (int i = 0; i < pp.pelaajat.size(); i++) {
            if (nimi.equalsIgnoreCase(pp.pelaajat.get(i).getNimi())) {
                peli.voittoStrategia = strategiat[i < strategiat.length ? i : strategiat.length - 1];
                return;
            }
        }
    }

    /**
     * Olettaa, että kaikki tekoälyt ovat yhteen putkeen, ja että vuorossa oleva on tekoäly.
     *
     * peluuttaa seuraavaksi vuorossa olevat, peräkkäiset tekoälyt, kunnes seuraava pelaaja on ihminen tai kierros loppuu.
     */
    public Kierros peluutaSeuraavatTekoalyt() {
        Kierros kierros = new Kierros();
        int i = 0;
        for (int j = 0; j < pp.kuinkaMontaAIta(); j++) {
            Long start = System.currentTimeMillis();
            Vuoro v = peluutaAInVuoro(strategiat[i]);
            Long ready = System.currentTimeMillis();
            if (i+1 < strategiat.length) { //jos strategioita on vielä peluuttamatta, peluutetaan seuraavaa
                i++;
            }
            kierros.lisaaVuoro(new AInVuoro(v, strategiat[i], ready - start));
            pp.seuraavanPelaajanVuoro();
        }
        return kierros;
    }


    protected Vuoro peluutaAInVuoro(Strategia strategia) {
        Vuoro v = AI.suunnitteleVuoro(pp.vuorossaOleva, pp.poyta, strategia);
        String toiminto = v.toiminto.toString();

        if (toiminto.contains("NOSTA"))
            pp.nostaNallekarkkeja(v.mitaNallekarkkejaNostetaan);
        else if (toiminto.contains("VARAA"))
            pp.varaa(v.varattavanOmistuksenNimi);
        else if (toiminto.contains("OSTA"))
            pp.osta(v.ostettavanOmaisuudenNimi);
        else {
            //System.out.println("\tAI nro " + pp.vuorossaOlevanNro + " ei osannut tehdä mitään!");
        }

        return v;
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
