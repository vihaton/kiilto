package main;

import java.util.*;

import main.ui.gui.Kayttoliittyma;
import main.ui.gui.Valintanapit;
import logiikka.*;
import tiralabra.AlmaIlmari;
import tiralabra.iterointi.Peluuttaja;
import tiralabra.vuorologiikka.Vuoro;

/**
 * Luokka vastaa pelin pyörittämisestä. Puuttuva linkki logiikka- ja main-moduulien välillä.
 * Vastaa logiikka - gui rajapintana toimimisesta.
 *
 * @author xvixvi
 */
public class Pelivelho {

    private Kayttoliittyma kayttoliittyma;
    private Valintanapit valintanapit;
    private Pelinpystyttaja pp;
    private Peluuttaja koutsi;
    private ArrayList<Pelaaja> pelaajat;

    /**
     * Luo pelivelhon.
     */
    public Pelivelho() {
        pelaajat = new ArrayList<>();
        valintanapit = null;
    }

    /**
     * Pelaa Kiilto -pelin graafisella käyttöliittymällä. Pelaajat on pitänyt
     * määritellä ennen tämän metodin suorittamista!
     */
    public void pelaa() {
        koutsi = new Peluuttaja(pp);

        pp.kierros = pp.poyta.luoTestattavaPelitilanne(5);
        kayttoliittyma = new Kayttoliittyma(this);
        kayttoliittyma.run();

        if (pp.onkoPelkkiaTekoalyja()) {
            while (pp.peliJatkuu) {
                koutsi.peluutaSeuraavatTekoalyt();
            }
            julistaVoittaja(pp.julistaVoittaja());
        }
    }

    public Poyta getPoyta() {
        return pp.poyta;
    }

    public void alustaPeli(boolean[] onkoPelaajaAI) {
        pp = new Pelinpystyttaja(onkoPelaajaAI, true);
    }

    public ArrayList<Pelaaja> getPelaajat() {
        return pp.pelaajat;
    }

    public String getVuorossaOleva() {
        return pp.vuorossaOleva.getNimi();
    }

    public String getKierros() {
        return "" + pp.kierros;
    }

    /**
     * Metodi kutsuu @Pelinpystyttajan metodia seuraavanPelaajanVuoro.
     */
    public void seuraavanPelaajanVuoro() {
        boolean oliIhmisenVuoro = !pp.onkoPelaajaAI[pp.vuorossaOlevanNro];

        if (!pp.seuraavanPelaajanVuoro())
            julistaVoittaja(pp.julistaVoittaja());

        if (oliIhmisenVuoro) {
            koutsi.peluutaSeuraavatTekoalyt();
            if (!pp.peliJatkuu)
                julistaVoittaja(pp.julistaVoittaja());
        }
    }

    /**
     * Tiedetään varmaksi, että joku on voittanut, muttei vielä varmuudella
     * kuka.
     */
    private void julistaVoittaja(Pelaaja voittaja) {
        kayttoliittyma.julistaVoittaja(voittaja.getNimi(), "" + voittaja.getArvovalta(), "" + pp.kierros);
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
        pp.nostaNallekarkkeja(maarat);
    }

    /**
     * Vie valitsinnapistolle vuorossa olevan pelaajan varaukset ja pelipöydän
     * näkyvät omistukset ostamista varten.
     */
    public void vieVarauksetJaNakyvatOmistuksetValitsimelle() {
        ArrayList<String> nimet = pp.poyta.getNakyvienNimet();
        nimet.addAll(pp.vuorossaOleva.getVaraustenNimet());
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
        return pp.osta(ostettavanNimi);
    }

    /**
     * Tekee varauksen vuorossa olevalle pelaajalle. Metodi luottaa syötteen
     * olevan hyvä.
     *
     * @param varattavanNimi varattavan omistuksen nimi.
     * @return suoritettiinko varaus.
     */
    public boolean varaa(String varattavanNimi) {
        return pp.varaa(varattavanNimi);
    }
}
