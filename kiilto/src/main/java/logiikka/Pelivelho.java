package logiikka;

import ui.gui.Kayttoliittyma;
import java.util.*;
import logiikka.valineluokat.*;
import ui.gui.Valintanapit;

/**
 * Luokka vastaa pelin pyörittämisestä. Esimerkiksi pelin alustaminen, vuorojen
 * organisoiminen sekä UI.n ja logiikan rajapintana toimiminen ovat sen
 * tehtäviä.
 * 
 * @author xvixvi
 */
public class Pelivelho {

    private Kayttoliittyma kayttoliittyma;
    private final ArrayList<Pelaaja> pelaajat;
    private Valintanapit valintanapit;
    private Poyta poyta;
    private final int voittoValta;
    private int kierros;
    private Pelaaja vuorossaOleva;

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
     * Alustaa ja pelaa kiilto-pelin graafisella käyttöliittymällä.
     */
    public void pelaa() {
        poyta = new Poyta(pelaajat);

        vuorossaOleva = pelaajat.get(0);
        
        kierros = poyta.luoTestattavaPelitilanne(5);
        
        kayttoliittyma = new Kayttoliittyma(this);
        kayttoliittyma.run();
    }
    
    public Poyta getPoyta() {
        return poyta;
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
     * Luo annettujen nimien mukaiset pelaajat.
     *
     * @param nimet pelaajien nimet.
     */
    public void luoPelaajat(ArrayList<String> nimet) {
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
    }

    /**
     * Luo pelaaja-oliot oletusnimillä.
     *
     * @param pelaajia kuinka monta pelaajaa luodaan.
     */
    public void luoPelaajat(int pelaajia) {
        for (int i = 0; i < pelaajia; i++) {
            Pelaaja p = new Pelaaja("Pelaaja" + (i + 1));
            pelaajat.add(p);
        }
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
        poyta.vaikuttikoPelaajaMerkkihenkilon(vuorossaOleva);

        if (kierroksenViimeinen() && onkoVoittaja()) {
            julistaVoittaja();
            return;
        }

        if (kierroksenViimeinen()) {
            kierros++;
            vuorossaOleva = pelaajat.get(0);
            return;
        }

        Pelaaja seuraava = pelaajat.get(pelaajat.size() - 1);
        for (int i = pelaajat.size() - 2; i > -1; i--) {
            Pelaaja p = pelaajat.get(i);

            if (p == vuorossaOleva) {
                vuorossaOleva = seuraava;
                break;
            }

            seuraava = p;
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
        return poyta.getMarkkinat().getKasanKoko(vari) > 3;
    }

    /**
     * Nostaa vuorossa olevalle pelaajalle nallekarkkeja annettujen määrien
     * mukaan. Metodi luottaa, että syöte on tarkastettu hyväksi.
     *
     * @param maarat nostettavien nallekarkkien määrät.
     */
    public void nostaNallekarkkeja(int[] maarat) {
        Kasakokoelma karkit = vuorossaOleva.getKarkit();
        for (int i = 0; i < maarat.length; i++) {
            int m = maarat[i];
            if (m > 0) {
                poyta.getMarkkinat().siirraToiseenKasaan(karkit, i + 1, m);
            }
        }
    }

    /**
     * Vie valitsinnapistolle vuorossa olevan pelaajan varaukset ja pelipöydän
     * näkyvät omistukset ostamista varten.
     */
    public void vieVarauksetJaNakyvatOmistuksetValitsimelle() {
        ArrayList<String> nimet = poyta.getNakyvienNimet();
        nimet.addAll(vuorossaOleva.getVaraustenNimet());
        valintanapit.setNakyvienNimet(nimet, true);
    }

    /**
     * Vie valitsinnapistolle varattavissa olevat omistukset.
     */
    public void vieNakyvatOmistuksetValitsimelle() {
        valintanapit.setNakyvienNimet(poyta.getNakyvienNimet(), false);
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
        if (poyta.getNakyvienNimet().contains(ostettavanNimi)) {
            return poyta.suoritaOsto(vuorossaOleva, Integer.parseInt(ostettavanNimi));
        } else {
            return poyta.suoritaOstoVarauksista(vuorossaOleva, Integer.parseInt(ostettavanNimi));
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
        return poyta.teeVaraus(vuorossaOleva, Integer.parseInt(varattavanNimi));
    }
}
