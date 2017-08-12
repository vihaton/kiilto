package main;

import java.util.*;

import main.ui.gui.Kayttoliittyma;
import main.ui.gui.Valintanapit;
import logiikka.*;
import logiikka.valineluokat.Kasakokoelma;
import tiralabra.AlmaIlmari;
import tiralabra.vuorologiikka.Vuoro;

/**
 * Luokka vastaa pelin pyörittämisestä. Puuttuva linkki logiikka- ja main-moduulien välillä. Vastaa vuorojen
 * organisoimisesta sekä logiikka - gui rajapintana toimimisesta.
 *
 * @author xvixvi
 */
public class Pelivelho {

    private Kayttoliittyma kayttoliittyma;
    private Valintanapit valintanapit;
    private final int voittoValta;
    private int kierros;
    private Pelinpystyttaja pp;
    private ArrayList<Pelaaja> pelaajat;
    private Pelaaja vuorossaOleva;              //nimetään ensimmäisen kerran pelaajien luomisen yhteydessä.
    private int vuorossaOlevanNro;
    private AlmaIlmari AI;

    /**
     * Luo pelivelhon.
     */
    public Pelivelho() {
        pelaajat = new ArrayList<>();
        voittoValta = 15;
        kierros = 1;
        vuorossaOleva = null;
        valintanapit = null;
    }

    /**
     * Pelaa Kiilto -pelin graafisella käyttöliittymällä. Pelaajat on pitänyt
     * määritellä ennen tämän metodin suorittamista!
     */
    public void pelaa() {
        AI = new AlmaIlmari();

        if (pp.onkoPelaajaAI[vuorossaOlevanNro]) {
            peluutaAInVuoro();
        }

//        kierros = poyta.luoTestattavaPelitilanne(5);
        kayttoliittyma = new Kayttoliittyma(this);
        kayttoliittyma.run();
    }

    public Poyta getPoyta() {
        return pp.poyta;
    }

    public void alustaPeli(boolean[] onkoPelaajaAI) {
        pp = new Pelinpystyttaja(onkoPelaajaAI);
        this.pelaajat = pp.pelaajat;
        vuorossaOlevanNro = 0;
        vuorossaOleva = pelaajat.get(vuorossaOlevanNro);
    }


    public ArrayList<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    public String getVuorossaOleva() {
        return vuorossaOleva.getNimi();
    }

    public String getKierros() {
        return "" + kierros;
    }

    private boolean onkoVoittaja() {
        for (Pelaaja p : pelaajat) {
            if (p.voittaja(voittoValta)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Onko vuorossa oleva pelaaja kierroksen viimeinen.
     *
     * @return -:-
     */
    public boolean kierroksenViimeinen() {
        return pelaajat.get(pelaajat.size() - 1) == vuorossaOleva;
    }

    /**
     * Metodi siirtää vuoron seuraavalle pelaajalle. Jos joku merkkihenkilö on
     * vaikuttunut pelaajan omaisuudesta, siirretään merkkihenkilö vierailemaan
     * pelaajalle. Jos joku pelaajista on voittaja, ja kierros on lopussa, peli
     * päättyy. Jos vuorossa oleva pelaaja on kierroksen viimeinen, kierrosluku
     * kasvaa ja vuoro siirtyy ensimmäiselle pelaajalle.
     */
    public void seuraavaPelaajanVuoro() {
        pp.poyta.vaikuttikoPelaajaMerkkihenkiloon(vuorossaOleva);

        if (kierroksenViimeinen() && onkoVoittaja()) {
            julistaVoittaja();
            return;
        }

        if (kierroksenViimeinen()) {
            kierros++;
            vuorossaOleva = pelaajat.get(0);
            vuorossaOlevanNro = 0;
        } else {
            vuorossaOlevanNro++;
            vuorossaOleva = pelaajat.get(vuorossaOlevanNro);
        }

        if (pp.onkoPelaajaAI[vuorossaOlevanNro]) {
            peluutaAInVuoro();
            kayttoliittyma.repaint();
            seuraavaPelaajanVuoro();
        }
    }

    private void peluutaAInVuoro() {
        System.out.println("Pelivelho kutsuu AI.ta pelaamaan vuoron pelaajalla nro " + vuorossaOlevanNro);
        Vuoro v = AI.suunnitteleVuoro(vuorossaOleva, pp.poyta);

        switch (v.toiminto) {
            case NOSTA: nostaNallekarkkeja(v.mitaNallekarkkejaNostetaan);
                break;
            case VARAA: varaa(v.varattavanOmistuksenNimi);
                break;
            case OSTA:  osta(v.ostettavanOmaisuudenNimi);
        }
    }

    /**
     * Tiedetään varmaksi, että joku on voittanut, muttei vielä varmuudella
     * kuka.
     */
    private void julistaVoittaja() {
        Pelaaja voittaja = pelaajat.get(0);
        for (int i = 1; i < pelaajat.size(); i++) {
            Pelaaja haastaja = pelaajat.get(i);
            if (haastaja.getArvovalta() > voittaja.getArvovalta()) {
                voittaja = haastaja;
            }
        }
        for (Pelaaja pelaaja : pelaajat) {
            if (pelaaja != voittaja && pelaaja.getOmaisuudenKoko() < voittaja.getOmaisuudenKoko() && pelaaja.getArvovalta() == voittaja.getArvovalta()) {
                voittaja = pelaaja;
            }
        }

        kayttoliittyma.julistaVoittaja(voittaja.getNimi(), "" + voittaja.getArvovalta(), "" + kierros);
    }

    /**
     * Jos nimi ei kerro mistä on kyse, et puhu suomea.
     *
     * @param vari 1=val, 2=sin, 3=vih, 4=pun ja 5=mus
     * @return oliko kasassa vähintään neljä karkkia.
     */
    public boolean vahintaanNeljaKarkkia(int vari) {
        return pp.poyta.getMarkkinat().getKasanKoko(vari) > 3;
    }

    /**
     * Nostaa vuorossa olevalle pelaajalle nallekarkkeja annettujen määrien
     * mukaan. Metodi luottaa, että syöte on tarkastettu hyväksi.
     *
     * @param maarat nostettavien nallekarkkien määrät.
     */
    public void nostaNallekarkkeja(int[] maarat) {
        Kasakokoelma karkit = vuorossaOleva.getKarkit();
        for (int i = 1; i < maarat.length; i++) {
            int m = maarat[i];
            if (m > 0) {
                pp.poyta.getMarkkinat().siirraToiseenKasaan(karkit, i, m);
            }
        }
    }

    /**
     * Vie valitsinnapistolle vuorossa olevan pelaajan varaukset ja pelipöydän
     * näkyvät omistukset ostamista varten.
     */
    public void vieVarauksetJaNakyvatOmistuksetValitsimelle() {
        ArrayList<String> nimet = pp.poyta.getNakyvienNimet();
        nimet.addAll(vuorossaOleva.getVaraustenNimet());
        valintanapit.setNakyvienNimet(nimet, true);
    }

    /**
     * Vie valitsinnapistolle varattavissa olevat omistukset.
     */
    public void vieNakyvatOmistuksetValitsimelle() {
        valintanapit.setNakyvienNimet(pp.poyta.getNakyvienNimet(), false);
    }

    /**
     * Asettaa pelivelhon Valintanapeiksi parametrina annetut valintanapit.
     *
     * @param vn Valintanapit.
     */
    public void setValintanapit(Valintanapit vn) {
        valintanapit = vn;
    }

    /**
     * Suorittaa oston, kohteesta riippuen pöydästä tai vuorossa olevan pelaajan
     * varauksista. Metodi luottaa saavansa hyvän syötteen.
     *
     * @param ostettavanNimi ostettavan omistuksen nimi.
     * @return suoritettiinko osto, eli käytännössä: jos pelaajalla ei ole varaa
     * niin false, muuten true.
     */
    public boolean osta(String ostettavanNimi) {
        if (pp.poyta.getNakyvienNimet().contains(ostettavanNimi)) {
            return pp.poyta.suoritaOsto(vuorossaOleva, Integer.parseInt(ostettavanNimi));
        } else {
            return pp.poyta.suoritaOstoVarauksista(vuorossaOleva, Integer.parseInt(ostettavanNimi));
        }
    }

    /**
     * Tekee varauksen vuorossa olevalle pelaajalle. Metodi luottaa syötteen
     * olevan hyvä.
     *
     * @param varattavanNimi varattavan omistuksen nimi.
     * @return suoritettiinko varaus.
     */
    public boolean varaa(String varattavanNimi) {
        return pp.poyta.teeVaraus(vuorossaOleva, Integer.parseInt(varattavanNimi));
    }
}
