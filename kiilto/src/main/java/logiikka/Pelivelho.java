package logiikka;

import ui.gui.Kayttoliittyma;
import java.awt.Graphics;
import java.util.*;
import ui.*;
import logiikka.valineluokat.*;
import ui.gui.Piirtoavustaja;
import ui.gui.Valintanapit;

/**
 * Luokka vastaa pelin pyörittämisestä. Esimerkiksi pelin alustaminen, vuorojen
 * organisoiminen sekä UI.n ja logiikan rajapintana toimiminen ovat sen
 * tehtäviä.
 *
 * Tulevaisuudessa peliin tehdään tekoäly, minkä vuoksi testinomaisia text user
 * interface -metodeita ja toimintoja säilytetään yhä kommenteissa.
 *
 * @author xvixvi
 */
public class Pelivelho {

    private final TUI tui;
    private final Kayttoliittyma kayttoliittyma;
    private final ArrayList<Pelaaja> pelaajat;
    private Valintanapit valintanapit;
    private Poyta poyta;
    private int voittoValta;
    private int kierros;
    private Pelaaja vuorossaOleva;

    public Pelivelho() {
        tui = new TUI(new Scanner(System.in));
        kayttoliittyma = new Kayttoliittyma(this);
        pelaajat = new ArrayList<>();
        voittoValta = 15;
        kierros = 1;
        vuorossaOleva = null;
        valintanapit = null;
    }

    /**
     * Alustaa ja pelaa kiilto-pelin.
     */
    public void pelaa() {
//        alustaTestiTUIPeli(); //tui-peli
//        alustaTUIPeli();
//        pelaaTUI();

        poyta = new Poyta(pelaajat);

        poyta.luoTestattavaPelitilanne(3);
        
        vuorossaOleva = pelaajat.get(0);

        kayttoliittyma.run();
    }

    /*
     ********************************************
     TEXT USER INTERFACE -METODIT ALKAVAT
     ********************************************
     */
    private void pelaaTUI() {
        while (onkoVoittaja()) {
            pelaaKierros();
        }
    }

    private void alustaTUIPeli() {
        int pm = tui.selvitaPelaajienMaara();
        ArrayList<String> nimet = tui.selvitaPelaajienNimet(pm);
        luoPelaajat(nimet);

        voittoValta = tui.selvitaVoittoonTarvittavaValta();
    }

    private void alustaTestiTUIPeli() {
        ArrayList<String> p = new ArrayList<>();
        p.add("varakas");
        p.add("homokaks");
        p.add("homo3");
        p.add("mr Gandalf");
        luoPelaajat(p);
        pelaajat.get(0).setKarkit(new int[]{3, 5, 5, 5, 5, 5});
        voittoValta = 5;
        poyta = new Poyta(pelaajat);
    }

    private void pelaaKierros() {
        kierros++;
        tui.tulostaKierroksenVaihto(kierros, voittoValta);
        for (Pelaaja p : pelaajat) {
            pelaaVuoro(p);
        }
    }

    private void pelaaVuoro(Pelaaja pelaaja) {
        vuorossaOleva = pelaaja;
        tui.tulostaVuoronAlkuinfot(vuorossaOleva, poyta);

        int valinta = 0;
        valinta = tui.pelaajanToimi(vuorossaOleva.getNimi());

        if (valinta == 1) { // nostetaan nallekarkkeja
            nostaNallekarkkeja();
        } else if (valinta == 2) { // ostetaan omaisuutta
            ostaOmaisuutta();
        } else if (valinta == 3) { // tehdään varaus pöydästä
            teeVaraus();
        }

        if (vuorossaOleva.liikaaKarkkeja()) {
            //wip
        }
    }

    private void nostaNallekarkkeja() {
        int[] maarat = tui.mitaKarkkejaNostetaan(poyta.getMarkkinat());
        //testataan, halusiko pelaaja tehdä toisen toiminnon
        if (maarat == null) {
            pelaaVuoro(vuorossaOleva);
            return;
        }

        nostaNallekarkkeja(maarat);
    }

    private void ostaOmaisuutta() {
        while (true) {
            int ostonNumero = tui.mikaOmistusOstetaan(poyta.getNakyvienNimet());

            if (ostonNumero == -1) {
                pelaaVuoro(vuorossaOleva);
                return;
            }

            if (ostonNumero == 0) {
                ostaVaraus();
                return;
            } else if (poyta.suoritaOsto(vuorossaOleva, ostonNumero)) {
                break;
            }
        }
    }

    private void ostaVaraus() {
        while (true) {
            int ostettava = tui.ostettavanVarauksenNro(vuorossaOleva);

            if (ostettava == -1) {
                pelaaVuoro(vuorossaOleva);
                return;
            }

            if (poyta.suoritaOstoVarauksista(vuorossaOleva, ostettava)) {
                break;
            }
        }
    }

    private void teeVaraus() {
        while (true) {
            int varauksenNro = tui.mikaOmistusVarataan(poyta.getNakyvienNimet());

            if (varauksenNro == -1) {
                pelaaVuoro(vuorossaOleva);
                return;
            }

            if (poyta.teeVaraus(vuorossaOleva, varauksenNro)) {
                break;
            }
        }
    }

    /*
     *******************************************
     TEXT USER INTERFACE -METODIT LOPPUVAT
     *******************************************
     */
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
     * Metodi piirtää pöydän koko komeudessaan.
     *
     * @param graphics
     * @param pa
     */
    public void piirra(Graphics graphics, Piirtoavustaja pa) {
        poyta.piirra(graphics, pa);
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
     * @return
     */
    public boolean vahintaanNeljaKarkkia(int vari) {
        return poyta.getMarkkinat().getKasanKoko(vari) > 3;
    }

    public void nostaNallekarkkeja(int[] maarat) {
        Kasakokoelma karkit = vuorossaOleva.getKarkit();
        for (int i = 0; i < maarat.length; i++) {
            int m = maarat[i];
            if (m > 0) {
                poyta.getMarkkinat().siirraToiseenKasaan(karkit, i + 1, m);
            }
        }
    }

    public void vieVarauksetJaNakyvatOmistuksetValitsimelle() {
        ArrayList<String> nimet = poyta.getNakyvienNimet();
        nimet.addAll(vuorossaOleva.getVaraustenNimet());
        valintanapit.setNakyvienNimet(nimet, true);
    }

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

    public boolean osta(String ostettavanNimi) {
        if (poyta.getNakyvienNimet().contains(ostettavanNimi)) {
            return poyta.suoritaOsto(vuorossaOleva, Integer.parseInt(ostettavanNimi));
        } else {
            return poyta.suoritaOstoVarauksista(vuorossaOleva, Integer.parseInt(ostettavanNimi));
        }
    }

    public boolean varaa(String varattavanNimi) {
        return poyta.teeVaraus(vuorossaOleva, Integer.parseInt(varattavanNimi));
    }

}
