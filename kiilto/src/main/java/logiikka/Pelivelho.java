package logiikka;

import java.util.*;

import tiralabra.AlmaIlmari;
import ui.gui.Kayttoliittyma;
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
    private final ArrayList<Pelaaja> pelaajat;  //luodaan vasta, kun käyttäjältä on kerätty tarvittavat tiedot.
    private Valintanapit valintanapit;
    private Poyta poyta;                        //luodaan heti, kun pelaajat on luotu.
    private final int voittoValta;
    private int kierros;
    private Pelaaja vuorossaOleva;              //nimetään ensimmäisen kerran pelaajien luomisen yhteydessä.
    private int vuorossaOlevanNro;
    private boolean[] onkoPelaajaAI;            //onko paikalla i oleva pelaaja tekoäly?
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
        poyta = new Poyta(pelaajat);
        AI = new AlmaIlmari();

        if (onkoPelaajaAI[vuorossaOlevanNro]) {
            peluutaAInVuoro();
        }

//        kierros = poyta.luoTestattavaPelitilanne(5);
        kayttoliittyma = new Kayttoliittyma(this);
        kayttoliittyma.run();
    }

    public Poyta getPoyta() {
        return poyta;
    }

    public void setPoyta(Poyta poyta) {
        this.poyta = poyta;
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
     *
     * @param onkoAI lista, mitkä pelaajat ovat tekoälyjä
     */
    public void luoPelaajatJaAIt(boolean[] onkoAI) {
        ArrayList<String> nimet = new ArrayList<>();
        int tekoalyja = 0;
        for (int i = 0; i < onkoAI.length ; i++) {
            if (onkoAI[i]) {
                tekoalyja++;
                nimet.add("AI " + tekoalyja);
            } else {
                nimet.add("Pelaaja " + (i - tekoalyja + 1));
            }
        }
        luoPelaajat(nimet, onkoAI);
    }

    /**
     * Luo annettujen nimien mukaiset pelaajat.
     *
     * @param nimet pelaajien nimet.
     */
    public void luoPelaajat(ArrayList<String> nimet, boolean[] onkoAI) {
        this.onkoPelaajaAI = onkoAI;
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
        alustaAloittavaPelaaja();
    }

    private void alustaAloittavaPelaaja() {
        vuorossaOlevanNro = 0;
        vuorossaOleva = pelaajat.get(vuorossaOlevanNro);
    }

    /**
     * Luo pelaaja-oliot oletusnimillä.
     *
     * @param pelaajia kuinka monta pelaajaa luodaan.
     */
    public void luoPelaajat(int pelaajia) {
        ArrayList<String> nimet = new ArrayList<>();
        for (int i = 0; i < pelaajia; i++) {
            nimet.add("Pelaaja" + (i + 1));
        }
        luoPelaajat(nimet, new boolean[nimet.size()]);
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
        poyta.vaikuttikoPelaajaMerkkihenkiloon(vuorossaOleva);

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

        if (onkoPelaajaAI[vuorossaOlevanNro]) {
            peluutaAInVuoro();
            kayttoliittyma.repaint();
            seuraavaPelaajanVuoro();
        }
    }

    private void peluutaAInVuoro() {
        System.out.println("Pelivelho kutsuu AI.ta pelaamaan vuoron pelaajalla nro " + vuorossaOlevanNro +
                "\n" + vuorossaOleva);
        AI.pelaaVuoro(vuorossaOleva, poyta);
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
